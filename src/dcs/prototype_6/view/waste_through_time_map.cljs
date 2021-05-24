(ns dcs.prototype-6.view.waste-through-time-map)

(defn root
      [route]
      (let [preset (-> route :parameters :path :preset)]

      [:div

       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Waste amounts through time"]
         [:h2.subtitle.is-6.has-text-info
          [:span "Discover how waste, recycling and CO" [:sub "2"] "e amounts have been changing across Scotland through the last 10 years"]]]

        [:div.container

         [:div.columns.is-centered
          [:div.column.has-text-centered

           [:div.content
            [:blockquote
             [:p
              [:span.has-text-weight-bold.has-text-danger "NB: This is " [:em "work-in-progress"] "."]
              " The following map tool is not yet integrated into this website."]]]

           [:figure.image.is-3by4
            [:iframe.has-ratio
             {:src         (str "https://data-commons-scotland.github.io/waste-quants-thru-time-on-a-map/index.html?preset=" preset)
              :scrolling   "no"
              :border      "no"
              :frameborder "no"}]]

           ]]

         ]]]))

