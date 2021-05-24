(ns dcs.prototype-6.view.household-waste-analysis.main
  (:require [dcs.prototype-6.state :as state]
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
         [:blockquote [:p.has-text-weight-bold.has-text-danger "NB: This is very much " [:em "work-in-progress"] "."]
          [:p "TODO:"]
          [:ul
           [:li "Introduce what it is that we're trying to convey through this page."]
           [:li "Provide an additional(?), easier to understand \"" [:em "what's in just " [:b "my"] " bin"] "\" perspective with simplier info-graphics."]]]
          [:p "The information in this page has been derived from a sample dataset that was supplied by"
           "Zero Waste Scotland" [:sup "1"] "."]
          [:div.content.is-small.has-text-info
           [:ol
            [:li [:a {:href "https://www.zerowastescotland.org.uk" :target "_blank"} "Zero Waste Scotland"]
             " not-for-profit environmental organisation, funded by the Scottish Government and European Regional Development Fund."]]]]]]

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

