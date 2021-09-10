(ns dcs.prototype-6.view.data
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.util :as util]))

(defn dataset-row
  [{:keys [name]}]
  [:tr {:id name}
   [:td [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view nil {:target name})} name]]
   (let [s (str name ".csv")]
     [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
   (let [s (str name ".json")]
     [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
   (let [s (str name ".ttl")]
     [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
   (let [s (str name "-metadata.json")]
     [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])])


(defn- tooltip
  [main-text tooltip-text]
  [:span.icon-text
   [:span main-text]
   [:span.icon.is-small.has-tooltip-bottom.has-tooltip-multiline.has-tooltip-info.has-text-danger
    {:data-tooltip tooltip-text}
    [:i.fas.fa-info-circle.fa-xs]]])

(defn ele
  [route metas]
  (let [target (some-> route :parameters :query :target)]
    ;(js/console.log (str "target=" target))
    (when target (r/after-render (util/scroll-fn target)))

    [:div

     [:section.hero
      [:div.hero-body

       [:div.content.has-text-centered
        [:h1.title.is-5 "The datasets used on this site"]]

       [:div.container
        [:div.content
         [:table#easier-table-default.table.is-hoverable.is-narrow
          [:thead
           [:tr.has-text-left
            [:th.has-text-danger {:row-span 2} [:span "dataset name" [:br] [:span.has-text-info {:style {:font-size "x-small"}} "(click for the description)"]]]
            [:th.has-text-danger {:col-span 3} [:span "the data" " " "(in 3 formats)"]]
            [:th.has-text-danger {:row-span 2} (tooltip "the specification of the data" "in CSVW format which is a machine-oriented description of the data's value columns, value types, linkages, etc.")]]
          [:tr.has-text-left
           [:th.has-text-danger (tooltip "CSV" "a simple tabular, human-oriented format")]
           [:th.has-text-danger (tooltip "JSON" "A machine-oriented format used by many software tools")]
           [:th.has-text-danger (tooltip "Turtle" "a machine-oriented format used by linked data (RDF) tools")]]]
          [:tbody 
           (map dataset-row (sort-by :name metas))
           [:tr {:id "zip-bundles"}
            [:td [:em "(ZIP bundles)"]]
            (let [s "all-csv.zip"]
              [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
            (let [s "all-json.zip"]
              [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
            (let [s "all-turtle.zip"]
              [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])
            (let [s "all-csvw.zip"]
              [:td [:a {:href (str util/easier-repo-data s) :target "_blank"} s]])]]]]]]]]))


(defn root [route]
  [ele
   route
   @state/meta-derivation-cursor])


