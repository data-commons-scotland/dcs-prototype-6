(ns dcs.prototype-6.view.waste-sites-map)

(defn root []
      []
      [:div

       [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Waste sites"]
         [:h2.subtitle.is-6.has-text-info "Waste sites and the quantities of materials they receive"]]

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
             {:src         "https://data-commons-scotland.github.io/cluster-map-of-materials-incoming/"
              :scrolling   "no"
              :border      "no"
              :frameborder "no"}]]

           ]]

         ]]])

