(ns dcs.prototype-6.view.home
  (:require [reitit.frontend.easy :as rfe]))

(defn leaf-tile [title subtitle colour href img-src]
      [:div.tile.is-child
       [:a.box.has-text-centered {:href href}
        [:img {:src img-src :alt title :height "128" :width "128"}]
        [:h1.subtitle.is-italic.is-3 {:class colour} title]
        [:p subtitle]]])

(defn root []
      [:section.section

       [:div.content.has-text-centered
        [:h1.subtitle.is-4.has-text-danger [:em "Discover and learn from the data about waste in Scotland"]]]

       [:div.tile.is-ancestor

        [:div.tile.is-vertical.is-parent.is-4
         (leaf-tile "Who generates waste?"
                    "Origins? Households, shops, hospitals, industry."
                    "has-text-primary"
                    (rfe/href :dcs.prototype-6.router/todo-view)
                    "img/cartoon-waste-generation.svg")
         (leaf-tile "Waste by region"
                    [:span [:em "How is waste"] " in my area? Where can i dispose of " [:em "xyz?"] "?"]
                    "has-text-link"
                    (rfe/href :dcs.prototype-6.router/dashboard-view)
                    "img/cartoon-by-location.svg")]

        [:div.tile.is-vertical.is-parent.is-4
         (leaf-tile "What enters the waste stream?"
                    "Materials? Food stuffs, plastics, cardboard, clothing, etc.?"
                    "has-text-info"
                    (rfe/href :dcs.prototype-6.router/todo-view)
                    "img/cartoon-waste-materials.svg")

         (leaf-tile "Understanding this site"
                    "Where does the data come from? Reliability indicator? Missing data?"
                    "has-text-success"
                    (rfe/href :dcs.prototype-6.router/todo-view)
                    "img/cartoon-blackboard.svg")]

        [:div.tile.is-vertical.is-parent.is-4
         (leaf-tile "Where does waste end up?"
                    "What gets reused, recycled, incinerated, landfilled?"
                    "has-text-grey"
                    "https://data-commons-scotland.github.io/cluster-map-of-materials-incoming/"
                    "img/cartoon-waste-dump.svg")]]])