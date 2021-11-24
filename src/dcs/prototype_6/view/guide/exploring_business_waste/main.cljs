(ns dcs.prototype-6.view.guide.exploring-business-waste.main
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]))

(def webapp-ctx (.. js/document -location -pathname))
(js/console.log (str "webapp-ctx: " webapp-ctx))

(defn root
  [route]
  (r/after-render (util/scroll-fn))
  
  (let [_preset (-> route :parameters :path :preset)]

    [:div
     
     [:section.hero.is-small.has-background 
      [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                            :alt "DCS top hero image"}]
      [:div.hero-body
       [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5 "Exploring Scotland's business waste data"]
         [:h2.subtitle.is-6.has-text-info
          [:span "A walk-through on how to extract information from the data about business waste in Scotland"]]]]]]
     
      [:div 
       [:div.iframe-row-container {:style {:height "750px"}}
          (comment
            "Copy the following style element into the head element of the HTML that gets pulled in by the iframe

             <style>
             .bg-white {
               --tw-bg-opacity: 0 !important; 
             }
               .mt-8.text-gray-400 {
               display: none !important;
             }
             h1 {
               display: none !important;
             }
             h2 {
               color: #BF5748 !important;
               font-weight: 600;
               font-size: larger;
             }
             a { text-decoration: underline; }
             a:hover { background: #A4CAC2; }
             </style>

             ")
        [:iframe.iframe-row 
         {:src         (str webapp-ctx "literate-programming-docs/index.html#/src%2Fexploring_business_waste.clj")
          :scrolling   "no"
          :border      "no"
          :frameborder "no"}]]]]))

