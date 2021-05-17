(ns dcs.prototype-6.view.stirling-bin-collection.derivation-percent-recycled
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
            :encoding   {:x       {:field "date" :type "temporal"
                                   :timeUnit "yearquarter"
                                   :axis {:tickCount quarter-count
                                          :title      "year quarter"
                                          :labelAngle 60
                                          :labelBound 40}}
                         :y       {:field "percentage" :type "quantitative"
                                   #_:scale #_{:zero false}}
                         :color   {:field "region" :type "nominal"
                                   :scale {:domain ["Scotland (recycled overall)" "Stirling (recycled overall)" "Stirling (recycling bin collection)"]
                                           :range ["#bcd6e9" "#fedfc4" "#BF5748"]}
                                   :axis {:title "category"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal" :title "category"}
                                   {:field "date" :type "temporal"}
                                   {:field "percentage" :type "quantitative"}]}}))

(defn chart [stirling-bin-collection-derivation-percent-recycled household-waste-derivation-percent-recycled]
      (let [;; min year to be used to filter the context data is 1 year less than the min year of the Stirling bin collection data
            ctx-min-year (->> stirling-bin-collection-derivation-percent-recycled
                              (map :year)
                              (apply min)
                              dec)

            ;; filter for Scotland and Stirling in the more general household waste data
            household-waste-derivation-percent-recycled' (->> household-waste-derivation-percent-recycled
                                                              (filter #(>= (:year %) ctx-min-year)) ;; we only want a specific range of years
                                                              (filter #(contains? #{"Scotland" "Stirling"} (:region %))) ;; we only want Scotland and Stirling
                                                              (map #(assoc % :region (str (:region %) " (recycled overall)")))) ;; re-label

            ;; estimate the quarterly figures by simply copying the yearly percentage
            household-waste-derivation-percent-recycled'' (->> household-waste-derivation-percent-recycled'
                                                         (map (fn [{:keys [region year percentage]}]
                                                                  (let [quarterly-percentage percentage]
                                                                       (for [quarter [1 2 3 4]]
                                                                            {:region  region
                                                                             :year    year
                                                                             :quarter quarter
                                                                             :percentage quarterly-percentage}))))
                                                         flatten)

            ;; add " (bins)" to the label for Stirling
            stirling-bin-collection-derivation-percent-recycled' (map #(assoc % :region "Stirling (recycling bin collection)")
                                                                stirling-bin-collection-derivation-percent-recycled)

            ;; combine Stirling bin collection data with that for the more general household waste of Stirling and Scotland
            derivation-percent-recycled' (concat stirling-bin-collection-derivation-percent-recycled'
                                           household-waste-derivation-percent-recycled'')

            ;; stringify the year for Vega
            derivation-percent-recycled'' (map #(assoc % :date (util/date-str (:year %) (:quarter %)))
                                          derivation-percent-recycled')]

           [:div
            [oz/vega-lite (chart-spec "% for the recycling bin collection" derivation-percent-recycled'')
             util/vega-embed-opts]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-percent-recycled-cursor
       @state/household-waste-derivation-percent-recycled-cursor])