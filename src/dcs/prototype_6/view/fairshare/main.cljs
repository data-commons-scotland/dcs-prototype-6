(ns dcs.prototype-6.view.fairshare.main
  (:require [dcs.prototype-6.view.fairshare.cars-worth :as cars-worth]
            [dcs.prototype-6.view.fairshare.per-material :as per-material]
            [dcs.prototype-6.view.fairshare.per-quarter :as per-quarter]))


(defn root []
      [:div

       [:section.hero {:style {:backgroundColor "#0f5499"}}
        [:div.hero-body
         [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5.has-text-success "The Fair Share"]
         [:h2.subtitle.is-6.has-text-primary-light "Discover how many " [:em "cars worth"] " of CO" [:sub "2"] "e is avoided"
                              " each year because of this university based, reuse store"]]

        [:div.content.has-text-primary-light
         [:p "The Fair Share" [:sup "a"] " is a university based, reuse store."
          " It accepts donations of second-hand books, clothes, kitchenware, electricals, etc. and sells these to students."]
         [:p "The Fair Share is in the process of publishing its data as " [:b "open data"] "."
          " This article is based on an draft of that work."]]
          [:div.content.is-small.has-text-info
           [:ol.is-lower-alpha
            [:li [:a {:href "https://www.stirlingstudentsunion.com/sustainability/fairshare/" :target "_blank"} "The Fair Share"]
             " is run by the Student Union at the University of Stirling. It meets the "
             [:a {:href "https://www.zerowastescotland.org.uk/revolve" :target "_blank"} "Revolve"] " quality standard for second-hand stores."]]]]]]


       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h1.title.is-5.has-text-weight-bold.has-text-success "CO" [:sub "2"] "e avoided"]
           [:h2.subtitle.is-4 [:span "Thinking about the amount of CO" [:sub "2"] "e that has been avoided, in terms of cars"]]]
          [:br]
          [cars-worth/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container
          [:div
            [:h2.title.is-5.has-text-weight-bold.has-text-danger "Materials reused"]
            [:h2.subtitle.is-4 "Analysing the per-material breakdown" #_"Categorising and weighing the reused materials"]]
          [:br]
          [per-material/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-success "Donation patterns"]
           [:h2.subtitle.is-4 "Looking at the donations over time"]]
          [:br]
          [per-quarter/root]
          ]
         ]
        ]

       ])

