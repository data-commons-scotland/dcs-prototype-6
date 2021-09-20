(ns dcs.prototype-6.view.waste-through-time-map
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]))

(def webapp-ctx (.. js/document -location -pathname))

(defn root
  [route]
  (r/after-render (util/scroll-fn))

  (let [preset (-> route :parameters :path :preset)]

    [:div
     
     [:section.hero.is-small.has-background
      [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                            :alt "Waste through the decade image"}]
      [:div.hero-body
       [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5 "Waste through the decade"]
         [:h2.subtitle.is-6.has-text-info
          [:span "Discover how waste, recycling and CO" [:sub "2"] "e amounts have been changing across Scotland through the last 10 years"]]]]]]

     [:section.section

      [:div.container
       
         ;; map | ui hints
       [:div.columns.is-centered

          ;; map
        [:div.column.is-three-fifths
         [:figure.image.is-3by4
          [:iframe.has-ratio
           {:src         (str webapp-ctx "waste-through-time-map/index.html?preset=" preset)
            :scrolling   "no"
            :border      "no"
            :frameborder "no"}]]]
        
          ;; ui hints
        [:div.column

         [:div.content.is-small
          [:blockquote
           [:p
            [:span.is-small.has-text-danger "NB: This is " [:em "work-in-progress"] "."]
            " At present, this map tool works only on some user devices."]]]
         
         [:div.content
          
          [:h3.subtitle.is-6.has-text-danger "Key elements"]

          [:div.columns.is-flex.is-vcentered
           [:column
            [:figure.image.is-inline-block
             [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/waste-quants-thru-time-on-a-map/dropdown.png"}]]]
           [:column
            "The " [:em "dataset of interest"] " is chosen using this control."
            ]]
          
          [:div {:style {:padding "0rem 2rem 2rem 2rem"}}
           [:p "Choices:"
            [:ol
             [:li "Tonnes of household waste per person per year."]
             [:li "Tonnes of C0" [:sub "2"] "e due to household waste per person per year."]
             [:li "Tonnes of household vs. business waste."]]]]
          
          [:div.columns.is-flex.is-vcentered
           [:column
            [:figure.image.is-inline-block
             [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/waste-quants-thru-time-on-a-map/slider.png"}]]]
           [:column
            "Used this control to " [:em "travel through time"] " ."]]

          [:div.columns.is-flex.is-vcentered
           [:column
            [:figure.image.is-inline-block.is-48x48
             [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/waste-quants-thru-time-on-a-map/pie.png"}]]]
           [:column
            "Each chart depicts the waste-related quantities for a council area. The sizes of its slices and its overall size, are related to the quantities that it depicts." ]]
          
          [:div.columns.is-flex.is-vcentered
           [:column
            [:figure.image.is-inline-block
             [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/waste-quants-thru-time-on-a-map/detail.png"}]]]
           [:column
            "Hover over a council area to see detailed metrics in the panel." ]]
          
          [:div.columns.is-flex.is-vcentered
           [:column
            [:figure.image.is-inline-block.is-64x64
             [:img {:src "https://github.com/data-commons-scotland/dcs-shorts/raw/master/cluster-map-of-materials-incoming/zoom-control.png"}]]]
           [:column
            "The usual zoom and pan controls."]]

          ]
         ]
        
        ]]]]))

