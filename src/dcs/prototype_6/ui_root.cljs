(ns dcs.prototype-6.ui-root
  (:require [dcs.prototype-6.state :refer [app-state]]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]))

(defn navbar []
      [:div
       [:h1 "Waste Matters Scotland"]])

(defn counter []
      [:div
       [:button.btn {:on-click #(decrement %)} "-"]
       [:button {:disabled true} (get @app-state :count)]
       [:button.btn {:on-click #(increment %)} "+"]])

(defn app []
      [:div.main-container
       [navbar]
       [counter]
       [:div.row
        [:div.floats-right-column.one-third-width [ui-map/create]]
        [:div.floats-right-column.two-thirds-width [:h1 "Region-xxx name"] [:p "Top waste facts about Region-xxx"]]]
       [:div.row [:p "Other waste facts (including infographics) about region-xxx"]]])

