(ns dcs.prototype-6.view.pivot-drilldown-and-plot
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]))

(def webapp-ctx (.. js/document -location -pathname))
(js/console.log (str "webapp-ctx: " webapp-ctx))

(defn root
  [route]
  (r/after-render (util/scroll-fn))
  
  (let [preset (-> route :parameters :path :preset)]

    [:div
     
     [:section.hero.is-small.has-background
      [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                            :alt "Data grid & graph image"}]
      [:div.hero-body
       [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5 "Data grid & graph tool"]
         [:h2.subtitle.is-6.has-text-info
          [:span "Explore the data using this tool which allows you to select, filter and visualise our datasets"]]]]]]
     
     [:section.section
      
      [:div.container
       [:div.columns.is-centered
        [:div.column.has-text-centered
         
         [:div.content.is-small
          [:blockquote
           [:p
            [:span.is-small.has-text-danger "NB: This is " [:em "work-in-progress"] "."]
            " At present, this tool is loaded with only subsets of our datasets."]]]
         
         [:figure.image.is-3by4
          [:iframe.has-ratio
           {:src         (str webapp-ctx "data-grid-and-graph/index.html?preset=" preset)
            :scrolling   "no"
            :border      "no"
            :frameborder "no"}]]
         
         ]]

       ]]]))

