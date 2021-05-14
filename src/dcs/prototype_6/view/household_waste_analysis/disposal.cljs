(ns dcs.prototype-6.view.household-waste-analysis.disposal
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(def stratum-labels ["urban £" "urban ££" "urban £££" "rural £/££" "rural £££"])

(defn chart-spec
      [chart-data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :background "#fff1e5"
       :data       {:values chart-data}
       :transform  [{:calculate "datum.stream + (datum.stream == datum.idealStream ? ' - appropriate' : ' - inappropriate')" :as "stream"}
                    {:aggregate [{:op "sum" :field "kgPerHhPerWk" :as "kgPer2Wks"}]
                     :groupby   ["stratum" "stream"]}
                    {:calculate "datum.kgPer2Wks / 2" :as "kg"}] ;; to average over the 2 phases
       :facet      {:column {:field  "stratum" :type "nominal"
                             :sort   stratum-labels
                             :header {:title "household type (location type & council tax band)"
                                      :titleOrient "bottom"
                                      :labelPadding -170}}} ;; hack to workaround labelOrient problem
       :spec       {:width  100
                    :height 150
                    :mark     {:type "bar"}
                    :encoding {:x           {:field "stream" :type "nominal"
                                             :axis  nil}
                               :y           {:field "kg" :type "quantitative"
                                             :axis  {:title "average kg/hh/wk"}}
                               :color {:field "stream" :type "nominal"
                                       :scale {:domain ["grey bin - appropriate"
                                                        "grey bin - inappropriate"
                                                        "recycling bin - appropriate"
                                                        "recycling bin - inappropriate"]
                                               :range ["#928E85"
                                                       "#BF5748"
                                                       "#7FD1AE"
                                                       "#FD8D58"]}
                                       :legend {:title "disposal"}}
                               :tooltip     [{:field "stratum" :type "nominal" :title "house type (location & CTax)"}
                                             {:field "stream" :type "nominal" :title "disposal"}
                                             {:field "kg" :type "quantitative" :title "avg kg per household per wk"}]}}})




(defn charts [derivation]
      (let [chart-data derivation]

           [:div.tile.is-ancestor
            [:div.tile.is-parent

             [:div.tile.is-child
              [oz/vega-lite (chart-spec chart-data)
               {:actions false}]]

             [:div.tile.is-child.content
              [:blockquote
               [:p "TODO:"]
               [:ul
                [:li "Say that the graph suggested that the majority of grey bin waste should be disposed of via a different means."]]]
              ]

             ]]))

(defn root []
      [charts
       @state/household-waste-analysis-derivation-cursor])
