(ns dcs.prototype-6.view.regional-dashboard.region-title
  (:require [dcs.prototype-6.state :as state]))

(defn ele [region]
      [:div.has-text-centered [:h1.title region]])

(defn root []
      [ele @state/region-holder])