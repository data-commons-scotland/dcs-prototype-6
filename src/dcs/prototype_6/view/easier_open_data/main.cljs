(ns dcs.prototype-6.view.easier-open-data.main
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.util :as util]))


(defn dataset-row
      [{:keys [name description #_record-count #_attribute-count creator supplier supply-url licence licence-url]}]
      [:tr {:id name}
       [:td [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target name})} name]]
       [:td description]
       #_[:td record-count " x " attribute-count]
       [:td creator]
       [:td [:a {:href supply-url :target "_blank"} supplier]]
       [:td [:a {:href licence-url :target "_blank"} licence]]])


(defn ele
  [route metas]
  (let [target (some-> route :parameters :query :target)]
           ;(js/console.log (str "target=" target))
    (when target (r/after-render (util/scroll-fn target)))

    [:div

     [:section.hero
      [:div.hero-body

       [:div.content.has-text-centered
        [:h1.title.is-6 "About the data on this site"]
        [:h2.subtitle.is-5.has-text-danger [:span [:em "Easier"] " open data about waste in Scotland"]]]

       [:div.container
        [:div.content
         [:h3.subtitle.is-6 "Objective"]
         [:p "Several organisations are doing a very good job of curating & publishing "
          [:b "open data"] " about waste in Scotland but,
          the published data is not always \"easy to use\" for non-experts.
          We have see several references to this at open data conference events and on social media platforms:"]
         [:blockquote "Whilst statisticians/coders may think that it is reasonably simple to knead together these
          somewhat diverse datasets into a coherent knowledge, the interested layman doesn't find it so easy."]
         [:p "One of the objectives of the Data Commons Scotland project is to address
          the \"ease of use\" issue over open data.
          The contents of this repository are the result of us " [:em "re-working"] " some of the
          existing " [:em "source"] " open data
          so that it is " [:b [:em "easier"]] " to use, understand, consume, parse, and all in one place.
          It may not be as detailed or have all the nuances as the source data - but aims to be
          better for the purposes of making the information accessible to non-experts."]
         [:h3.subtitle.is-6 "Approach"]
         [:p "We have processed the source data just enough to:"]
         [:ul
          [:li "provide intuitive value-based cross-referencing between datasets"]
          [:li "add a few fields whose values are generally useful but not easily derivable by a
           simple calculation (such as latitude & longitude)"]
          [:li "make it available as simple CSV and JSON files in a Git repository."]]
         [:p "We have not augmented the data with derived values that can be simply calculated,
          such as per-population amounts, averages, trends, totals, etc."]
         [:p "We have tried to rectify the inconsistencies that occur in the source data
              (in particular, the inconsistent labelling of waste materials and regions).
              However, this is still \"work-in-progress \" and we yet to tease out & 
              make consistent further useful dimensions."]
         
         [:a {:href (rfe/href :dcs.prototype-6.router/data-view)}
          [:div.notification {:style {:backgroundColor "#f2dfce"}}
           [:div.icon-text.has-text-weight-bold.icon-text.has-text-danger.is-justify-content-center.has-text-centered 
            [:span.icon
             [:i.fas.fa-eye.fa-lg]]
            [:span " See the list of the " (count metas)
             " " [:em "easier-to-use"]
             " datasets that are used on this site"]]]]]]]]]))


(defn root [route]
  [ele
   route
   @state/meta-derivation-cursor])




