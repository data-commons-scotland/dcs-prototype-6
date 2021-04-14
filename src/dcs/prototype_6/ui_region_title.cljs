(ns dcs.prototype-6.ui-region-title
  (:require [dcs.prototype-6.state :as state]))

(defn ele [region]
      [:div.has-text-centered [:h1.title region]])

(defn create []
      [ele @state/region-holder])