(ns dcs.prototype-6.view.waste-sites-map
  (:require [reitit.frontend.easy :as rfe]))

(def webapp-ctx (.. js/document -location -pathname))

(defn root
      []

      [:div
       
       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Waste sites"]
         [:h2.subtitle.is-6.has-text-info "Waste sites and the quantities of materials they receive"]]

        [:div.container
         
         ;; map | ui hints
         [:div.columns.is-centered

          ;; map
          [:div.column.is-three-fifths
           [:figure.image.is-3by4
            [:iframe.has-ratio
             {:src         (str webapp-ctx "waste-sites-cluster-map/")
              :scrolling   "no"
              :border      "no"
              :frameborder "no"}]]]

          ;; ui hints
          [:div.column

           [:div.content.is-small
            [:blockquote
             [:p
              [:span.is-small.has-text-danger "NB: This is " [:em "work-in-progress"] "."]
              " At present, this map tool works only on some user devices."
              " Also, it currently includes only the materials incoming to waste sites during the year 2019."]]]

           [:div.content

            [:h3.subtitle.is-6.has-text-danger "Key elements"]
            [:p "Each pie chart depicts the amounts of materials incoming to a single waste site, or the aggregation of waste sites within a map area."]

             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block.is-48x48
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/single-waste-site-pie.png"}]]]
              [:column
               "Depicts a single waste site."]]
             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block.is-48x48
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/multi-waste-sites-pie.png"}]]]
              [:column
               "Depicts an aggregation of 26 waste sites."]]
             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block.is-32x32
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/no-pie.png"}]]]
              [:column
               "(I.e. a number without a surrounding pie chart) depicts a waste site with no incoming materials (probably because the site was not operational during 2019)."]]
             [:div.columns.is-flex.is-vcentered
              [:column
              [:figure.image.is-inline-block.is-256x256
               [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/segment-popup.png"}]]]
              [:column
              "Hovering the cursor over a pie segment will pop-up details about incoming tonnes of the material depicted by the segment."]]
             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block.is-128x128
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/area-highlighting.png"}]]]
              [:column
               "Hovering the cursor over a pie that depicts an aggregation will highlight the map area in which the aggregated waste sites are located."]]
             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/waste-site-popup.png"}]]]
              [:column
               "Clicking on a single waste site will pop-up details about that waste site."]]
             [:div.columns.is-flex.is-vcentered
              [:column
               [:figure.image.is-inline-block.is-64x64
                [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/zoom-control.png"}]]]
              [:column
               "The webapp supports the usual zoom and pan controls. The user can also double-click on an aggregation pie to zoom into the area that it covers."]]
             
             ]

           ]]
         
         ;; intro | data mapping
         [:div.columns.is-centered

          ;; intro
          [:div.column.is-three-fifths
           [:div.content

            [:h3.subtitle.is-6.has-text-danger "An introduction to this data"]
            [:p "SEPA publish a "
             [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view nil {:target "waste-site-io"})} "Site returns"]
             " dataset that says:"]
            [:ul
             [:li "how many tonnes"]
             [:li "of each ("
              [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view nil {:target "ewc-coding"})} "EWC coded"]
              ") waste material"]
             [:li "was moved in or out"]
             [:li "of each authorised waste site in Scotland."]]
            [:p "Here is an extract:"]
             [:figure.image.is-4by1.is-flex.is-justify-content-center
              [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/screenshot-sepa-site-returns-sample.png"
                     :alt "An extract of the 'Site returns' dataset"}]]
            [:p "This is impressive, ongoing data collection and curation by SEPA."]
            [:p [:em "But might some of its information be made more understandable to the general public by depicting it on a map?"]]
            [:p "Towards answering that, we have built this map tool."]
            
            
          ;; data mapping
            [:h3.subtitle.is-6.has-text-danger "Data mapping"]
            [:p "To aid comprehension, SEPA often sorts waste materials into 33 categories."
             " We do the same for this map tool, mapping each EWC coded waste material into 1 of the 33 categories:"]
             [:figure.image.is-128x128.is-flex.is-justify-content-center
              [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/material-categories-sample.png"
                     :alt "A sample of the 33 categories"}]]
            [:p "The Site returns dataset identifies waste sites by their Permit/Licence code."
             " We want our map to show additional information about each waste site."
             " Specifically, its name, council area, waste processing activities, client types,"
             " and (of course!) location."]
            [:p "SEPA holds that additional information about waste sites, in a second dataset: "
             [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view nil {:target "waste-site"})} "Waste sites and capacity summary"] "."
             " Our map tool uses the Permit/Licence codes to cross-reference between the 2 SEPA datasets."]
            ]]
            
            [:div.column]]]
        
        ]])

