(ns dcs.prototype-6.view.data
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [goog.string :as gstring]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.util :as util]))

(defn dataset-row
  [{:keys [name description #_record-count #_attribute-count creator max-date-in-data supplier supplied-date supply-url licence licence-url]}]
  (if (contains? #{"ace-furniture-count" "ace-furniture-avg-weight"} name) ;; temporary hack until we release/publish these
    [:tr {:id name}
     [:td.has-text-weight-bold name]
     [:td description]
     #_[:td record-count " x " attribute-count]
     [:td "t.b.d."]
     [:td "t.b.d."]
     [:td "t.b.d."]
     [:td [:a {:href   (str util/easier-repo-data (str name "-metadata.json"))
               :target "_blank"} "CSVW"]]
     [:td creator]
     [:td max-date-in-data]
     [:td [:a {:href   supply-url
               :target "_blank"} supplier]]
     [:td supplied-date]
     [:td [:a {:href   licence-url
               :target "_blank"} licence]]] 
    [:tr {:id name}
     [:td.has-text-weight-bold name]
     [:td description]
     #_[:td record-count " x " attribute-count]
     [:td [:a {:href   (str util/easier-repo-data (str name ".csv"))
               :target "_blank"} "CSV"]]
     [:td [:a {:href   (str util/easier-repo-data (str name ".json"))
               :target "_blank"} "JSON"]]
     [:td [:a {:href   (str util/easier-repo-data (str name ".ttl"))
               :target "_blank"} "Turtle"]]
     [:td [:a {:href   (str util/easier-repo-data (str name "-metadata.json"))
               :target "_blank"} "CSVW"]]
     [:td creator]
     [:td max-date-in-data]
     [:td [:a {:href   supply-url
               :target "_blank"} supplier]]
     [:td supplied-date]
     [:td [:a {:href   licence-url
               :target "_blank"} licence]]]))


(defn- tooltip
  [main-text tooltip-text]
  [:span.icon-text {:style {:display "inline-block" :white-space "nowrap"}}
   main-text
   [:span.icon.is-small.has-tooltip-bottom.has-tooltip-multiline.has-tooltip-info.has-text-danger
    {:data-tooltip tooltip-text}
    [:i.fas.fa-info-circle.fa-xs]]])

(defn ele
  [route metas]
  (let [target (some-> route :parameters :query :target)]
    ;(js/console.log (str "target=" target))
    (if target 
      (r/after-render (util/scroll-fn target))
      (r/after-render (util/scroll-fn)))

    [:div
     
     [:section.hero.is-small.has-background {:style {:backgroundColor "#fff1e5"}}
      [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                            :alt "Datasets image"}]
      [:div.hero-body
       [:div.container
        [:div.content.has-text-centered
         [:h1.title.is-5 {:style {:margin 0}}
          [:span "The " (count metas)
           " " [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view)} [:em "easier"]]
           " datasets that are used on this site"]]]]]]

     [:section.hero {:style {:backgroundColor "#fff1e5"}}
      [:div.hero-body

       [:div.content.has-text-centered
        ]

       [:div.container
        [:div.content
         [:table#easier-table-fff1e5.table.is-hoverable.is-narrow
          [:thead
           [:tr.has-text-left
            [:th.has-text-danger {:col-span 6} [:span "dataset"]]
            [:th.has-text-danger {:col-span 5} [:span "source"]]]
           [:tr.has-text-left
            [:th.has-text-danger {:row-span 2} "name"]
            [:th.has-text-danger {:row-span 2} "description"]
            [:th.has-text-danger {:col-span 3} [:span "the data" " " [:span "(3" (gstring/unescapeEntities "&nbsp;") "formats)"]]]
            [:th.has-text-danger {:row-span 2} (tooltip [:span "its spec"] "a machine-oriented description of the data's: entity URI; column names, types, references; etc.")]
            [:th.has-text-danger "creator"]
            [:th.has-text-danger "max date in data"]
            [:th.has-text-danger "supplier"]
            [:th.has-text-danger "supplied when"]
            [:th.has-text-danger "licence"]]
           [:tr.has-text-left
            [:th.has-text-danger (tooltip "CSV" " a simple tabular, human-oriented format")]
            [:th.has-text-danger (tooltip "JSON" "a machine-oriented format used by many software tools")]
            [:th.has-text-danger (tooltip "Turtle" "a machine-oriented format used by linked data (RDF) tools")]]]
          [:tbody 
           (map dataset-row (sort-by :name metas))
           [:tr {:id "zip-bundles"}
            [:td]
            [:td [:em "(handy ZIP bundles)"]]
            [:td [:a {:href   (str util/easier-repo-data "all-csv.zip")
                      :target "_blank"} "ZIP"]]
            [:td [:a {:href   (str util/easier-repo-data "all-json.zip")
                      :target "_blank"} "ZIP"]]
            [:td [:a {:href   (str util/easier-repo-data "all-turtle.zip")
                      :target "_blank"} "ZIP"]]
            [:td [:a {:href   (str util/easier-repo-data "all-csvw.zip")
                      :target "_blank"} "ZIP"]]
            [:td]
            [:td]
            [:td]
            [:td]
            [:td]]]]]]]]]))


(defn root [route]
  [ele
   route
   @state/meta-derivation-cursor])


