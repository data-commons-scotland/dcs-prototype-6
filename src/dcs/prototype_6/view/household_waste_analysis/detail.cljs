(ns dcs.prototype-6.view.household-waste-analysis.detail
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
       :background "floralwhite"
       :spacing 3
       :data       {:values chart-data}
       :transform  [{:aggregate [{:op "mean" :field "kgPerHhPerWk" :as "kg"}]
                     :groupby ["stratum" "material-L1" "material-L2" "stream" "idealStream"]}]
       :facet     {:column {:field "stratum" :type "nominal"
                            :sort stratum-labels
                            :header {:title "household type (location type & council tax band)"}}
                   :row {:field "material-L2" :type "nominal"
                         :header {:title "material (in detail)" :labelAngle 0 :labelAlign "left"}
                         :sort {:field "kg" :op "max" :order "descending"}}}
       :spec {:width 130
              :height 20
              :layer [{:mark "bar"
                       :encoding {:x {:field "kg" :type "quantitative" :scale {:type "sqrt"} :axis {:title "avg kg/hh/wk"}}
                                  :y {:field "stream" :type "nominal" :axis {:title ""}}
                                  :color {:field "material-L2" :type "nominal"
                                          :scale {:scheme "tableau20"}
                                          :legend nil}
                                  :fillOpacity {:value 0.5}
                                  :tooltip [{:field "stratum" :type "nominal" :title "house type (location & CTax)"}
                                            {:field "material-L1" :type "nominal" :title "material (high level)"}
                                            {:field "material-L2" :type "nominal" :title "material (in detail)"}
                                            {:field "stream" :type "nominal" :title "(actual) disposal"}
                                            {:field "idealStream" :type "nominal" :title "ideal disposal"}
                                            {:field "kg" :type "quantitative" :title "kg per household per week"}]}}
                      {:mark "bar"
                       :transform [{:filter "(datum.kg > 0) && (datum.stream != datum.idealStream)"}],
                       :encoding {:x {:field "kg" :type "quantitative" :scale {:type "sqrt"} :axis {:title "avg kg/hh/wk"}}
                                  :y {:field "stream" :type "nominal" :axis {:title ""}}
                                  :color {:field "material-L2" :type "nominal"
                                          :scale {:scheme "tableau20"}
                                          :legend nil}
                                  :fillOpacity {:value 0}
                                  :stroke {:value "red"}
                                  :strokeWidth {:value 1}
                                  :tooltip [{:field "stratum" :type "nominal" :title "house type (location & CTax)"}
                                            {:field "material-L1" :type "nominal" :title "material (high level)"}
                                            {:field "material-L2" :type "nominal" :title "material (detailed level)"}
                                            {:field "stream" :type "nominal" :title "(actual) disposal"}
                                            {:field "idealStream" :type "nominal" :title "ideal disposal"}
                                            {:field "kg" :type "quantitative" :title "avg kg per household per wk"}]}}]}})

(defn charts [derivation]
      (let [chart-data derivation]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec chart-data)
                {:actions false}]]

              [:div.tile.is-child.content
               [:blockquote
                [:p "TODO:"]
                [:ul
                 [:li "This chart conveys a fair amount of information - explain how to interpret it. E.g. bars outlined in red represent inappropriate disposal."]
                 [:li "How should we interpret for 'Unavoidable food waste'? Was there an collection that targeted food & garden waste,
                 that wasn't covered by the sample dataset? ...since that would might mean that, elsewhere,  'Unavoidable food waste' was being disposed of appropriately."]]]
               ]

              ]]]))

(defn root []
      [charts
       @state/household-waste-analysis-derivation-cursor])
