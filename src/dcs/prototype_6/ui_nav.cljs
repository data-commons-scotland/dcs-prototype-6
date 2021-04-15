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
          [:label.navbar-link {:for "datasets-checkbox"} "Data"]
          [:div#data-dropdown.navbar-dropdown.is-right
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/todo-page))
            [:div.navbar-content
             "Overview"
             [:p.is-size-7.has-text-info "A description of our datasets"]]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/todo-page))
            [:div.navbar-content
             "Dimensions overview"
             [:p.is-size-7.has-text-info "The list of our datasets' dimensions"]]]
           [:br.navbar-divider]
           [:div.navbar-item
            [:p.has-text-info "Directly download our datasets"]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/todo-page))
            [:div.navbar-content
             "CO2e"]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/route1-page) )
            [:div.navbar-content
             "Route 1"]]]]

         ;; About
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown.is-right
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/home-page))
            [:div.navbar-content
             "Home page"
             [:p.is-size-7.has-text-info"Go to the splash page"]]]
           [:a.navbar-item (navbar-clickable (rfe/href :dcs.prototype-6.browser/route1-page) )
            [:div.navbar-content
             "Route 1"
             [:p.is-size-7.has-text-info "Take route 1"]]]]]]]])