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
      "Clickable navbar items should close the burger.
      This codes that."
      [href]
      {:href href
       :on-click close-burger})

(defn navbar []
      [:nav.navbar.is-fixed-top.is-primary {:role "navigation"}

       [:input#toggler.toggler {:type "checkbox"}]
       [:div.navbar-brand
        [:a.navbar-item {:href "#"}
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
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/dashboard-page))
            [:div.navbar-content
             "Waste by region"
             [:p.is-size-7.has-text-info "Discover and compare regional waste figures"]]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/todo-page))
            [:div.navbar-content
             "Waste through time"
             [:p.is-size-7.has-text-info "Discover and compare waste figures through time"]]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/todo-page))
            [:div.navbar-content
             "Dimensions overview"
             [:p.is-size-7.has-text-info "The list of our datasets' dimensions"]]]]]

         ;; Data
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#data-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "data-checkbox"} "Data"]
          [:div#data-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About the datasets on this site"]]
           [:a.navbar-item (navbar-clickable "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/")
            [:div.navbar-content
             "Datasets"
             [:p.is-size-7.has-text-info "An introduction to our " [:em "easier-to-use"] " datasets"]]]
           [:a.navbar-item (navbar-clickable "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/index.html#dimensions")
            [:div.navbar-content
             "Dimensions"
             [:p.is-size-7.has-text-info "Descriptions of the dimensions of the datasets"]]]
           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Directly access the datasets"]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-waste.csv")
            [:div.navbar-content
             "Household waste " [:content.has-text-info "(19,008 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-co2e.csv")
            [:div.navbar-content
             "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e " [:small "(208 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-region.csv")
            [:div.navbar-content
             "Business waste by region " [:content.has-text-info "(8,976 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-sector.csv")
            [:div.navbar-content
             "Business waste by sector " [:content.has-text-info "(2,640 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site.csv")
            [:div.navbar-content
             "Waste site " [:small "(1254 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site-io.csv")
            [:div.navbar-content
             "Waste site ins " [:amp] " outs " [:content.has-text-info "(2,667,914 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/material-coding.csv")
            [:div.navbar-content
             "Material coding " [:content.has-text-info "(557 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/ewc-coding.csv")
            [:div.navbar-content
             "EWC coding " [:content.has-text-info "(973 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/households.csv")
            [:div.navbar-content
             "Households " [:content.has-text-info "(288 records)"]]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/population.csv")
            [:div.navbar-content
             "Population " [:content.has-text-info "(288 records)"]]]]]

         ;; About
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown.is-right
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/home-page))
            [:div.navbar-content
             "Home page"
             [:p.is-size-7.has-text-info"Go to the splash page"]]]
           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Background"]]
           [:a.navbar-item (navbar-clickable "https://campuspress.stir.ac.uk/datacommonsscotland/")
            [:div.navbar-content
             "About the encompassing project"]]
           [:a.navbar-item (navbar-clickable "https://github.com/data-commons-scotland")
            [:div.navbar-content
             "GitHub repositories"
             [:p.is-size-7.has-text-info "For some of the project’s longer-lifespan outputs such as concepts/models, standards, research output and open source code."]]]]]]]])