(ns dcs.prototype-6.view.regional-dashboard.main
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.regional-dashboard.map :as map]
            [dcs.prototype-6.view.regional-dashboard.region-title :as region-title]
            [dcs.prototype-6.view.regional-dashboard.region-curiosities :as region-curiosities]
            [dcs.prototype-6.view.regional-dashboard.region-position :as region-position]
            [dcs.prototype-6.view.regional-dashboard.waste-site-derivation :as waste-site-derivation]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation :as household-waste-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-percent-recycled :as household-waste-derivation-percent-recycled]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-management :as household-waste-derivation-management]
            [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-composition :as household-waste-derivation-composition]
            [dcs.prototype-6.view.regional-dashboard.household-co2e-derivation-generation :as household-co2e-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-generation :as business-waste-by-region-derivation-generation]
            [dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-composition :as business-waste-by-region-derivation-composition]))


(defn root [route]
  (r/after-render (util/scroll-fn))

  (let [region (some-> route :parameters :query :region)]
    ;;(js/console.log (str "region=" region))
    (reset! state/region-cursor region)

    [:div
     
     [:section.hero.is-small.has-background
      [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                            :alt "Regional dashboard image"}]
      [:div.hero-body
       [:div.container
        [:div.content.has-text-centered
         [:div.content.has-text-centered
          [:h1.title.is-5  {:style {:margin 0}} "Regional dashboard"]]]]]]
     
     [:section.section
      
     ;; Map column & household column, reversed-in
      [:div.columns.is-vcentered.is-flex-direction-row-reverse
       [:div.column.is-one-third
        [map/root]]
       [:div.column
        [:div.columns
         [:div.column.is-full
          [region-title/root]]]
        [:div.columns
         [:div.column.is-full
          [region-curiosities/root]]]
        [:div.columns
         [:div.column.is-two-fifths
          [:h1.subtitle "Household waste per person"]
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
         [:ol.is-lower-alpha
          [:li "By 2025, the " [:a {:href   "https://www.gov.scot/policies/managing-waste/"
                                    :target "_blank"} "Scottish Government aims"] 
           " to \"" [:b "reduce total waste arising in Scotland by 15% against 2011 levels"] "\""
           " and to \"" [:b "recycle 70% of remaining waste"] "\"."]
          [:li "The " [:b "carbon impact"] " is a measure devised by " [:a {:href   "https://www.zerowastescotland.org.uk"
                                                                            :target "_blank"} "Zero Waste Scotland"] ","
           " that conveys the whole-life carbon impact of waste,"
           " from resource extraction and manufacturing emissions, right through to waste management emissions."
           " Its unit-of-measure is " [:b "tonnes of carbon dioxide equivalent"] " (" [:b "CO" [:sub "2"] "eT"] ")."]]]]]]]))

