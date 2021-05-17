(ns dcs.prototype-6.view.stirling-bin-collection.derivation-composition
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            ;;:title      title
            :width      250
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type "line" :point {:filled false :fill "floralwhite"}}
            #_:mark       #_{:type                 "bar"
                         :cornerRadiusTopLeft  3
                         :cornerRadiusTopRight 3}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "date" :type "temporal"
                                   :timeUnit "yearquarter"
                                   :axis {;:tickCount year-count
                                          :title      "year quarter"
                                          :labelAngle 60
                                          :labelOffset 10
                                          :labelBound 25}}
                         :y       {:field "tonnes" :type "quantitative"
                                   :scale {:zero false}
                                   :axis {:title "tonnes per citizen"}}
                         :color   {:field "material" :type "nominal"
                                   :scale {:scheme "tableau10"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "material" :type "nominal"}
                                   {:field "date" :type "temporal"}
                                   {:field "tonnes" :type "quantitative" :title "tonnes per citizen"}]}}))

(defn chart [derivation-composition]
      (let [;; construct a date field for Vega
            derivation-composition' (map #(assoc % :date (util/date-str (:year %) (:quarter %)))
                                         derivation-composition)]

           [:div
            [oz/vega-lite (chart-spec "Composition" derivation-composition')
             util/vega-embed-opts]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-composition-cursor])