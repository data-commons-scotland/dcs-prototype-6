(ns dcs.prototype-6.view.ace-furniture.counts-since-inception
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
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
       :height     1000
       :background "#f2dfce"
       :data       {:values data}
       :transform  shared/transform-spec
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:y       {:field "item" :type "nominal"
                              :sort {:field "count" :order "descending"}}
                    :x       {:title "count"
                              :field "count" :type "quantitative"
                              :axis {:title "total count" :orient "top"}}
                    :color   shared/color-spec
                    :tooltip [{:title "category" :field "category" :type "nominal"}
                              {:title "item" :field "item" :type "nominal"}
                              {:title "period" :field "period" :type "nominal"}
                              {:title "count" :field "count" :type "quantitative"}]}})


(defn tabbed-area [counts]
      [:div
       [:div.tabs
        [:ul
         [:li.tab-counts-since-inception.is-active {:on-click (fn [e] (util/open-tab e "counts-since-inception" "category-level"))} [:a "Less detail"]]
         [:li.tab-counts-since-inception {:on-click (fn [e] (util/open-tab e "counts-since-inception" "item-level"))} [:a "More detail"]]]]


       [:div#category-level-counts-since-inception.content.tab-content-counts-since-inception
        [:p "Less detail"]
        [oz/vega-lite (chart-spec-category-level counts) util/vega-embed-opts]
        ]


       [:div#item-level-counts-since-inception.content.tab-content-counts-since-inception {:style {:display "none"}}
        [:p "More detail"]
        [oz/vega-lite (chart-spec-item-level counts) util/vega-embed-opts]
        ]


       ]
      )

(defn root []
      [tabbed-area
       @state/ace-furniture-counts-derivation-orig-cursor])