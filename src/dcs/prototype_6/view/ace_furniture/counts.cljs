(ns dcs.prototype-6.view.ace-furniture.counts
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]
    [dcs.prototype-6.view.ace-furniture.shared :as shared]))


(defn chart-spec-category-level
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      500
       :height     400
       :background "#f2dfce"
       :data       {:values data}
       :transform  (conj shared/transform-spec
                         {:aggregate [{:op "sum" :field "count" :as "count"}]
                          :groupby ["period" "category"]})
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:y       {:field "category" :type "nominal"
                              :sort {:field "count" :order "descending"}}
                    :x       {:title "count"
                              :field "count" :type "quantitative"
                              :axis {:title "total count" :orient "top"}}
                    :color   shared/color-spec
                    :tooltip [{:title "category" :field "category" :type "nominal"}
                              {:title "period" :field "period" :type "nominal"}
                              {:title "count" :field "count" :type "quantitative"}]}})


(defn chart-spec-item-level
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      500
       :height     1500
       :background "#f2dfce"
       :data       {:values data}
       :transform  shared/transform-spec
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:y       {:title "sub-category" :field "item" :type "nominal"
                              :sort {:field "count" :order "descending"}}
                    :x       {:title "count"
                              :field "count" :type "quantitative"
                              :axis {:title "total count" :orient "top"}}
                    :color   shared/color-spec
                    :tooltip [{:title "category" :field "category" :type "nominal"}
                              {:title "sub-category" :field "item" :type "nominal"}
                              {:title "period" :field "period" :type "nominal"}
                              {:title "count" :field "count" :type "quantitative"}]}})

(def hint [:div.content.is-small.has-text-info-dark
           [:p "A coloured segment of a bar represents an " [:em "accounting"] " period."
            " Click on it to see further data detail."]])

(defn tabbed-area [counts]
      [:div
       [:div.tabs
        [:ul
         [:li.tab-counts-since-inception.is-active {:on-click (fn [e] (util/open-tab e "counts-since-inception" "category-level"))} [:a "Less detail"]]
         [:li.tab-counts-since-inception {:on-click (fn [e] (util/open-tab e "counts-since-inception" "item-level"))} [:a "More detail"]]]]


       [:div#category-level-counts-since-inception.content.tab-content-counts-since-inception
        [:p "The " [:b "total count"] " of the items of furniture sold in each " [:b "category"] "."]
         [:p "The categories " [:span.has-text-warning "Furniture"] " and " [:span.has-text-warning "Soft Furniture"] " account (by far) for the most items sold."
          " Click on the " [:span.has-text-warning "More detail"] " tab [above] to find out which specific types have been the most popular."]
        hint
        [oz/vega-lite (chart-spec-category-level counts) util/vega-embed-opts]
        ]


       [:div#item-level-counts-since-inception.content.tab-content-counts-since-inception {:style {:display "none"}}
        [:p "The " [:b "total count"] " of the items of furniture sold in each " [:b "sub-category"] "."]
         [:p "The sub-category " [:span.has-text-warning "Chair, Kitchen, Dining or Wooden"] " accounts (by far) for the most items sold."
          " Click on one of its bar segments and you'll see that it is associated with the category " [:span.has-text-warning "Furniture"] "."
          " Interestingly, many of the top sub-categories appear to have enjoyed an upwards surge in numbers during the last (and shortest) of the 3 accounting periods."
          " The trends graphs (later on this page) reinforce that observation."]
        hint
        [oz/vega-lite (chart-spec-item-level counts) util/vega-embed-opts]
        ]


       ]
      )

(defn root []
      [tabbed-area
       @state/ace-furniture-derivation-count-cursor])