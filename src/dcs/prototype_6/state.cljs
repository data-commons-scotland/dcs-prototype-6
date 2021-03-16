(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce geojson-holder (r/atom nil))

(defonce region-holder (r/atom "Please select a region..."))


(js/console.log (str "loading GeoJSON data"))
(go (let [response (<! (http/get "regions.json"))]
         (do
           (js/console.log (str "GeoJSON data, status:" (:status response)))
           (js/console.log (str "GeoJSON data, success:" (:success response)))
           (reset! geojson-holder (clj->js (:body response))))))