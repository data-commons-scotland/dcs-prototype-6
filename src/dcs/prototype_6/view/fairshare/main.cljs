(ns dcs.prototype-6.view.fairshare.main
  (:require [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.fairshare.cars-worth :as cars-worth]
            [dcs.prototype-6.view.fairshare.amounts-reused :as amounts-reused]
            [dcs.prototype-6.view.fairshare.co2e-avoided :as co2e-avoided]))


(defn root []
      [:div

       [:section.hero {:style {:backgroundColor "#0f5499"}}
        [:div.hero-body
         [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5.has-text-success "Fairshare"]
         [:h2.subtitle.is-6.has-text-primary-light "Discover how many " [:em "cars worth"] " of CO" [:sub "2"] "e is avoided"
                              " each year because of this university-based, reuse store"]]

        [:div.content.has-text-primary-light
         [:quote "Fairshare is ...ETC ETC ...TODO"]]]]]


       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h1.title.is-5.has-text-weight-bold [:span "Understanding"]]
           [:h2.subtitle.is-4 [:span "Understanding the amount of CO" [:sub "2"] "e that has been avoided, in terms of cars"]]]
          [:br]
          [cars-worth/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container
          [:div
            [:h2.title.is-5.has-text-weight-bold.has-text-danger "Amounts reused"]
            [:h2.subtitle.is-4 "The amounts of reused materials"]]
          [:br]
          [amounts-reused/root]
          ]
         ]
        ]

       [:section.hero {:style {:backgroundColor "#f2dfce"}}
        [:div.hero-body
         [:div.container
          [:div
           [:h2.title.is-5.has-text-weight-bold.has-text-success [:span "CO" [:sub "2"] "e avoided"]]
           [:h2.subtitle.is-4 [:span "CO" [:sub "2"] "e is avoided because of reuse"]]]
          [:br]
          [co2e-avoided/root]
          ]
         ]
        ]

       ])

