(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce region-holder (r/atom "Please select a region..."))

(defonce geojson-holder (r/atom nil))

(defonce population-holder (r/atom nil))

(defonce household-waste-holder (r/atom nil))

(defonce household-co2e-holder (r/atom nil))


(js/console.log (str "loading GeoJSON data"))
(go (let [response (<! (http/get "regions-geo.json"))]
         (do
           (js/console.log (str "GeoJSON, status=" (:status response)))
           (js/console.log (str "GeoJSON, success=" (:success response)))
           (reset! geojson-holder (clj->js (:body response))))))

(js/console.log (str "loading population data"))
(go (let [response (<! (http/get "population.json"))]
         (do
           (js/console.log (str "population, status:" (:status response)))
           (js/console.log (str "population, success:" (:success response)))
           (reset! population-holder (:body response)))))

(js/console.log (str "loading household-waste data"))
(go (let [response (<! (http/get "household-waste.json"))]
         (do
           (js/console.log (str "household-waste, status:" (:status response)))
           (js/console.log (str "household-waste, success:" (:success response)))
           (let [household-waste (:body response)]
                (reset! household-waste-holder household-waste)))))

(js/console.log (str "loading household-co2e data"))
(go (let [response (<! (http/get "household-co2e.json"))]
         (do
           (js/console.log (str "household-co2e, status:" (:status response)))
           (js/console.log (str "household-co2e, success:" (:success response)))
           (let [household-co2e (:body response)]
              (reset! household-co2e-holder household-co2e)))))
