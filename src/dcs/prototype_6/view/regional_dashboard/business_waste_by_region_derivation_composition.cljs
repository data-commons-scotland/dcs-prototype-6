(ns dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-composition
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
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
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field "material" :type "nominal" :scale {:scheme "tableau20"} :legend nil #_{:orient "bottom" :columns 3}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "material" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [region business-waste-by-region-derivation-composition]
      (let [;; filter
            business-waste-by-region-derivation-composition' (filter #(= region (:region %)) business-waste-by-region-derivation-composition)

            ;; stringify the year for Vega
            business-waste-by-region-derivation-composition'' (map #(assoc % :year (str (:year %)))
                                                                   business-waste-by-region-derivation-composition')]
           [:div
            [oz/vega-lite (chart-spec "Composition" region business-waste-by-region-derivation-composition'')
             util/vega-embed-opts]]))

(defn root []
      [chart @state/region-cursor @state/business-waste-by-region-derivation-composition-cursor])