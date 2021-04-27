(ns dcs.prototype-6.view.stirling-bin-collection.derivation-generation
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title data]
      (let [quarter-count (count (group-by (juxt :year :quarter) data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            ;;:title      title
            :width      250
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type "line" :point {:filled false :fill "floralwhite"}}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:timeUnit "yearquarter"
                                   :field    "date"
                                   :type "temporal"
                                   :axis     {:tickCount quarter-count
                                              :title      "year quarter"
                                              :labelAngle 60
                                              :labelBound 45}}
                         :y       {:field "tonnes" :type "quantitative"
                                   ;:scale {:domain [0.03 0.1]}
                                   :axis  {:title "tonnes per citizen"}}
                         :color   {:field "region" :type "nominal"
                                   :scale {:domain ["Scotland (all means)" "Stirling (all means)" "Stirling (bin collection)"]
                                           :range  ["#bcd6e9" "#fedfc4" "#BF5748"]}
                                   :axis  {:title "category"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal" :title "category"}
                                   {:field "date" :type "temporal" }
                                   {:field "tonnes" :type "quantitative" :title "tonnes per citizen"}]}}))

(defn chart [stirling-bin-collection-derivation-generation household-waste-derivation-generation]
      (let [;; min year to be used to filter the context data is 1 year less than the min year of the Stirling bin collection data
            ctx-min-year (->> stirling-bin-collection-derivation-generation
                              (map :year)
                              (apply min)
                              dec)

            ;; filter for Scotland and Stirling in the more general household waste data
            household-waste-derivation-generation' (->> household-waste-derivation-generation
                                                        (filter #(>= (:year %) ctx-min-year)) ;; we only want a specific range of years
                                                        (filter #(contains? #{"Scotland" "Stirling"} (:region %))) ;; we only want Scotland and Stirling
                                                        (map #(assoc % :region (str (:region %) " (all means)")))) ;; re-label

            ;; estimate the quarterly figures by simply dividing by 4
            household-waste-derivation-generation'' (->> household-waste-derivation-generation'
                                                         (map (fn [{:keys [region year tonnes]}]
                                                                  (let [quarterly-tonnes (double (/ tonnes 4))]
                                                                       (for [quarter [1 2 3 4]]
                                                                            {:region  region
                                                                             :year    year
                                                                             :quarter quarter
                                                                             :tonnes  quarterly-tonnes}))))
                                                         flatten)


            ;; add " (bins)" to the label for Stirling
            stirling-bin-collection-derivation-generation' (map #(assoc % :region "Stirling (bin collection)")
                                                                stirling-bin-collection-derivation-generation)

            ;; combine Stirling bin collection data with that for the more general household waste of Stirling and Scotland
            derivation-generation' (concat stirling-bin-collection-derivation-generation'
                                           household-waste-derivation-generation'')

            ;; construct a date field for Vega
            derivation-generation'' (map #(assoc % :date (util/date-str (:year %) (:quarter %)))
                                         derivation-generation')]

           [:div
            [oz/vega-lite (chart-spec "Amount" derivation-generation'')
             {:actions false}]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-generation-cursor
       @state/household-waste-derivation-generation-cursor])