(ns dcs.prototype-6.navbar
  (:require [clojure.string :as str]
            [reitit.frontend.easy :as rfe]
            [goog.string :as gstring]
            [dcs.prototype-6.util :as util]))

(defn remove-class [el toggleable-class]
  (let [el-classList (.-classList el)]
    (when (.contains el-classList toggleable-class)
      (.remove el-classList toggleable-class))))

(defn toggle-class [el toggleable-class]
  (let [el-classList (.-classList el)]
    (if (.contains el-classList toggleable-class)
      (.remove el-classList toggleable-class)
      (.add el-classList toggleable-class))))

(defn toggle-burger
  "Expands/collapses the burger-ised menu"
  [_event]
  (let [burger-el (.getElementById js/document "topburger")
        ;burger-el (.-currentTarget _event)
        navbarmenu-id (.-target (.-dataset burger-el))
        navbarmenu-el (.getElementById js/document navbarmenu-id)]
    (toggle-class burger-el "is-active")
    (toggle-class navbarmenu-el "is-active")))

(defn collapse-burger
  "Collapses the burger-ised menu. Only works when the burger is in-play."
  []
  (let [burger-el (.getElementById js/document "topburger")
        navbarmenu-id (.-target (.-dataset burger-el))
        navbarmenu-el (.getElementById js/document navbarmenu-id)]
    (remove-class burger-el "is-active")
    (remove-class navbarmenu-el "is-active")))

(defn navbar-clickable
      ([title href]
       [:a.navbar-item
        {:on-click #(collapse-burger) :href href :target (if (str/starts-with? href "http") "_blank" "_self")}  ;; Clickable navbar items should close the burger.
        [:span.navbar-content
         [:span.has-text-link-dark title]]])
      ([title subtitle href]
       (-> (navbar-clickable title href)
           (assoc-in [2 2] [:p.is-size-7.has-text-info subtitle])))) ;; Append a vector that contains the subtitle

(defn root []
      [:nav.navbar.is-fixed-top.is-primary {:role "navigation"}

       [:div.navbar-brand
        [:a.navbar-item {:href (rfe/href :dcs.prototype-6.router/home-view)}
         [:img.brand-logo {:src "img/dcs-circle.png" :alt "Waste Matters Scotland logo"}]
         "Waste Matters Scotland"]
        [:a.navbar-item]

        [:span#topburger.navbar-burger.burger {:data-target "topnavbar" :on-click #(toggle-burger %) }
         [:span]
         [:span]
         [:span]]]

       [:div#topnavbar.navbar-menu

        [:div.navbar-start
         #_[:a.navbar-item {:href "#"} "Left side items beside the brand logo"]]

        [:div.navbar-end

         ;; Articles
         [:div.navbar-item.has-dropdown.is-hoverable
          [:label.navbar-link "Articles"]
          [:div.navbar-dropdown.is-right
           
           [:div.navbar-item
            [:p.has-text-weight-bold "Learn from data-based articles"]]
           (navbar-clickable "Stirling's bin collection"
                             [:span "Interesting facts about Stirling's"
                              [:br] "bin collection of household waste"]
                             (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view))
           (navbar-clickable "Stirling Community Food"
                             [:span "Graphs indicating how this organisation"
                              [:br] "is reducing a community's food waste"]
                             (rfe/href :dcs.prototype-6.router/stirling-community-food-view))
           (navbar-clickable "The Fair Share"
                             [:span "Discover how many " [:em "cars worth"] " of CO" [:sub "2"] "e is avoided"
                              [:br] "each year by this university based, reuse store."]
                             (rfe/href :dcs.prototype-6.router/fairshare-view))
           (navbar-clickable "The ACE furniture reuse initiative"
                             [:span "Find out about the amounts and types of furniture"
                              [:br] "that are being reused because of this initiative."]
                             (rfe/href :dcs.prototype-6.router/ace-furniture-view))
           (navbar-clickable "Household waste analysis"
                             [:span "What do households put into their bins"
                              [:br] "and how appropriate are their disposal"
                              [:br] "decisions?"]
                             (rfe/href :dcs.prototype-6.router/household-waste-analysis-view))
           ]]

         ;; Tools
         [:div.navbar-item.has-dropdown.is-hoverable
          [:label.navbar-link "Tools"]
          [:div.navbar-dropdown.is-right
           
           [:div.navbar-item
            [:p.has-text-weight-bold "Explore using tools"]]
           (navbar-clickable "Waste by region"
                             [:span "Discover and compare regional"
                              [:br] "waste figures"]
                             (rfe/href :dcs.prototype-6.router/dashboard-view))
           (navbar-clickable "Waste sites"
                             [:span "Waste sites and the quantities of"
                              [:br] "materials that they receive"]
                             (rfe/href :dcs.prototype-6.router/waste-sites-map-view))
           (navbar-clickable "Waste through the decade"
                             [:span "Discover how waste, recycling and"
                              [:br] "CO" [:sub "2"] "e amounts have been changing"
                              [:br] "across Scotland over the last 10yrs"]
                             (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hw-mgmt"}))
           (navbar-clickable "Data grid & graph tool"
                             [:span "Explore the data using this tool"
                              [:br] "which allows you to select, filter"
                              [:br] "and visualise our datasets"]
                             (rfe/href :dcs.prototype-6.router/pivot-drilldown-and-plot-view {:preset "orig"}))]]

         ;; Data
         [:div.navbar-item.has-dropdown.is-hoverable
          [:label.navbar-link "Data"]
          [:div.navbar-dropdown.is-right
           
           [:div.navbar-item
            [:p.has-text-weight-bold "About the data on this site"]]
           (navbar-clickable "Introduction"
                             [:span "An introduction to our " [:em "easier-to-use"] [:br]
                              "datasets and their dimensions"]
                             (rfe/href :dcs.prototype-6.router/easier-open-data-view))

           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-weight-bold "Directly access the dataset files"]]
           (navbar-clickable  [:span "Household waste " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "household-waste.json"))
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "household-co2e.json"))
           (navbar-clickable [:span "Business waste by region " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "business-waste-by-region.json"))
           (navbar-clickable [:span "Business waste by sector " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "business-waste-by-sector.json"))
           (navbar-clickable [:span "Waste site " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "waste-site.json"))
           (navbar-clickable [:span "Waste site ins & outs " [:span.has-text-info.is-size-7 "(CSV)"]]
                             (str util/easier-repo-data "waste-site-io.csv"))
           (navbar-clickable [:span "Stirling bin collection " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "stirling-bin-collection.json"))
           (navbar-clickable [:span "Material coding " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "material-coding.json"))
           (navbar-clickable [:span "EWC coding " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "ewc-coding.json"))
           (navbar-clickable [:span "CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e multipiers " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "co2e-multiplier.json"))
           (navbar-clickable [:span "Households " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "households.json"))
           (navbar-clickable [:span "Population " [:span.has-text-info.is-size-7 "(JSON)"]]
                             (str util/easier-repo-data "population.json"))]]

         ;; About
         [:a.navbar-item
           {:on-click #(collapse-burger)
            :href (rfe/href :dcs.prototype-6.router/about-view)}
           [:span (gstring/unescapeEntities "&nbsp;") "About" (gstring/unescapeEntities "&nbsp;")]]]]])