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
         [:h1.title.is-5 "Stirling Community Food"]]]


       [:section.hero {:style {:backgroundColor "#980f3d"}} ;
        [:div.hero-body
         [:div.container
          [:div
           [:h1.title.is-5.has-text-weight-bold.has-text-success "Flow"]
           [:h2.subtitle.is-4.has-text-primary-light "An overview of the process"]]
          [:br]
          [flow/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container
          [:div
            [:h2.title.is-5.has-text-weight-bold.has-text-danger "Received"]
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

