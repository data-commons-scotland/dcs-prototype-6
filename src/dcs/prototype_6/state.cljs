(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce geojson (r/atom nil))

(defonce region-holder (r/atom "please select a region..."))


;; request
(go (let [response (<! (http/get "regions.json"))]
         (do
           (js/console.log (str "state status:" (:status response)))
           (js/console.log (str "state success:" (:success response)))
           (reset! geojson (clj->js (:body response)))
           (js/console.log (str "state @geojson type:" (type @geojson)))
           )))