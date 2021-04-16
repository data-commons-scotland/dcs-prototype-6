(ns dcs.prototype-6.ui-nav
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


(defn close-burger
      "Close-up the burger's dropdowns and the burger itself"
      [& _]
      ;;  Comment-out. Isn't affecting clicked-on dropdowns as I'd like. Maybe give-up on this.
      ;;  (doseq [id ["explore-dropdown" "data-dropdown" "about-dropdown" "toggler" ]]
      ;;         (.remove (.-classList (.getElementById js/document id)) "is-active"))
      (doseq [id ["explore-checkbox" "data-checkbox" "about-checkbox" "toggler" ]]
             (set! (.-checked (.getElementById js/document id)) false)))

(defn navbar-clickable
      ([title href]
       [:a.navbar-item
        {:href href :on-click close-burger}  ;; Clickable navbar items should close the burger.
        [:div.navbar-content
         title]])
      ([title subtitle href]
       (-> (navbar-clickable title href)
           (assoc-in [2 2] [:p.is-size-7.has-text-info subtitle])))) ;; Append a vector that contains the subtitle

(defn navbar []
      [:nav.navbar.is-fixed-top.is-primary {:role "navigation"}

       [:input#toggler.toggler {:type "checkbox"}]
       [:div.navbar-brand
        [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/home-page)}
         [:img.brand-logo {:src "img/dcs-circle.png" :alt "Waste Matters Scotland logo"}]
         "Waste Matters Scotland"]
        [:a.navbar-item]

        [:label.navbar-burger.burger {:data-target "topnavbar" :for "toggler" :role "button"}
         [:span]
         [:span]
         [:span]]]

       [:div#topnavbar.navbar-menu

        [:div.navbar-start
         #_[:a.navbar-item {:href "#"} "Left side items beside the brand logo"]]

        [:div.navbar-end

         ;; Explore
         [:div.navbar-item.has-dropdown.is-hoverable.is-right
          [:input#explore-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "explore-checkbox"} "Explore"]
          [:div#explore-dropdown.navbar-dropdown.is-right
           (navbar-clickable "Waste by region"
                             "Discover and compare regional waste figures"
                             (rfe/href :dcs.prototype-6.browser/dashboard-page))
           (navbar-clickable "Waste sites and the quantities of incoming materials"
                             "https://data-commons-scotland.github.io/cluster-map-of-materials-incoming/")
           (navbar-clickable "Household quanitites through time"
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hw-mgmt")
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e through time"]
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hw-co2e")
           (navbar-clickable "Household vs business quantities through time"
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hvb")]]

         ;; Data
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#data-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "data-checkbox"} "Data"]
          [:div#data-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About the datasets on this site"]]
           (navbar-clickable "Datasets"
                             [:span "An introduction to our " [:em "easier-to-use"] " datasets"]
                             "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/")
           (navbar-clickable "Dimensions"
                             "Descriptions of the dimensions of the datasets"
                             "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/index.html#dimensions")
           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Directly access the datasets"]]
           (navbar-clickable [:span "Household waste " [:content.has-text-info "(19,008 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-waste.csv")
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e " [:content.has-text-info "(208 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-co2e.csv")
           (navbar-clickable [:span "Business waste by region " [:content.has-text-info "(8,976 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-region.csv")
           (navbar-clickable [:span "Business waste by sector " [:content.has-text-info "(2,640 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-sector.csv")
           (navbar-clickable [:span "Waste site " [:content.has-text-info "(1254 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site.csv")
           (navbar-clickable [:span "Waste site ins " [:amp] " outs " [:content.has-text-info "(2,667,914 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site-io.csv")
           (navbar-clickable [:span "Material coding " [:content.has-text-info "(557 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/material-coding.csv")
           (navbar-clickable [:span "EWC coding " [:content.has-text-info "(973 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/ewc-coding.csv")
           (navbar-clickable [:span "Households " [:content.has-text-info "(288 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/households.csv")
           (navbar-clickable [:span "Population " [:content.has-text-info "(288 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/population.csv")]]

         ;; About
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown.is-right
           (navbar-clickable "About the encompassing project"
                             "https://github.com/data-commons-scotland")
           (navbar-clickable "GitHub repositories"
                             "For some of the project’s longer-lifespan outputs such as concepts/models, standards, research output and open source code."
                             "https://github.com/data-commons-scotland")]]]]])