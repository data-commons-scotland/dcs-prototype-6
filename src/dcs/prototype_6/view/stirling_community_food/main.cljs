(ns dcs.prototype-6.view.stirling-community-food.main
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.view.stirling-community-food.flow :as flow]
            [dcs.prototype-6.view.stirling-community-food.received :as received]
            [dcs.prototype-6.view.stirling-community-food.outcomes :as outcomes]
            [dcs.prototype-6.view.stirling-community-food.footfall :as footfall]))


(defn root []
  (r/after-render (util/scroll-fn))

  [:div
   
   [:section.hero.is-small.has-background
    [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                          :alt "Stirling Community Food image"}]
    [:div.hero-body
     [:div.content.has-text-centered
      [:h1.title.is-5 "Stirling Community Food"]
      [:h2.subtitle.is-6.has-text-info "Graphs indicating how this organisation is reducing a community's food waste"]]]]

   [:section.section

    [:div.container
     [:div.content
      [:p "Stirling Community Food" [:sup "1"] " is a project that helps to reduce food waste in Stirling by"
       " collecting (from supermarkets & aggregators) excess and near-sell-by-date food,"
       " routing it away from waste bins, and making it available (for free) to the community."]
      [:p "Stirling Community Food are working on publishing their data as "
       [:b "open data"] ". The following graphs have been generated from their 2020-21 "
       [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target "stirling-community-food-tonnes"})} "tonnage"]
       " and "
       [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target "stirling-community-food-footfall"})} "footfall"]
       " datasets."]
      
      [:div.content.is-small.has-text-info
       [:ol
        [:li [:a {:href   "https://www.transitionstirling.org.uk/community-food"
                  :target "_blank"} "Stirling Community Food"]
         " is run by the charity Transition Stirling,"
         " and was set-up in partnership with the " [:a {:href   "https://www.facebook.com/thekitchenat44kingstreet/"
                                                         :target "_blank"} "Kitchen at 44"] "."]]]]]]


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

