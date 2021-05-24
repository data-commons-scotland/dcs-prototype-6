(ns dcs.prototype-6.view.home
  (:require [reitit.frontend.easy :as rfe]))

#_(defn leaf-tile [title subtitle colour href img-src]
      [:div.tile.is-child
       [:a.box.has-text-centered {:href href}
        [:img {:src img-src :alt title :height "128" :width "128"}]
        [:h1.subtitle.is-italic.is-3 {:class colour} title]
        [:p subtitle]]])

(defn leaf-tile [title subtitle body href img-src]
      [:div.tile.is-child
       [:a {:href href }
       [:div.card.is-shadowless {:style {:backgroundColor "#fff1e5"}}
        [:div.card-image
         [:figure.image.is-2by1
          [:img {:src img-src :alt title}]]]
        [:div.card-content.pt-2.pr-1.pl-1
         [:div
          [:h2.title.is-6.has-text-weight-bold.has-text-danger title]
          [:h2.subtitle.is-5 subtitle]]
         [:p.content.is-small body]
         ]]]])

(defn leaf-tile-red [title subtitle body href img-src]
           [:div.tile.is-child
            [:a {:href href }
             [:div.card.is-shadowless {:style {:backgroundColor "#980f3d"}}
              [:div.card-image
               [:figure.image.is-2by1
                [:img {:src img-src :alt title}]]]
              [:div.card-content.pt-2.pr-1.pl-1
               [:div
                [:h2.title.is-6.has-text-weight-bold.has-text-success title]
                [:h2.subtitle.is-5.has-text-primary-light subtitle]]
               [:p.content.is-small.has-text-primary-light body]
               ]]]])

(defn leaf-tile-blue [title subtitle body href img-src]
      [:div.tile.is-child
       [:a {:href href }
        [:div.card.is-shadowless {:style {:backgroundColor "#0f5499"}}
         [:div.card-image
          [:figure.image.is-2by1
           [:img {:src img-src :alt title}]]]
         [:div.card-content.pt-2.pr-1.pl-1
          [:div
           [:h2.title.is-6.has-text-weight-bold.has-text-success]
           [:h2.subtitle.is-5.has-text-primary-light subtitle]]
          [:p.content.is-small.has-text-primary-light body]
          ]]]])

(defn root []
      [:div

       [:section.hero.is-small {:style {:backgroundColor "#f2dfce"}} ;
        [:div.hero-body
         [:div.container
          [:div.content.has-text-centered
           [:h2.subtitle.is-4 "Discover and learn from the data about waste in Scotland"]]]]]


       [:section.hero {:style {:backgroundColor "#fff1e5"}}
        [:div.hero-body
         [:div.container

          [:div.tile.is-ancestor

           [:div.tile.is-vertical.is-parent.is-3
            (leaf-tile "Article"
                       "Household waste analysis"
                       "What do households put into their bins and how appropriate are their disposal decisions?"
                       (rfe/href :dcs.prototype-6.router/household-waste-analysis-view)
                       "img/analysing-waste-composition.jpg")

            (leaf-tile "Tool"
                       "Waste by region"
                       "How is waste in your region? Explore and compare regional waste amounts."
                       (rfe/href :dcs.prototype-6.router/regional-dashboard-view)
                       "img/regional-dashboard.png")]

           [:div.tile.is-vertical.is-parent.is-3
            (leaf-tile "Tool"
                       "Waste sites"
                       "Explore the location of waste sites and quantities of materials that they receive."
                       (rfe/href :dcs.prototype-6.router/todo-view)
                       "img/waste-site.jpeg")

            (leaf-tile "Article"
                       "Stirling's bin collection"
                       "Interesting facts about Stirling's bin collection of household waste."
                       (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)
                       "img/looking-at-bin-collections.jpeg")]

           [:div.tile.is-vertical.is-parent.is-3
            (leaf-tile "Article"
                       "Stirling Community Food"
                       "Discover, through data, how this project is reducing a community's wasted food."
                       (rfe/href :dcs.prototype-6.router/stirling-community-food-view)
                       "img/avoid-wasting-food.png")


            (leaf-tile "Tool"
                       "Waste through the decade"
                       [:span "Discover how waste, recycling and CO" [:sub "2"] "e amounts have been changing across Scotland through the last 10 years"]
                       (rfe/href :dcs.prototype-6.router/waste-through-time-map-view)
                       "img/map-mgmt.gif")
            ]

           [:div.tile.is-vertical.is-parent.is-3
            (leaf-tile-red "Data"
                       [:span [:em "Easier"] " data"]
                       [:span "Find out about our " [:em "easier"] " to use datasets, their dimensions, and how to download them."]
                       (rfe/href :dcs.prototype-6.router/easier-open-data-view)
                       "img/download-our-datasets.png")

            (leaf-tile-blue "About"
                       "This site and the project"
                       "Find out about the ideas behind this website and about its encompassing project, Data Commons Scotland."
                       (rfe/href :dcs.prototype-6.router/todo-view)
                       "img/project-team.png")]
           ]
          ]
         ]
        ]


       ])