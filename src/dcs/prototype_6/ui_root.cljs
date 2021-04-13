(ns dcs.prototype-6.ui-root
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.events :refer [increment decrement]]
            [dcs.prototype-6.ui-map :as ui-map]
            [dcs.prototype-6.ui-region-title :as ui-region-title]
            [dcs.prototype-6.ui-region-position :as ui-region-position]
            [dcs.prototype-6.ui-waste-site-derivation :as ui-waste-site-derivation]
            [dcs.prototype-6.ui-household-waste-derivation-generation :as ui-household-waste-derivation-generation]
            [dcs.prototype-6.ui-household-waste-derivation-percent-recycled :as ui-household-waste-derivation-percent-recycled]
            [dcs.prototype-6.ui-household-waste-derivation-management :as ui-household-waste-derivation-management]
            [dcs.prototype-6.ui-household-waste-derivation-composition :as ui-household-waste-derivation-composition]
            [dcs.prototype-6.ui-household-co2e-derivation-generation :as ui-household-co2e-derivation-generation]
            [dcs.prototype-6.ui-business-waste-by-region-derivation-generation :as ui-business-waste-by-region-derivation-generation]
            [dcs.prototype-6.ui-business-waste-by-region-derivation-composition :as ui-business-waste-by-region-derivation-composition]))


(defn counter []
      [:div
       [:button.btn {:on-click #(decrement %)} "-"]
       [:button {:disabled true} (get @state/app-state :count)]
       [:button.btn {:on-click #(increment %)} "+"]])

(defn app []
      [:div
       [:div.page-title.rounded-corners [:h2 "Regional dashboard"]]
       ;[counter]
       [:div.row
        [:div.floats-right-column.one-third-width [ui-map/create]]
        [:div.floats-right-column.two-thirds-width
         [:div.row
          [ui-region-title/create]]
         [:div.row
          [:h3 "Household waste per citizen"]
          [:div.floats-left-column.two-fiths-width [ui-region-position/create]]
          [:div.floats-left-column.three-fiths-width [ui-household-waste-derivation-generation/create]]]
         [:div.row
          [:div.floats-left-column.two-fiths-width [ui-household-waste-derivation-composition/create]]
          [:div.floats-left-column.three-fiths-width [ui-household-waste-derivation-management/create]]]
         [:div.row
          [:div.floats-left-column.two-fiths-width [ui-household-waste-derivation-percent-recycled/create]]
          [:div.floats-left-column.three-fiths-width [ui-household-co2e-derivation-generation/create]]]]]
       [:div.row
        [:div.floats-left-column.two-thirds-width
         [:div.floats-left-column.two-fiths-width
          [:h3 "Business waste for the region"]
          [ui-business-waste-by-region-derivation-composition/create]]
         [:div.floats-left-column.thrww-fiths-width
          [:h3 {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
          [ui-business-waste-by-region-derivation-generation/create]]]
         [:div.floats-left-column.one-third-width
          [:br]
          [:h3 "Operational waste sites in the region"]
          [ui-waste-site-derivation/create]]]])

