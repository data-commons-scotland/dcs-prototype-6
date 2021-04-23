(ns dcs.prototype-6.view.stirling-bin-collection.derivation-percent-recycled
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
            :mark       {:type "line" :point false #_{:filled false :fill "floralwhite"}}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal"
                                   :timeUnit "year"
                                   :axis {:tickCount year-count :title "year"}}
                         :y       {:field "percentage" :type "quantitative"
                                   #_:scale #_{:zero false}}
                         :color   {:field "region" :type "nominal"
                                   :scale {:domain ["Scotland (recycled overall)" "Stirling (recycled overall)" "Stirling (recycling bin collection)"]
                                           :range ["#bcd6e9" "#fedfc4" "#BF5748"]}
                                   :axis {:title "category"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal" :title "category"}
                                   {:field "year" :type "temporal"}
                                   {:field "percentage" :type "quantitative"}]}}))

(defn chart [stirling-bin-collection-derivation-percent-recycled household-waste-derivation-percent-recycled]
      (let [;; filter for Scotland and Stirling in the more general household waste data
            household-waste-derivation-percent-recycled' (->> household-waste-derivation-percent-recycled
                                                              (filter #(contains? #{"Scotland" "Stirling"} (:region %))) ;; we only want Scotland and Stirling
                                                              (map #(assoc % :region (str (:region %) " (recycled overall)")))) ;; re-label

            ;; add " (bins)" to the label for Stirling
            stirling-bin-collection-derivation-percent-recycled' (map #(assoc % :region "Stirling (recycling bin collection)")
                                                                stirling-bin-collection-derivation-percent-recycled)

            ;; combine Stirling bin collection data with that for the more general household waste of Stirling and Scotland
            derivation-percent-recycled' (concat stirling-bin-collection-derivation-percent-recycled'
                                           household-waste-derivation-percent-recycled')

            ;; stringify the year for Vega
            derivation-percent-recycled'' (map #(assoc % :year (str (:year %)))
                                          derivation-percent-recycled')]

           [:div
            [oz/vega-lite (chart-spec "% for the recycling bin collection" derivation-percent-recycled'')
             {:actions false}]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-percent-recycled-holder
       @state/household-waste-derivation-percent-recycled-holder])