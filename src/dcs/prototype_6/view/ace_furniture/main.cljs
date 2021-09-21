(ns dcs.prototype-6.view.ace-furniture.main
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.view.ace-furniture.counts :as counts]
            [dcs.prototype-6.view.ace-furniture.weights :as weights]
            [dcs.prototype-6.view.ace-furniture.co2e :as co2e]
            [dcs.prototype-6.view.ace-furniture.trends :as trends]))


(defn root []
  (r/after-render (util/scroll-fn))
  
  [:div

   [:section.hero.is-small.has-background.is-info
    [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                          :alt "ACE furniture image"}]
    [:div.hero-body
     [:div.container
      [:div.content.has-text-centered
       [:h1.title.is-5 "The ACE furniture reuse initiative"]
       [:h2.subtitle.is-6.has-text-primary "Find out about the amounts and types of furniture that are being reused because of this initiative."]]]]]
   
   [:section.hero.is-info ;{:style {:backgroundColor "#0f5499"}}
    [:div.hero-body
     [:div.container

      [:div.content
       [:p "The ACE" [:sup.has-text-danger "a"] " furniture reuse initiative has been running since 1984(!), helping prevent furniture from becoming waste."]
       [:p "ACE is in the process of publishing " 
        [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target "ace-furniture-count"})} [:span.has-text-link-dark "its data"]] " as " [:b "open data"] "."
        " This article is based on an draft of that work."]]
      [:div.content.is-small.has-text-danger
       [:ol.is-lower-alpha
        [:li [:a {:href   "https://ace.scot/"
                  :target "_blank"} [:span.has-text-link-dark "ACE"]]
         " - Alloa Community Enterprises - is a charity, community enterprise and second-hand furniture outlet based near Alloa."]]]]]]


   [:section.hero {:style {:backgroundColor "#f2dfce"}}
    [:div.hero-body
     [:div.container
      [:div
       [:h1.title.is-5.has-text-weight-bold.has-text-success "Counts"]
       [:h2.subtitle.is-4 [:span "This helps compare the relative " [:em "popularities"] " of the sold furniture types"]]]
      [:br]
      [counts/root]
      ]
     ]
    ]


   [:section.hero {:style {:backgroundColor "#fff1e5"}}
    [:div.hero-body
     [:div.container
      [:div
       [:h2.title.is-5.has-text-weight-bold.has-text-danger "Weights"]
       [:h2.subtitle.is-4 "This helps compare the relative weights of the sold furniture types"]]
      [:br]
      [weights/root]
      ]
     ]
    ]
   
   [:section.hero {:style {:backgroundColor "#f2dfce"}}
    [:div.hero-body
     [:div.container
      [:div
       [:h1.title.is-5.has-text-weight-bold.has-text-success "CO" [:sub "2"] "e avoided"]
       [:h2.subtitle.is-4 [:span "This helps us comprehend (in terms of flights) the amounts of CO" [:sub "2"] "e that has been avoided"]]]
      [:br]
      [co2e/root]]]]
   
   [:section.hero
    [:div.hero-body
     [:div.container
      [:div
       [:h2.title.is-5.has-text-weight-bold.has-text-success "Trends"]
       [:h2.subtitle.is-4 "This helps show the trends across the furniture types"]]
      [:br]
      [trends/root]
      ]
     ]
    ]

   ])

