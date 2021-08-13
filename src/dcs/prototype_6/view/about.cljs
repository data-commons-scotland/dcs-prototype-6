(ns dcs.prototype-6.view.about)

(defn root
  []
  [:section.section
  
   [:div.content.has-text-centered
    [:h1.title.is-5 "About this site"]
    [:h2.subtitle.is-6.has-text-info "This is the " [:em [:b "Waste Matters Scotland"]] " website."]]

   [:div.container
    [:div.content

     [:div.notification.is-info [:p "NB: This prototype-6 version of the website is a " 
                                 [:em "work-in-progress"]
                                 ". Its primary purpose is to prototype, trial & showcase ideas that will form parts of the eventual, final version of the website."]]
     
     [:div.content.is-flex.is-align-items-center
        
         [:img.is-rounded {:src "img/dcs-circle.png"
                           :alt "Data Commons Scotland - icon"
                           :width "64px"}]
       
        [:div.content.ml-3
         "The development of a website is one of the outcomes of the "
         [:a {:href   "https://campuspress.stir.ac.uk/datacommonsscotland/"
              :target "_blank"} "Data Commons Scotland"] " research project ("
         [:a {:href   "https://gow.epsrc.ukri.org/NGBOViewGrant.aspx?GrantRef=EP/S027521/1"
              :target "_blank"} "funded"] " by the EPSRC)."]]

     [:p "The objectives for this website include:"]
     [:ul
      [:li "Help its user community discover, learn about and understand the open data about waste management in Scotland."]
      [:li "Be a demonstrator of the findings from the encompassing research project,
            and be an archetype for future portals onto other categories of open data."]]
     
     
       [:div.notification.has-text-primary-light {:style {:backgroundColor "#0f5499"}}
        [:p.has-text-weight-bold "Find out more from the project's..."]
        [:ul 
         [:li [:a {:href   "https://campuspress.stir.ac.uk/datacommonsscotland/"
                   :target "_blank"} "blog site"]
          " - which provides much more information about the project itself, and its activities."]
         [:li [:a {:href   "https://github.com/data-commons-scotland"
                   :target "_blank"} "GitHub repositories"]
          " - which contains some of the project’s longer-lifespan" 
          " outputs such as concepts/models, standards," 
          " research output and open source code."]]]

     ]]])


