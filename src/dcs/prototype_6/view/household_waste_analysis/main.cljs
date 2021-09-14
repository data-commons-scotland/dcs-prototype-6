(ns dcs.prototype-6.view.household-waste-analysis.main
  (:require [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.view.household-waste-analysis.composition :as composition]
            [dcs.prototype-6.view.household-waste-analysis.disposal :as disposal]
            [dcs.prototype-6.view.household-waste-analysis.detail :as detail]))


(defn root []
      [:div

       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Household waste analysis"]
         [:h2.subtitle.is-6.has-text-info "What do households put into their bins and and how appropriate are their disposal decisions?"]]

        [:div.container
         [:div.content
          [:p "...To provide an answer to that question, ZWS" [:sup "a"] " occasionally asks"
           " each of the 32 Scottish councils to sample their bin collections"
           " and to analyse their content."
           " This " [:em "compositional analysis"]  " uncovers the types and weights of the disposed of materials,"
           " and assesses the appropriateness of the disposal decisions"
           " (i.e. " [:em "was it put into the right bin?"] ")."]
          [:p "ZWS is considering publishing this data as " [:b "open data"] "."
           " This article is based on an anonymised" [:sup "b"] " " [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target "household-waste-analysis"})} "subset of the data"] "."]   
          [:p "But " [:span.has-text-danger [:em "\"What's in " [:b "my"] " bin?\""]] "..."
           " Well, the best that you can do here, with this anonymised subset of the data,"
           " is to select [from the graphs below] the " [:em "household type "] " that most closely matches with yours:"]
          [:ul
           [:li "Your property's location?"
            [:ul
             [:li [:span.has-text-warning "urban"]]
             [:li [:span.has-text-warning "rural"]]]]
           [:li "Your property's price?"
            [:ul
             [:li [:span.has-text-warning "£"] " - " [:a {:href "https://www.saa.gov.uk/council-tax/council-tax-bands/" :target "_blank"} "council tax band"] " A or B"]  
             [:li [:span.has-text-warning "££"] " - council tax band C or D"]
             [:li [:span.has-text-warning "£££"] " - council tax band E, F or G" [:span.has-text-info " (The data subset doesn't cover band H property.)"]]]]]
         
          [:div.content.is-small.has-text-info
           [:ol.is-lower-alpha
            [:li [:a {:href "https://www.zerowastescotland.org.uk" :target "_blank"} "ZWS"]
             " - Zero Waste Scotland - a not-for-profit environmental organisation, funded by the Scottish Government and European Regional Development Fund."]
            [:li "This subset of the data comes from one, anonymised council region;"
             " and to bins that it collected across the 2 periods of Nov/Dec 2013 and Mar 2014."]]]
          
          ;; ...Does that provide a good enough introduction to what it is that we're trying to convey through this page?
          ;; It would be nice to provide an additional, easier to understand "what's in just my bin" perspective with simplier info-graphics.
          ]]]

       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
            [:h2.title.is-5.has-text-weight-bold.has-text-success "Composition"]
            [:h2.subtitle.is-4 "What materials do households put into their bins?"]]
          [:br]
          [composition/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-danger "Disposal decisions"]
           [:h2.subtitle.is-4 "How appropriate are the disposal decisions that households make?"]]
          [:br]
          [disposal/root]
          ]
         ]
        ]

       [:section.hero
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-info "Detail"]
           [:h2.subtitle.is-4 "A more detailed look at the disposal decision per material"]]
          [:br]
          [detail/root]
          ]
         ]
        ]

       ])

