(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation
  (:require
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
            :mark       {:type "line" :point false #_{:filled false :fill "floralwhite" }}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x          {:field    "year"
                                      :type     "temporal"
                                      :timeUnit "year"
                                      :axis     {:tickCount year-count
                                                 :title     "year"}}
                         :y          {:field "tonnes"
                                      :type  "quantitative"
                                      :scale {:zero false}
                                      :axis  {:title "tonnes"}}
                         :strokeDash {:condition {:test  "datum.region == 'Scot gov target'"
                                                  :value [5 10]}
                                      :value     [0]}
                         :color      {:field "region"
                                      :type  "nominal"
                                      :scale {:domain [region "Scotland" "Scot gov target"] 
                                              :range  ["#fdae6b" "#1f77b4" "lightgrey"]}}
                         :opacity    {:condition {:selection "my"
                                                  :value     1}
                                      :value     0.2}
                         :tooltip    [{:field "region"
                                       :type  "nominal"}
                                      {:field "year"
                                       :type  "temporal"}
                                      {:field "tonnes"
                                       :type  "quantitative"}]}}))

(defn chart [region household-waste-derivation-generation]
      (let [;; filter
            household-waste-derivation-generation' (filter #(contains? #{region "Scotland" "Scot gov target"} (:region %)) household-waste-derivation-generation)

            ;; stringify the year for Vega
            household-waste-derivation-generation'' (map #(assoc % :year (str (:year %)))
                                                         household-waste-derivation-generation')]
           [:div
            [oz/vega-lite (chart-spec "Waste generated per person" region household-waste-derivation-generation'')
             util/vega-embed-opts]]))

(defn root []
      [chart @state/region-cursor @state/household-waste-derivation-generation-cursor])