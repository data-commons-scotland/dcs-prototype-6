(ns dcs.prototype-6.view.pivot-drilldown-and-plot)

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

           [:div.content
            [:blockquote
             [:p
              [:span.has-text-weight-bold.has-text-danger "NB: This is " [:em "work-in-progress"] "."]
              " The following data grid & graph tool is not yet integrated into this website."]]]

           [:figure.image.is-3by4
            [:iframe.has-ratio
             {:src         (str "https://data-commons-scotland.github.io/pivot-drilldown-and-plot/index.html?preset=" preset)
              :scrolling   "no"
              :border      "no"
              :frameborder "no"}]]

           ]]

         ]]]))

