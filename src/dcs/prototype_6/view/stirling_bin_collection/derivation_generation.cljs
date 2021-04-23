(ns dcs.prototype-6.view.stirling-bin-collection.derivation-generation
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
                         :y       {:field "tonnes" :type "quantitative"
                                   #_:scale #_{:domain [0.2 0.5]}
                                   :axis {:title "tonnes per citizen"}}
                         :color   {:field "region" :type "nominal"
                                   :scale {:domain ["Scotland (all means)" "Stirling (all means)" "Stirling (bin collection)"]
                                           :range ["#bcd6e9" "#fedfc4" "#BF5748"]}
                                   :axis {:title "category"}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal" :title "category"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative" :title "tonnes per citizen"}]}}))

(defn chart [stirling-bin-collection-derivation-generation household-waste-derivation-generation]
      (let [;; filter for Scotland and Stirling in the more general household waste data
            household-waste-derivation-generation' (->> household-waste-derivation-generation
                                                        (filter #(contains? #{"Scotland" "Stirling"} (:region %))) ;; we only want Scotland and Stirling
                                                        (map #(assoc % :region (str (:region %) " (all means)")))) ;; re-label

            ;; add " (bins)" to the label for Stirling
            stirling-bin-collection-derivation-generation' (map #(assoc % :region "Stirling (bin collection)")
                                                                stirling-bin-collection-derivation-generation)

            ;; combine Stirling bin collection data with that for the more general household waste of Stirling and Scotland
            derivation-generation' (concat stirling-bin-collection-derivation-generation'
                                           household-waste-derivation-generation')

            ;; stringify the year for Vega
            derivation-generation'' (map #(assoc % :year (str (:year %)))
                                          derivation-generation')]

           [:div
            [oz/vega-lite (chart-spec "Amount" derivation-generation'')
             {:actions false}]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-generation-holder
       @state/household-waste-derivation-generation-holder])