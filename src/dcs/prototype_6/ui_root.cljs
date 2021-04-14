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


(defn app []
      [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Regional dashboard"]]

        ;; Map column & household column, reversed-in
        [:div.columns.is-vcentered.is-flex-direction-row-reverse
         [:div.column.is-one-third
          [ui-map/create]]
         [:div.column
          [:div.columns
           [:div.column.is-full
            [ui-region-title/create]]]
          [:div.columns
           [:div.column.is-two-fifths
            [:h1.subtitle "Household waste per citizen"]
            [ui-region-position/create]]
           [:div.column
            [:h1.subtitle {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
            [ui-household-waste-derivation-generation/create]]]
          [:div.columns
           [:div.column.is-two-fifths
            [ui-household-waste-derivation-composition/create]]
           [:div.column
            [ui-household-waste-derivation-management/create]]]
          [:div.columns
           [:div.column.is-two-fifths
            [ui-household-waste-derivation-percent-recycled/create]]
           [:div.column
            [ui-household-co2e-derivation-generation/create]]]]]

        ;; Business column
        [:div.columns
         [:div.column.is-two-thirds
          [:div.columns
           [:div.column.is-two-fifths
            [:h1.subtitle "Business waste for the region"]
            [ui-business-waste-by-region-derivation-composition/create]]
           [:div.column
            [:h1.subtitle {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
            [ui-business-waste-by-region-derivation-generation/create]]]]
         [:div.column
          [:br]
          [:h1.subtitle "Operational waste sites in the region"]
          [ui-waste-site-derivation/create]]]])

