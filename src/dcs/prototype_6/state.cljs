(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [dcs.prototype-6.data-shaping :as data-shaping])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce region-holder (r/atom "Please select a region..."))

(defonce geojson-holder (r/atom nil))

(defonce household-waste-3dim-holder (r/atom nil))

(defonce household-waste-4dim-holder (r/atom nil))

(defonce household-co2e-3dim-holder (r/atom nil))

;; -----------------

(defonce population-holder (atom nil))

(defonce household-waste-holder (atom nil))

(defonce household-co2e-holder (atom nil))

;; -----------------

;; Fetch data into holders

(js/console.log (str "load geojson"))
(go (let [response (<! (http/get "geojson.json"))]
         (do
           (js/console.log (str "geojson, status=" (:status response) ", success=" (:success response)))
           (reset! geojson-holder (clj->js (:body response))))))


(js/console.log (str "load population"))
(go (let [response (<! (http/get "population.json"))]
         (do
           (js/console.log (str "population, status=" (:status response) ", success=" (:success response)))
           (let [population0 (:body response)
                 population (concat population0 (data-shaping/rollup-population-regions population0))]
                (reset! population-holder population)))))


(js/console.log (str "load household-waste"))
(go (let [response (<! (http/get "household-waste.json"))]
         (do
           (js/console.log (str "household-waste, status=" (:status response) ", success=" (:success response)))
           (let [household-waste0 (:body response)
                 household-waste (concat household-waste0 (data-shaping/rollup-household-waste-regions household-waste0))]
                (reset! household-waste-holder household-waste)))))


(js/console.log (str "load household-co2e"))
(go (let [response (<! (http/get "household-co2e.json"))]
         (do
           (js/console.log (str "household-co2e, status=" (:status response) ", success=" (:success response)))
           (let [household-co2e0 (:body response)
                 household-co2e (concat household-co2e0 (data-shaping/rollup-household-co2e-regions household-co2e0))]
                (reset! household-co2e-holder household-co2e)))))


;; ----------------------

;; Calc derived data

(defn maybe-calc-household-waste-3dim-and-4dim []
      (let [household-waste @household-waste-holder
            population @population-holder]
           (when (and (some? household-waste)
                      (some? population))
                 (js/console.log "calculating household-waste-3dim and household-waste-4dim")
                 (let [;; Roll-up to get values for (region, year) pairs
                       household-waste-3dim0 (data-shaping/rollup-household-waste-materials-and-management household-waste)

                       ;; Roll-up to get values for (region, year, management) triples
                       household-waste-4dim0 (data-shaping/rollup-household-waste-materials household-waste)

                       ;; Prep for the per citizen calculation
                       population-for-lookup (group-by (juxt :region :year) population)
                       lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

                       ;; Calculate the per citizen values
                       household-waste-3dim (map (fn [{:keys [region year tonnes]}] {:region region
                                                                                     :year   year
                                                                                     :tonnes (double (/ tonnes (lookup-population region year)))})
                                                 household-waste-3dim0)
                       household-waste-4dim (map (fn [{:keys [region year management tonnes]}] {:region     region
                                                                                                :year       year
                                                                                                :management management
                                                                                                :tonnes     (double (/ tonnes (lookup-population region year)))})
                                                 household-waste-4dim0)]
                      (reset! household-waste-3dim-holder household-waste-3dim)
                      (reset! household-waste-4dim-holder household-waste-4dim)))))

(defn maybe-calc-household-co2e-3dim []
      (let [household-co2e @household-co2e-holder
            population @population-holder]
           (when (and (some? household-co2e)
                      (some? population))
                 (js/console.log "calculating household-co2e-3dim")
                 (let [;; Prep for the per citizen calculation
                       population-for-lookup (group-by (juxt :region :year) population)
                       lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

                       ;; Calculate the per citizen values
                       household-co2e-3dim (map (fn [{:keys [region year tonnes]}] {:region region
                                                                                    :year   year
                                                                                    :tonnes (double (/ tonnes (lookup-population region year)))})
                                                household-co2e)]
                      (reset! household-co2e-3dim-holder household-co2e-3dim)))))

;; -------------------

;; Watch for data updates

(add-watch population-holder :household-waste-3dim-and-4dim-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-3dim-and-4dim))))

(add-watch population-holder :household-waste-3dim-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-3dim))))

(add-watch household-waste-holder :household-waste-3dim-and-4dim-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-3dim-and-4dim))))

(add-watch household-co2e-holder :household-co2e-3dim-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-3dim))))
