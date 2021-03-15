(ns dcs.prototype-6.views
  (:require [dcs.prototype-6.state :refer [app-state]]
            [dcs.prototype-6.events :refer [increment decrement]]))

(defn header []
      [:div
       [:h1 "A template for reagent apps"]])

(defn counter []
      [:div
       [:button.btn {:on-click #(decrement %)} "-"]
       [:button {:disabled true} (get @app-state :count)]
       [:button.btn {:on-click #(increment %)} "+"]])

(defn map-ui []
      [:div#map-ui.map-container])

(defn app []
      [:div.main-container
       [header]
       [counter]
       [map-ui]])

