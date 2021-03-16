(ns dcs.prototype-6.ui-region-info
  (:require [dcs.prototype-6.state :as state]))

(defn panel-a [region]
      [:div
       [:div.region-title [:h1 region]]
       [:p (str "Top waste facts about " region)]])

(defn panel-b [region]
      [:div
       [:p (str "Other waste facts (including infographics) about " region)]])

(defn create-panel-a []
      [panel-a @state/region-holder])

(defn create-panel-b []
      [panel-b @state/region-holder])