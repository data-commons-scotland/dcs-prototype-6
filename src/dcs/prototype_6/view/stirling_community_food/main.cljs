(ns dcs.prototype-6.view.stirling-community-food.main
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.stirling-community-food.flow :as flow]
            [dcs.prototype-6.view.stirling-community-food.received :as received]
            [dcs.prototype-6.view.stirling-community-food.outcomes :as outcomes]
            [dcs.prototype-6.view.stirling-community-food.footfall :as footfall]))


(defn root []
      [:div

       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Stirling Community Food"]
         [:h2.subtitle.is-6.has-text-info "Graphs indicating how this organisation is reducing a community's food waste"]]

        [:div.container
         [:div.content
          [:p [:a {:href "https://www.transitionstirling.org.uk/community-food"} "Stirling Community Food"]
           " is an charitable organisation that helps to reduce food waste in Stirling by"
           " collecting (from supermarkets & aggregators) excess and near-sell-by-date food,"
           " routing it away from waste bins, and making it available to the community (for free)."]
          [:p "Stirling Community Food are working on publishing their data as "
           [:b "open data"] ". The following graphs have been generated from their 2020-21 tonnage and footfall datasets."]]]]


       [:section.hero {:style {:backgroundColor "#980f3d"}} ;
        [:div.hero-body
         [:div.container
          [:div
           [:h1.title.is-5.has-text-weight-bold.has-text-success "Process overview"]
           [:h2.subtitle.is-4.has-text-primary-light "An overview of the flow of food materials"]]
          [:br]
          [flow/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container
          [:div
            [:h2.title.is-5.has-text-weight-bold.has-text-danger "Materials received"]
            [:h2.subtitle.is-4 "Sources and amounts of incoming materials"]]
          [:br]
          [received/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-success "Outcomes"]
           [:h2.subtitle.is-4 "Destinations and amounts of outgoing materials"]]
          [:br]
          [outcomes/root]
          ]
         ]
        ]

       [:section.hero
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-danger "Footfall"]
           [:h2.subtitle.is-4 "Visitor dates and numbers"]]
          [:br]
          [footfall/root]
          ]
         ]
        ]

       ])

