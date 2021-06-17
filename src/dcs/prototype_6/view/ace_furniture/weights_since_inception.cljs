(ns dcs.prototype-6.view.ace-furniture.weights-since-inception
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
       :background "#fff1e5"
       :data       {:values data}
       :transform  (conj shared/transform-spec
                         {:aggregate [{:op "sum" :field "weight" :as "weight"}]
                          :groupby ["period" "category"]})
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:y       {:field "category" :type "nominal"
                              :sort {:field "weight" :order "descending"}}
                    :x       {:title "weight"
                              :field "weight" :type "quantitative"
                              :axis {:title "total kg" :orient "top"}}
                    :color   shared/color-spec
                    :tooltip [{:title "category" :field "category" :type "nominal"}
                              {:title "period" :field "period" :type "nominal"}
                              {:title "kg" :field "weight" :type "quantitative"}]}})


(defn chart-spec-item-level
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      500
       :height     1000
       :background "#fff1e5"
       :data       {:values data}
       :transform  shared/transform-spec
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:y       {:title "sub-category" :field "item" :type "nominal"
                              :sort {:field "weight" :order "descending"}}
                    :x       {:title "weight"
                              :field "weight" :type "quantitative"
                              :axis {:title "total kg" :orient "top"}}
                    :color   shared/color-spec
                    :tooltip [{:title "category" :field "category" :type "nominal"}
                              {:title "sub-category" :field "item" :type "nominal"}
                              {:title "period" :field "period" :type "nominal"}
                              {:title "kg" :field "weight" :type "quantitative"}]}})

(def hint [:div.content.is-small.has-text-info-dark
           [:p "A coloured segment of a bar represents an " [:em "accounting"] " period."
            " Click on it to see further detail."]])

(defn tabbed-area [weights]
      [:div
       [:div.tabs
        [:ul
         [:li.tab-weights-since-inception.is-active {:on-click (fn [e] (util/open-tab e "weights-since-inception" "category-level"))} [:a "Less detail"]]
         [:li.tab-weights-since-inception {:on-click (fn [e] (util/open-tab e "weights-since-inception" "item-level"))} [:a "More detail"]]]]


       [:div#category-level-weights-since-inception.content.tab-content-weights-since-inception
        [:p "The " [:b "total weight"] " of the items of furniture sold in each " [:b "category"] "."]
        hint
        [:div.has-text-danger-dark
         [:p "For the concern of pollution avoidance, the weight of furniture is more representative than the number of items."
          " So weights are depicted in the graph below."
          " The categories "[:span.has-text-grey "Large Household Appliances"] " and "
          [:span.has-text-grey "Cooling Appliances"] " contain weighty items such as washing machines and fridge-freezers"
          " so it is unsurprising that those two categories feature higher up this weights chart [below] than they did"
          " in the counts chart [above]."]]
        [oz/vega-lite (chart-spec-category-level weights) util/vega-embed-opts]
        ]


       [:div#item-level-weights-since-inception.content.tab-content-weights-since-inception {:style {:display "none"}}
        [:p "The " [:b "total weight"] " of the items of furniture sold in each " [:b "sub-category"] "."]
        hint
        [oz/vega-lite (chart-spec-item-level weights) util/vega-embed-opts]
        ]


       ]
      )

(defn root []
      [tabbed-area
       @state/ace-furniture-weights-derivation-cursor])