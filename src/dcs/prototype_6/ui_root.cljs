(ns dcs.prototype-6.ui-root
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]
            [dcs.prototype-6.ui-region-title :as ui-region-title]
            [dcs.prototype-6.ui-household-waste-derivation-generation :as ui-household-waste-derivation-generation]
            [dcs.prototype-6.ui-household-waste-derivation-percent-recycled :as ui-household-waste-derivation-percent-recycled]
            [dcs.prototype-6.ui-household-waste-derivation-management :as ui-household-waste-derivation-management]
            [dcs.prototype-6.ui-household-waste-derivation-composition :as ui-household-waste-derivation-composition]
            [dcs.prototype-6.ui-household-co2e-derivation :as ui-household-co2e-derivation]
            [dcs.prototype-6.ui-business-waste-by-region-derivation-generation :as ui-business-waste-by-region-derivation-generation]
            [dcs.prototype-6.ui-business-waste-by-region-derivation-composition :as ui-business-waste-by-region-derivation-composition]))

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
          [ui-region-title/create]]
         [:div.row
          [:h3 "Household waste per citizen"]
          [:p "todo: say something specific about this region"]
          [:div.floats-left-column.half-width [ui-household-waste-derivation-generation/create]]
          [:div.floats-left-column.half-width [ui-household-waste-derivation-composition/create]]]
         [:div.row
          [:div.floats-left-column.half-width [ui-household-waste-derivation-management/create]]
          [:div.floats-left-column.half-width [ui-household-waste-derivation-percent-recycled/create]]]
         [:div.row
          [:div.floats-left-column.half-width [ui-household-co2e-derivation/create]]
          [:div.floats-left-column.half-width [:p "todo: number of waste sites"] [:p "todo: nth best for abc"] [:p "todo: nth best for xyz"]]
          ]]]
       [:div.row
        [:h3 "Business waste for the region"]
        [:div.floats-left-column.one-third-width [ui-business-waste-by-region-derivation-generation/create]]
        [:div.floats-left-column.one-third-width [ui-business-waste-by-region-derivation-composition/create]]
        [:div.floats-left-column.one-third-width [:p "todo: more info about business waste"]]]])

