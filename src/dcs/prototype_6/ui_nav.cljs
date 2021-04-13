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
      (doseq [id ["explore-checkbox" "datasets-checkbox" "about-checkbox" "toggler" ]]
             (set! (.-checked (.getElementById js/document id)) false)))


(defn navbar []
      [:nav.navbar.is-fixed-top.is-link {:role "navigation"}
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
         [:div.navbar-item.has-dropdown.is-hoverable.is-right
          [:input#explore-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "explore-checkbox"} "Explore"]
          [:div#explore-dropdown.navbar-dropdown
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/dashboard-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Waste by region"]]
             [:p [:small "Discover and compare regional waste figures"]]]]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/todo-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Waste through time"]]
             [:p [:small "Discover and compare waste figures through time"]]]]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/todo-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Dimensions overview"]]
             [:p [:small "The list of our datasets' dimensions"]]]]]]
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#datasets-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "datasets-checkbox"} "Datasets"]
          [:div#datasets-dropdown.navbar-dropdown
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/todo-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Overview"]]
             [:p [:small "A description of our datasets"]]]]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/todo-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Dimensions overview"]]
             [:p [:small "The list of our datasets' dimensions"]]]]
           [:br.navbar-divider]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/todo-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "CO2e"]]
             [:p [:small "What does this look like?"]]]]
           [:br.navbar-divider]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/route1-page) :on-click close-burger}
            [:div.navbar-content
             [:p
              "Route 1"]
             [:p "Take route 1"]]]]]
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/home-page) :on-click close-burger}
            [:div.navbar-content
             [:p [:strong "Home page"]]
             [:p [:small "Go to the splash page"]]]]
           [:br.navbar-divider]
           [:a.navbar-item {:href (rfe/href :dcs.prototype-6.browser/route1-page) :on-click close-burger}
            [:div.navbar-content
             [:p
              "Route 1"]
             [:p "Take route 1"]]]]]]]])