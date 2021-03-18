(ns dcs.prototype-6.ui-root
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]
            [dcs.prototype-6.ui-region-info :as ui-region-info]
            [dcs.prototype-6.ui-household-waste-3dim :as ui-household-waste-3dim]
            [dcs.prototype-6.ui-household-waste-4dim :as ui-household-waste-4dim]
            [dcs.prototype-6.ui-household-co2e-3dim :as ui-household-co2e-3dim]))

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
       [:div.page-title.rounded-corners [:h2 "Regional dashboards"]]
       ;[counter]
       [:div.row
        [:div.floats-right-column.one-third-width [ui-map/create]]
        [:div.floats-right-column.two-thirds-width
         [:div.row
          [ui-region-info/create-panel-a]]
         [:div.row
          [:h3 "Household waste per citizen"]
          [:div.floats-left-column.half-width [ui-household-waste-3dim/create]]
          [:div.floats-left-column.half-width [:p "TODO % recycled"]]]
         [:div.row
          [:div.floats-left-column.half-width [ui-household-waste-4dim/create]]
          [:div.floats-left-column.half-width [ui-household-co2e-3dim/create]]]]]
       [:div.row
        [:h3 "Business and household for the region"]
        [:div.floats-left-column.one-third-width [:p "TODO h vs b"]]
        [:div.floats-left-column.one-third-width [:p "TODO"]]
        [:div.floats-left-column.one-third-width [:p "TODO"]]]])

