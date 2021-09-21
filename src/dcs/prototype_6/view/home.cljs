(ns dcs.prototype-6.view.home
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [dcs.prototype-6.util :as util]))

#_(defn leaf-tile [title subtitle colour href img-src]
      [:div.tile.is-child
       [:a.box.has-text-centered {:href href}
        [:img {:src img-src :alt title :height "128" :width "128"}]
        [:h1.subtitle.is-italic.is-3 {:class colour} title]
        [:p subtitle]]])

(defn leaf-tile [title title-colour  subtitle body href img-src]
      [:div#standout.tile.is-child 
       [:a {:href href}
       [:div.card.is-shadowless {:style {:backgroundColor "#fff1e5"}}
        [:div.card-image
         [:figure.image.is-2by1
          [:img {:src img-src :alt title}]]]
        [:div.card-content.pt-2.pr-1.pl-1
         [:div
          [:h2.title.is-7.has-text-weight-bold {:style {:color title-colour}} title]
          [:h2.subtitle.is-5.has-text-danger subtitle]]
         [:p.content.is-small body]
         ]]]])

(defn leaf-tile-red [title subtitle body href img-src]
           [:div#standout.tile.is-child
            [:a {:href href}
             [:div.card.is-shadowless {:style {:backgroundColor "#980f3d"}}
              [:div.card-image
               [:figure.image.is-2by1
                [:img {:src img-src :alt title}]]]
              [:div.card-content.pt-2.pr-1.pl-1
               [:div
                #_[:h2.title.is-6.has-text-weight-bold.has-text-success title]
                [:h2.subtitle.is-5.has-text-link subtitle]]
               [:p.content.is-small.has-text-primary-light body]
               ]]]])

(defn leaf-tile-blue [title subtitle body href img-src]
      [:div#standout.tile.is-child
       [:a {:href href}
        [:div.card.is-shadowless {:style {:backgroundColor "#0f5499"}}
         [:div.card-image
          [:figure.image.is-2by1
           [:img {:src img-src :alt title}]]]
         [:div.card-content.pt-2.pr-1.pl-1
          [:div
           #_[:h2.title.is-6.has-text-weight-bold.has-text-success]
           [:h2.subtitle.is-5.has-text-link subtitle]]
          [:p.content.is-small.has-text-primary-light body]
          ]]]])

(defn leaf-tile-blank []
  [:div.tile.is-child
   
    [:div.card.is-shadowless {:style {:backgroundColor "#fff1e5"}}
     [:div.card-image
      ]
     [:div.card-content.pt-2.pr-1.pl-1
      [:div
       ]
      [:p.content.is-small ""]]]])

(defn root []
  (r/after-render (util/scroll-fn))
  
  [:div

   [:section.hero.is-small.has-background.is-primary
    [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                          :alt "The Fair Share image"}]
    [:div.hero-body
     [:div.container
      [:div.content.has-text-centered
       [:h2.subtitle.is-5  {:style {:margin 0}}
        "Helping non-experts learn about waste management in Scotland through Open Data" 
        #_"Discover and learn from the data about waste in Scotland"]]]]]


   [:section.hero {:style {:backgroundColor "#fff1e5"}}
    [:div.hero-body
     [:div.container

      [:div.tile.is-ancestor

       [:div.tile.is-vertical.is-parent.is-3 #_{:style {:backgroundColor "#f2dfce"}}
        (leaf-tile "Government data"
                   "#1f77b4"
                   "Household waste analysis"
                   "What do households put into their bins and how appropriate are their disposal decisions?"
                   (rfe/href :dcs.prototype-6.router/household-waste-analysis-view)
                   "img/analysing-waste-composition.jpg")

        (leaf-tile "Government data"
                   "#1f77b4"
                   "Waste by region"
                   "How is waste in your region? Explore and compare regional waste amounts."
                   (rfe/href :dcs.prototype-6.router/dashboard-view)
                   "img/regional-dashboard.png")
        
        (leaf-tile "Local reuse organisation data"
                   "#fdae6b"
                   "Stirling Community Food"
                   "Discover, through data, how this project is reducing a community's wasted food."
                   (rfe/href :dcs.prototype-6.router/stirling-community-food-view)
                   "img/stirling-community-food.png")]

       [:div.tile.is-vertical.is-parent.is-3 #_{:style {:backgroundColor "floralwhite"}}
        (leaf-tile "Government data"
                   "#1f77b4"
                   "Waste sites"
                   "Explore the location of waste sites and quantities of materials that they receive."
                   (rfe/href :dcs.prototype-6.router/waste-sites-map-view)
                   "img/waste-site.jpeg")
        
        (leaf-tile "Local reuse organisation data"
                   "#fdae6b"
                   "ACE furniture"
                   [:span "Find out about the amounts and types of furniture that are being reused because of this initiative."]
                   (rfe/href :dcs.prototype-6.router/ace-furniture-view)
                   "img/ace-furniture.png")

        (leaf-tile "Government data"
                   "#1f77b4"
                   "Stirling's bin collection"
                   "Interesting facts about Stirling's bin collection of household waste."
                   (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)
                   "img/looking-at-bin-collections.jpeg")]

       [:div.tile.is-vertical.is-parent.is-3
        (leaf-tile "Local reuse organisation data"
                   "#fdae6b"
                   "The Fair Share"
                   [:span "Discover how many cars worth of CO" [:sub "2"] "e is avoided each year because of this university based, reuse store."]
                   (rfe/href :dcs.prototype-6.router/fairshare-view)
                   "img/fairshare.png")

        (leaf-tile "Government data"
                   "#1f77b4"
                   "Waste through the decade"
                   [:span "Discover how waste, recycling and CO" [:sub "2"] "e amounts have been changing across Scotland over the last 10 years"]
                   (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hw-mgmt"})
                   "img/map-mgmt.gif")
        
        (leaf-tile-blank)
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
                        (rfe/href :dcs.prototype-6.router/about-view)
                        "img/project-team.png")
        
        (leaf-tile-blank)]
       ]
      ]
     ]
    ]

   ])