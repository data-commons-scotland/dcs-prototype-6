(ns dcs.prototype-6.view.regional-dashboard.region-title
  (:require [dcs.prototype-6.state :as state]))

(defn ele [region]
  (let [content (if (nil? region)
                  [:h1.subtitle.has-text-danger "Click on the map to select a region"]
                  [:h1.title region])]
      [:div.has-text-centered content]))

(defn root []
      [ele @state/region-cursor])