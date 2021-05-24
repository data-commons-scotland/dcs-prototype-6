(ns dcs.prototype-6.view.regional-dashboard.main
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.regional-dashboard.map :as map]
            [dcs.prototype-6.view.regional-dashboard.region-title :as region-title]
            [dcs.prototype-6.view.regional-dashboard.region-position :as region-position]
            [dcs.prototype-6.view.regional-dashboard.waste-site-derivation :as waste-site-derivation]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation :as household-waste-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-percent-recycled :as household-waste-derivation-percent-recycled]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-management :as household-waste-derivation-management]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-composition :as household-waste-derivation-composition]
            [dcs.prototype-6.view.regional-dashboard.household-co2e-derivation-generation :as household-co2e-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-generation :as business-waste-by-region-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-composition :as business-waste-by-region-derivation-composition]))


(defn root []
      [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Regional dashboard"]]

        ;; Map column & household column, reversed-in
        [:div.columns.is-vcentered.is-flex-direction-row-reverse
         [:div.column.is-one-third
          [map/root]]
         [:div.column
          [:div.columns
           [:div.column.is-full
            [region-title/root]]]
          [:div.columns
           [:div.column.is-two-fifths
            [:h1.subtitle "Household waste per citizen"]
            [region-position/root]]
           [:div.column
            [:h1.subtitle {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
            [household-waste-derivation-generation/root]]]
          [:div.columns
           [:div.column.is-two-fifths
            [household-waste-derivation-composition/root]]
           [:div.column
            [household-waste-derivation-management/root]]]
          [:div.columns
           [:div.column.is-two-fifths
            [household-waste-derivation-percent-recycled/root]]
           [:div.column
            [household-co2e-derivation-generation/root]]]]]

        ;; Business column
        [:div.columns
         [:div.column.is-two-thirds
          [:div.columns
           [:div.column.is-two-fifths
            [:h1.subtitle "Business waste for the region"]
            [business-waste-by-region-derivation-composition/root]]
           [:div.column
            [:h1.subtitle {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
            [business-waste-by-region-derivation-generation/root]]]]
         [:div.column
          [:br]
          [:h1.subtitle "Operational waste sites in the region"]
          [waste-site-derivation/root]]]

       ;; Info column
       [:div.columns
        [:div.column.is-full
        [:div.content.is-small.has-text-info
         [:ol
          [:li "The " [:b "carbon impact"] " is a measure devised by " [:a {:href "https://www.zerowastescotland.org.uk" :target "_blank"} "Zero Waste Scotland"] ","
           " that conveys the whole-life carbon impact of waste,"
           " from resource extraction and manufacturing emissions, right through to waste management emissions."
           " Its unit-of-measure is " [:b "tonnes of carbon dioxide equivalent"] " (" [:b "CO" [:sub "2"] "eT"] ")."]]]]]])

