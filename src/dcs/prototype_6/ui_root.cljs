(ns dcs.prototype-6.ui-root
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]))

(defn navbar []
      [:div.navbar
       [:a {:href "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/index.html"}
        [:img {:src "/img/dcs-circle.png"}]
        "Waste Matters Scotland"]])

(defn counter []
      [:div
       [:button.btn {:on-click #(decrement %)} "-"]
       [:button {:disabled true} (get @state/app-state :count)]
       [:button.btn {:on-click #(increment %)} "+"]])

(defn app []
      [:div.main-container
       [navbar]
       [:div.page-title [:h2 "Regional facts about waste"]]
       ;[counter]
       [:div.row
        [:div.floats-right-column.one-third-width [ui-map/create]]
        [:div.floats-right-column.two-thirds-width [:div.region-title [:h1 @state/region-holder]] [:p "Top waste facts about Region-xxx"]]]
       [:div.row [:p "Other waste facts (including infographics) about region-xxx"]]])

