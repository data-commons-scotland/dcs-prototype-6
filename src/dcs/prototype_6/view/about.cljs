(ns dcs.prototype-6.view.about
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]))

(defn root
  []
  (r/after-render (util/scroll-fn))

  [:div
   
   [:section.hero.is-small.has-background
    [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                          :alt "Project team image"}]
    [:div.hero-body
     [:div.container
      [:div.content.has-text-centered
       [:h1.title.is-5 "About this site"]
       [:h2.subtitle.is-6.has-text-info "This is the " [:em [:b "Waste Matters Scotland"]] " website."]]]]]

   [:section.section

    [:div.container
     [:div.content

      [:div.notification.is-info [:p "NB: This prototype-6 version of the website is a " 
                                  [:em "work-in-progress"]
                                  ". Its primary purpose is to prototype, trial & showcase ideas that will form parts of the eventual, final version of the website."]]
      
      [:div.content.px-6.is-flex.is-align-items-center
       
       [:img.is-rounded {:src   "img/dcs-circle.png"
                         :alt   "Data Commons Scotland - icon"
                         :width "64px"}]
       
       [:div.content.ml-3
        "The development of a website is one of the outcomes of the "
        [:a {:href   "https://campuspress.stir.ac.uk/datacommonsscotland/"
             :target "_blank"} "Data Commons Scotland"] " research project ("
        [:a {:href   "https://gow.epsrc.ukri.org/NGBOViewGrant.aspx?GrantRef=EP/S027521/1"
             :target "_blank"} "funded"] " by the EPSRC)."]]

     [:div.content.px-6
      [:p "The objectives for this website include:"]
      [:ol.is-lower-roman
       [:li "Help " [:b "non-experts learn"] " about waste management in Scotland through Open Data."]
       [:li "Encourage 3rd-sector, " [:b "reuse/recycling organisations to publish"] " their data as Open Data."]
       [:li "Be a demonstrator of the findings from the encompassing research project,
            and be an archetype for future portals onto other categories of " [:b "Open Data"] "."]]]
      
      
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

      ]]]])


