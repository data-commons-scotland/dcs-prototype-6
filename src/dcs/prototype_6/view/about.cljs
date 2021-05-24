(ns dcs.prototype-6.view.about)

(defn root
      []
      [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "About this site"]
         ;[:h2.subtitle.is-6.has-text-info "About this site and the project"]
         ]

       [:div.container
        [:div.content

         [:p "This is the " [:b "Waste Matters Scotland"] " website."]

         [:div.columns

          [:div.column.is-two-thirds

           [:blockquote [:p "NB: This prototype-6 version of the website is a " [:em "work-in-progress"]
                         ". Its primary purpose is to prototype, trial & showcase ideas that will form parts of the eventual, final version of the website."]]
           [:p "The development of a website is one of the outcomes of the "
            [:a {:href "https://campuspress.stir.ac.uk/datacommonsscotland/" :target "_blank"} "Data Commons Scotland"] " research project ("
            [:a {:href "https://gow.epsrc.ukri.org/NGBOViewGrant.aspx?GrantRef=EP/S027521/1" :target "_blank"} "funded"] " by the EPSRC)."]
         ]

          [:div.column

           [:figure.image.is-128x128
            [:img.is-rounded {:src "img/dcs-circle.png" :alt "Data Commons Scotland - icon"}]]

           ]]

          [:p "The objectives for this website include:"]
           [:ul
            [:li "Help its user community discover, learn about and understand the open data about waste management in Scotland."]
            [:li "Be a demonstrator of the findings from the encompassing research project,
            and be an archetype for future portals onto other categories of open data."]]

        ]]])