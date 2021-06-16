(ns dcs.prototype-6.view.ace-furniture.trends
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
       :background "floralwhite"
       :data       {:values data}
       :transform  (conj shared/transform-spec
                         {:calculate "truncate(datum.category, 30)" :as "categorytruncated"})
       :facet   {:field "categorytruncated"
                 :type "nominal"
                 :sort {:field "trend" :op "mean" :order "descending"}
                 :header {:title "category" :titlePadding 30}}
       :columns    4
       :bounds "flush"
       :spacing 35
       ;:resolve {:axis {:x "independent" :y "independent"}}
       :spec       {:width 130
                    :height 50
                    :layer [{:mark {:type "line"
                                    :point {:filled false :fill "#f2dfce"}}
                             :encoding   {:x       {:field "instant" :type "temporal"
                                                    :axis {:title nil
                                                           :format "%b %y"
                                                           :labelAngle 90
                                                           ;:labelBound 45
                                                           }
                                                    }
                                          :y       {:field "avg-count" :type "quantitative"
                                                    :axis {:title nil}
                                                    :scale {:type "sqrt"}
                                                    }
                                          :color   {:value "#B46E6F"}
                                          :tooltip [{:title "category" :field "category" :type "nominal"}
                                                    {:title "period" :field "period" :type "nominal"}
                                                    {:title "avg count per month" :field "avg-count" :type "quantitative"}
                                                    {:title "trend" :field "trend" :type "quantitative"}]}

                             }]
                    }
       })


(defn chart-spec-item-level
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :background "floralwhite"
       :data       {:values data}
       :transform  (conj shared/transform-spec
                         {:calculate "truncate(datum.item, 30)" :as "itemtruncated"})
       :facet   {:field "itemtruncated"
                 :type "nominal"
                 :sort {:field "trend" :op "max" :order "descending"}
                 :header {:title "item" :titlePadding 30}}
       :columns    5
       :bounds "flush"
       :spacing 35
       ;:resolve {:axis {:x "independent" :y "independent"}}
       :spec       {:width 130
                    :height 50
                    :layer [{:mark {:type "line"
                                    :point {:filled false :fill "#f2dfce"}}
                             :encoding   {:x       {:field "instant" :type "temporal"
                                                    :axis {:title nil
                                                           :format "%b %y"
                                                           :labelAngle 90
                                                           ;:labelBound 45
                                                           }
                                                    }
                                          :y       {:field "avg-count" :type "quantitative"
                                                    :axis {:title nil}
                                                    :scale {:type "sqrt"}
                                                    }
                                          :color   {:value "#B46E6F"}
                                          :tooltip [{:title "category" :field "category" :type "nominal"}
                                                    {:title "item" :field "item" :type "nominal"}
                                                    {:title "period" :field "period" :type "nominal"}
                                                    {:title "avg count per month" :field "avg-count" :type "quantitative"}
                                                    {:title "trend" :field "trend" :type "quantitative"}]}

                             }]
                    }
       })


(defn tabbed-area [category-trends item-trends]
      [:div
       [:div.tabs
        [:ul
         [:li.tab-counts-per-period.is-active {:on-click (fn [e] (util/open-tab e "trends" "category-level"))} [:a "Less detail"]]
         [:li.tab-counts-per-period {:on-click (fn [e] (util/open-tab e "trends" "item-level"))} [:a "More detail"]]]]


       [:div#category-level-trends.content.tab-content-trends
        [:p "Less detail"]
        [oz/vega-lite (chart-spec-category-level category-trends) util/vega-embed-opts]
        ]


       [:div#item-level-trends.content.tab-content-trends {:style {:display "none"}}
        [:p "More detail"]
        [oz/vega-lite (chart-spec-item-level item-trends) util/vega-embed-opts]
        ]


       ]
      )

(defn root []
      [tabbed-area
       @state/ace-furniture-counts-derivation-category-trends-cursor
       @state/ace-furniture-counts-derivation-item-trends-cursor])