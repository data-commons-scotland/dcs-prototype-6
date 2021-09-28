(ns dcs.prototype-6.view.ace-furniture.trends
  (:require
    [oz.core :as oz]
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
                                          :color   {:condition {:test "datum.trend >= 0" :value "#00769C"}
                                                    :value "#B46E6F"}
                                          :tooltip [{:title "category" :field "category" :type "nominal"}
                                                    {:title "period" :field "period" :type "nominal"}
                                                    {:title "total count for the period" :field "period-count" :type "quantitative"}
                                                    {:title "avg count per month" :field "avg-count" :type "quantitative" :format ".3f"}
                                                    {:title "trend" :field "trend" :type "quantitative" :format ".3f"}]}

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
                 :header {:title "sub-category" :titlePadding 30}}
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
                                          :color   {:condition {:test "datum.trend >= 0" :value "#00769C"}
                                                    :value "#B46E6F"}
                                          :tooltip [{:title "category" :field "category" :type "nominal"}
                                                    {:title "sub-category" :field "item" :type "nominal"}
                                                    {:title "period" :field "period" :type "nominal"}
                                                    {:title "total count for the period" :field "period-count" :type "quantitative"}
                                                    {:title "avg count per month" :field "avg-count" :type "quantitative" :format ".3f"}
                                                    {:title "trend" :field "trend" :type "quantitative" :format ".3f"}]}

                             }]
                    }
       })

(def hint [:div.content.is-small.has-text-info-dark
           [:p "A circle on the a line represents the end of an " [:em "accounting"] " period."
            " Click on it to see further detail, including the " [:em "trend"] " value."
            " Each trend is calculated as a straight line approximation to the actual line, and the trend value is the slope of the line."
            " Upward trends are drawn in " [:span.has-text-weight-bold {:style {:color "#00769C"}} "blue"]
            ", downward trends in " [:span.has-text-weight-bold {:style {:color "#B46E6F"}} "red"] "."
            " The furniture graphs are order by their trend - with the most positive (upward) trend first."
            " " ]])

(defn tabbed-area [category-trends item-trends]
      [:div
       [:div.tabs
        [:ul
         [:li.tab-trends.is-active {:on-click (fn [e] (util/open-tab e "trends" "category-level"))} [:a "Less detail"]]
         [:li.tab-trends {:on-click (fn [e] (util/open-tab e "trends" "item-level"))} [:a "More detail"]]]]


       [:div#category-level-trends.content.tab-content-trends
        [:p "The " [:b "trend"] " of the average-count-per-month of the items of furniture sold in each " [:b "category"] "."]
        [:p "The category " [:span.has-text-warning "Furniture"] " has the best trend (for the average number of items reused per month),"
          " and the category " [:span.has-text-warning "Children's items"] " has the worse."]
        hint
        [oz/vega-lite (chart-spec-category-level category-trends) util/vega-embed-opts]
        ]


       [:div#item-level-trends.content.tab-content-trends {:style {:display "none"}}
        [:p "The " [:b "trend"] " of the average-count-per-month of the items of furniture sold in each " [:b "sub-category"] "."]
        [:p "The sub-category " [:span.has-text-warning "Chair, Kitchen, Dining or Wooden"] " has the best trend (for the average number of items reused per month),"
          " and the sub-category " [:span.has-text-warning "Wardrobe, single"] " has the worse."]
        hint
        [oz/vega-lite (chart-spec-item-level item-trends) util/vega-embed-opts]
        ]


       ]
      )

(defn root []
      [tabbed-area
       @state/ace-furniture-derivation-category-trends-cursor
       @state/ace-furniture-derivation-item-trends-cursor])