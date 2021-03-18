(ns dcs.prototype-6.ui-region-title
  (:require [dcs.prototype-6.state :as state]))

(defn ele [region]
      [:div.region-title [:h1 region]])

(defn create []
      [ele @state/region-holder])