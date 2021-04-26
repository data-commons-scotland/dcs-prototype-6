(ns dcs.prototype-6.view.stirling-bin-collection.derivation-composition
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            ;;:title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type                 "bar"
                         :cornerRadiusTopLeft  3
                         :cornerRadiusTopRight 3}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal"
                                   :timeUnit "year"
                                   :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative"
                                   :scale {:zero false}
                                   :axis {:title "tonnes per citizen"}}
                         :color   {:field "material" :type "nominal"
                                   :scale {:scheme "tableau20"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "material" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative" :title "tonnes per citizen"}]}}))

(defn chart [derivation-composition]
      (let [;; stringify the year for Vega
            derivation-composition' (map #(assoc % :year (str (:year %)))
                                         derivation-composition)]

           [:div
            [oz/vega-lite (chart-spec "Composition" derivation-composition')
             {:actions false}]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-composition-cursor])