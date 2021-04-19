(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation
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
            :mark       {:type "line" :point false #_{:filled false :fill "floralwhite" }}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field "region" :type "nominal" :scale {:domain ["Scotland" region] :range ["#1f77b4" "#fdae6b"]}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [region household-waste-derivation-generation]
      (let [;; filter
            household-waste-derivation-generation' (filter #(contains? #{"Scotland" region} (:region %)) household-waste-derivation-generation)

            ;; stringify the year for Vega
            household-waste-derivation-generation'' (map #(assoc % :year (str (:year %)))
                                                         household-waste-derivation-generation')]
           [:div
            [oz/vega-lite (chart-spec "Generation" region household-waste-derivation-generation'')
             {:actions false}]]))

(defn root []
      [chart @state/region-holder @state/household-waste-derivation-generation-holder])