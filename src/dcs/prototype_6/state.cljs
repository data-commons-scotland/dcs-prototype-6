(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce geojson (r/atom nil))


;; request
(go (let [response (<! (http/get "regions.json"))]
         (do
           (js/console.log (str "status:" (:status response)))
           (js/console.log (str "success:" (:success response)))
           (reset! geojson (:body response))
           (js/console.log (str "body size:" (count (:body response))))
           )))