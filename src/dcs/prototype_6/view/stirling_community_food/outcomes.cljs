(ns dcs.prototype-6.view.stirling-community-food.outcomes
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-outcome
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "#f2dfce"
       :data       {:values data}
       :transform  [{:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby ["outcome"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:field "outcome" :type "nominal"
                              :axis {:labelAngle 45
                                     :labelBound 45}
                              :sort {:field "tonnes" :order "descending"}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :color {:field "outcome" :type "nominal"
                            :scale {:domain ["Used as food"
                                             "Donated to animal sanctuary"
                                             "Used by individuals for compost"
                                             "Council compost, Energen biogas, etc."]
                                    :range  ["#00AC8F"
                                             "#006AC7"
                                             "#B49531"
                                             "#E27E44"]}
                            :legend {:symbolType "circle"
                                     :orient "bottom" :columns 3}}
                    :tooltip [{:field "outcome" :type "nominal"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "#f2dfce"
       :data       {:values data}
       :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                    {:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby ["month" "outcome"]}]
       :mark       {:type "line"
                    :point {:filled false :fill "#f2dfce"}}
       :encoding   {:x       {:field "month" :type "temporal"
                              :axis {:format "%b %y"
                                     :labelAngle 60
                                     :labelBound 45}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :strokeDash {:condition {:test  "datum.outcome == 'Total received'"
                                             :value [5 5]}
                                 :value     [0]}
                    :color   {:field "outcome" :type "nominal"
                              :scale {:domain ["Used as food"
                                               "Donated to animal sanctuary"
                                               "Used by individuals for compost"
                                               "Council compost, Energen biogas, etc."
                                               "Total received"]
                                      :range  ["#00AC8F"
                                               "#006AC7"
                                               "#B49531"
                                               "#E27E44"
                                               "grey" #_"#B1AB99"]}
                              :legend {:orient "bottom" :columns 3}}
                    :tooltip [{:field "outcome" :type "nominal"}
                              {:field "month" :type "temporal" :format "%b %Y"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month-logarithmic
      [data]
      (-> (chart-spec-per-month data)
        (assoc-in [:encoding :y :scale] {:type "log"})
          (assoc-in [:encoding :color :legend] nil)))


(defn charts [derivation-tonnes]
      (let [chart-data (->> derivation-tonnes
                            (filter #(= "out" (:io-direction %)))
                            (map #(assoc % :outcome (:counter-party %))))
            chart-data-plus (concat
                                  chart-data
                                  ;; for comparison, include the total received
                                  (->> derivation-tonnes
                                       (filter #(= "in" (:io-direction %)))
                                       (map #(assoc % :outcome "Total received"))))]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-outcome chart-data)
                util/vega-embed-opts]]
              [:div.tile.is-child
               [:div.tile.is-child.content
                [:p "The graph above, shows that Stirling Community Food has
                provided (for free) to the community during the year:"]
                 [:ul
                  [:li "73.7 tonnes of food to people"]
                  [:li "3.7 tonnes of waste food to Stirling council (for compost) and to Energen (for biogas and fertiliser)"]
                  [:li "2 tonnes of food to an animal sanctuary"]
                  [:li "1.4 tonnes to individuals for compost"]
                  ]
                [:p "The next graphs, provide a month-by-month breakdown
               (with the second one using a non-linear scale to make differences more obvious).
               Changes in amounts outgoing coincide with the changes in amounts received "
               [:span.has-text-grey "(depicted by the dashed grey line the next graph)"] "."]]]]]

            [:div.tile
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month chart-data-plus)
                util/vega-embed-opts]]
              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month-logarithmic chart-data)
                util/vega-embed-opts]]]]]))

(defn root []
      [charts
       @state/stirling-community-food-tonnes-derivation-tonnes-cursor])