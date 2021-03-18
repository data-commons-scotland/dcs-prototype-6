(ns dcs.prototype-6.ui-household-waste-derivation-percent-recycled
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       "line"
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "percentage" :type "quantitative" :scale {:zero false} :axis {:title "percentage"}}
                         :color   {:field "region" :type "nominal" :scale {:domain ["Scotland" region] :range ["#1f77b4" "#fdae6b"]}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "percentage" :type "quantitative"}]}}))

(defn chart [region household-waste-derivation-percent-recycled]
      (let [;; filter
            household-waste-derivation-percent-recycled' (filter #(contains? #{"Scotland" region} (:region %)) household-waste-derivation-percent-recycled)

            ;; stringify the year for Vega
            household-waste-derivation-percent-recycled'' (map #(assoc % :year (str (:year %)))
                                                               household-waste-derivation-percent-recycled')]
           [:div
            [oz/vega-lite (chart-spec "% recycled" region household-waste-derivation-percent-recycled'')
             {:actions false}]]))

(defn create []
      [chart @state/region-holder @state/household-waste-derivation-percent-recycled-holder])