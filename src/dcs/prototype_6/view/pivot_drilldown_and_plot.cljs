(ns dcs.prototype-6.view.pivot-drilldown-and-plot)

(def webapp-ctx (.. js/document -location -pathname))
(js/console.log (str "webapp-ctx: " webapp-ctx))

(defn root
      [route]
      (let [preset (-> route :parameters :path :preset)]

      [:div

       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Data grid & graph tool"]
         [:h2.subtitle.is-6.has-text-info
          [:span "Explore the data using this tool which allows you to select, filter and visualise our datasets"]]]
   
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

