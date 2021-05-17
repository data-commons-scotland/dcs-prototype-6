(ns dcs.prototype-6.navbar
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))


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

         ;; Explore
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#explore-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "explore-checkbox"} "Explore"]
          [:div#explore-dropdown.navbar-dropdown.is-right
           (navbar-clickable "Waste by region"
                             [:span "Discover and compare regional"
                              [:br] "waste figures"]
                             (rfe/href :dcs.prototype-6.router/dashboard-view))
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
           (navbar-clickable "Waste sites"
                             [:span "Waste sites and the quantities of"
                              [:br] "incoming materials through time, on a map"]
                             "https://data-commons-scotland.github.io/cluster-map-of-materials-incoming/")
           (navbar-clickable "Household waste"
                             [:span "Household quanitites through time"]
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hw-mgmt")
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e"]
                             [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e through time,"
                              [:br] "on a map"]
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hw-co2e")
           (navbar-clickable "Household vs business quantities"
                             [:span "Household vs business quantities"
                              [:br] "through time, on a map"]
                             "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=hvb")]]

         ;; Data
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#data-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "data-checkbox"} "Data"]
          [:div#data-dropdown.navbar-dropdown.is-right
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "About the data that is used on this site"]]
           (navbar-clickable "Datasets"
                             [:span "An introduction to our " [:em "easier-to-use"] " datasets"]
                             "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/")
           (navbar-clickable "Dimensions"
                             "Descriptions of the dimensions of the datasets"
                             "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/index.html#dimensions")
           [:hr.navbar-divider]
           [:div.navbar-item
            [:p.has-text-link.has-text-weight-bold "Directly access the datasets" [:span.has-text-weight-normal.has-text-info " (as CSVs)"]]]
           (navbar-clickable [:span "Household waste " [:span.has-text-info "(19,008 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-waste.csv")
           (navbar-clickable [:span "Household CO" [:span {:dangerouslySetInnerHTML {:__html "<sub>2</sub>"}}] "e " [:span.has-text-info "(208 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/household-co2e.csv")
           (navbar-clickable [:span "Business waste by region " [:span.has-text-info "(8,976 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-region.csv")
           (navbar-clickable [:span "Business waste by sector " [:span.has-text-info "(2,640 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/business-waste-by-sector.csv")
           (navbar-clickable [:span "Waste site " [:span.has-text-info "(1254 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site.csv")
           (navbar-clickable [:span "Waste site ins & outs " [:span.has-text-info "(2,667,914 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/waste-site-io.csv")
           (navbar-clickable [:span "Stirling bin collection " [:span.has-text-info "(127 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/stirling-bin-collection.csv")
           (navbar-clickable [:span "Material coding " [:span.has-text-info "(557 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/material-coding.csv")
           (navbar-clickable [:span "EWC coding " [:span.has-text-info "(973 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/ewc-coding.csv")
           (navbar-clickable [:span "Households " [:span.has-text-info "(288 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/households.csv")
           (navbar-clickable [:span "Population " [:span.has-text-info "(288 records)"]]
                             "https://github.com/data-commons-scotland/dcs-easier-open-data/raw/master/data/population.csv")]]

         ;; About
         [:div.navbar-item.has-dropdown.is-hoverable
          [:input#about-checkbox {:type "checkbox"}]
          [:label.navbar-link {:for "about-checkbox"} "About"]
          [:div#about-dropdown.navbar-dropdown.is-right
           (navbar-clickable "About the encompassing project"
                             "https://github.com/data-commons-scotland")
           (navbar-clickable "GitHub repositories"
                             [:span "For some of the project’s longer-lifespan outputs" [:br]
                              "such as concepts/models, standards, research output" [:br]
                              "and open source code."] ;; Explicit line breaking because I haven't figured out the Bulma CSS way of wrapping this text
                             "https://github.com/data-commons-scotland")]]]]])