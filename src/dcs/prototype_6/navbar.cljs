(ns dcs.prototype-6.navbar
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.util :as util]))


(defn close-burger
      "Close-up the burger's dropdowns and the burger itself"
      [& _]
      ;;  Comment-out. Isn't affecting clicked-on dropdowns as I'd like. Maybe give-up on this.
      ;;  (doseq [id ["explore-dropdown" "data-dropdown" "about-dropdown" "toggler" ]]
      ;;         (.remove (.-classList (.getElementById js/document id)) "is-active"))
      (doseq [id ["articles-checkbox" "tools-checkbox" "data-checkbox" "about-checkbox" "toggler"]]
             (set! (.-checked (.getElementById js/document id)) false)))

(defn navbar-clickable
      ([title href]
       [:a.navbar-item
        {:href href :on-click close-burger}  ;; Clickable navbar items should close the burger.
        [:span.navbar-content
         title]])
      ([title subtitle href]
       (-> (navbar-clickable title href)
           (assoc-in [2 2] [:p.is-size-7.has-text-info subtitle])))) ;; Append a vector that contains the subtitle

(defn root []
      [:nav.navbar.is-fixed-top.is-primary {:role "navigation"}

       [:input#toggler.toggler {:type "checkbox"}]
       [:div.navbar-brand
        [:a.navbar-item {:href (rfe/href :dcs.prototype-6.router/home-view)}
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

         ;; Articles
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#articles-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "articles-checkbox"} "Articles"]
          [:div#articles-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Learn from data-based articles"]]
           (navbar-clickable "Stirling's bin collection"
                             [:span "Interesting facts about Stirling's"
                              [:br] "bin collection of household waste"]
                             (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view))
           (navbar-clickable "Stirling Community Food"
                             [:span "Graphs indicating how this organisation"
                              [:br] "is reducing a community's food waste"]
                             (rfe/href :dcs.prototype-6.router/stirling-community-food-view))
           (navbar-clickable "Household waste analysis"
                             [:span "What do households put into their bins"
                              [:br] "and how appropriate are their disposal"
                              [:br] "decisions?"]
                             (rfe/href :dcs.prototype-6.router/household-waste-analysis-view))
           ]]

         ;; Tools
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#tools-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "tools-checkbox"} "Tools"]
          [:div#tools-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Explore using tools"]]
           (navbar-clickable "Waste by region"
                             [:span "Discover and compare regional"
                              [:br] "waste figures"]
                             (rfe/href :dcs.prototype-6.router/dashboard-view))
           (navbar-clickable "Waste sites"
                             [:span "Waste sites and the quantities of"
                              [:br] "materials that they receive"]
                             (rfe/href :dcs.prototype-6.router/waste-sites-map-view))
           (navbar-clickable "Household waste"
                             [:span "Household quanitites through time"]
                             (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hw-mgmt"}))
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e"]
                             [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e through time,"
                              [:br] "on a map"]
                             (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hw-co2e"}))
           (navbar-clickable "Household vs business quantities"
                             [:span "Household vs business quantities"
                              [:br] "through time, on a map"]
                             (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hvb"}))]]

         ;; Data
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#data-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "data-checkbox"} "Data"]
          [:div#data-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About the data on this site"]]
           (navbar-clickable "Introduction"
                             [:span "An introduction to our " [:em "easier-to-use"] [:br]
                              "datasets and their dimensions"]
                             (rfe/href :dcs.prototype-6.router/easier-open-data-view))

           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Directly access the dataset files"]]
           (navbar-clickable  [:span.icon-text
                               [:span "Household waste " [:span.has-text-info.is-size-7 "(JSON)"]]
                               [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "household-waste.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "household-co2e.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Biz. waste by region " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "business-waste-by-region.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Biz. waste by sector " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "business-waste-by-sector.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Waste site " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "waste-site.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Waste site ins & outs " [:span.has-text-info.is-size-7 "(CSV)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "waste-site-io.csv"))
           (navbar-clickable [:span.icon-text
                              [:span "Stir. bin collection " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "stirling-bin-collection.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Material coding " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "material-coding.json"))
           (navbar-clickable [:span.icon-text
                              [:span "EWC coding " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "ewc-coding.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Households " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "households.json"))
           (navbar-clickable [:span.icon-text
                              [:span "Population " [:span.has-text-info.is-size-7 "(JSON)"]]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             (str util/easier-repo-data "population.json"))]]

         ;; About
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About this site"]]
           (navbar-clickable "This site"
                             (rfe/href :dcs.prototype-6.router/todo-view))
           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About the encompassing project"]]
           (navbar-clickable [:span.icon-text
                              [:span "Blog site"]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             [:span "For further information about the" [:br]
                              "project and its activities"]
                             "https://campuspress.stir.ac.uk/datacommonsscotland/")
           (navbar-clickable [:span.icon-text
                              [:span "GitHub repositories"]
                              [:span.icon.has-text-info.is-size-7 [:i.fas.fa-external-link-alt]]]
                             [:span "For some of the project’s longer-lifespan" [:br]
                              "outputs such as concepts/models, standards," [:br]
                              " research output and open source code."] ;; Explicit line breaking because I haven't figured out the Bulma CSS way of wrapping this text
                             "https://github.com/data-commons-scotland")]]]]])