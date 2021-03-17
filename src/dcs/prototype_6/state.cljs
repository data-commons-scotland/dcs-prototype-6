(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [dcs.prototype-6.data-wrangling :as data-wrangling])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce region-holder (r/atom "Please select a region..."))

(defonce geojson-holder (r/atom nil))

(defonce population-holder (r/atom nil))

(defonce household-waste-3dim-holder (r/atom nil))

(defonce household-co2e-holder (r/atom nil))


(js/console.log (str "loading GeoJSON data"))
(go (let [response (<! (http/get "regions-geo.json"))]
         (do
           (js/console.log (str "GeoJSON, status=" (:status response) ", success=" (:success response)))
           (reset! geojson-holder (clj->js (:body response))))))


(js/console.log (str "loading population data"))
(go (let [response (<! (http/get "population.json"))]
         (do
           (js/console.log (str "population, status=" (:status response) ", success=" (:success response)))
           (let [population0 (:body response)
                 population (concat population0 (data-wrangling/population-values-for-scotland population0))]
                (reset! population-holder population)))))


(js/console.log (str "loading household-waste data"))
(go (let [response (<! (http/get "household-waste.json"))]
         (do
           (js/console.log (str "household-waste, status=" (:status response) ", success=" (:success response)))
           (let [household-waste (:body response)
                 household-waste-3dim0 (data-wrangling/household-waste-3dim household-waste)
                 household-waste-3dim (concat household-waste-3dim0 (data-wrangling/household-waste-3dim-values-for-scotland household-waste-3dim0))]
                (reset! household-waste-3dim-holder household-waste-3dim)))))


(js/console.log (str "loading household-co2e data"))
(go (let [response (<! (http/get "household-co2e.json"))]
         (do
           (js/console.log (str "household-co2e, status=" (:status response) ", success=" (:success response)))
           (let [household-co2e0 (:body response)
                 household-co2e (concat household-co2e0 (data-wrangling/household-co2e-values-for-scotland household-co2e0))]
              (reset! household-co2e-holder household-co2e)))))
