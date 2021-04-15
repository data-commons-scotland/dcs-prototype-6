(ns dcs.prototype-6.datasets-page
      (:require [dcs.prototype-6.state :as state]))

(defn ele [_region-not-used]
      [:div.has-text-centered [:h1.title "Datasets"]])

(defn create []
      [ele @state/region-holder])