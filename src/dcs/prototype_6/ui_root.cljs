(ns dcs.prototype-6.ui-root
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]
            [dcs.prototype-6.ui-region-info :as ui-region-info]
            [dcs.prototype-6.ui-household-waste :as ui-household-waste]
            [dcs.prototype-6.ui-household-co2e :as ui-household-co2e]))

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
       [:div.page-title.rounded-corners [:h2 "Regional facts"]]
       ;[counter]
       [:div.row
        [:div.floats-right-column.one-third-width [ui-map/create]]
        [:div.floats-right-column.two-thirds-width [ui-region-info/create-panel-a]]]
       [:div.row
        [:div.floats-left-column.one-third-width [ui-household-waste/create]]
        [:div.floats-left-column.one-third-width [ui-household-co2e/create]]
        [:div.floats-left-column.one-third-width [:p "WIP"]]]])

