(ns dcs.prototype-6.view.stirling-community-food.outcomes
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-outcome
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      400
       :height     200
       :background "#f2dfce"
       :data       {:values data}
       :transform  [{:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby ["outcome"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:field "outcome" :type "nominal"
                              :axis {:labelAngle 60
                                     :labelBound 45}
                              :sort {:field "tonnes" :order "descending"}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :color {:field "outcome" :type "nominal"
                            :scale {:domain ["Used as food"
                                             "Donated to animal scantuary"
                                             "Used for compost"
                                             "Disposed of as waste"]
                                    :range  ["#009175"
                                             "#AC8E00"
                                             "#A16A51"
                                             "#BF5748"]}
                            :legend {:symbolType "circle"
                                     :orient "bottom" :columns 3}}
                    :tooltip [{:field "outcome" :type "nominal"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      400
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
                    :color   {:field "outcome" :type "nominal"
                              :scale {:domain ["Used as food"
                                               "Donated to animal scantuary"
                                               "Used for compost"
                                               "Disposed of as waste"]
                                      :range  ["#009175"
                                               "#AC8E00"
                                               "#A16A51"
                                               "#BF5748"]}
                              :legend nil #_{:orient "bottom"}}
                    :tooltip [{:field "outcome" :type "nominal"}
                              {:field "month" :type "temporal" :format "%b %Y"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month-logarithmic
      [data]
      (assoc-in (chart-spec-per-month data) [:encoding :y :scale] {:type "log"}))


(defn charts [derivation-tonnes]
      (let [chart-data (->> derivation-tonnes
                            (filter #(= "out" (:io-direction %)))
                            (map #(assoc % :outcome (:counter-party %))))]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-outcome chart-data)
                {:actions false}]]
              [:div.tile.is-child
               [:div.tile.is-child.content
                [:p "The graph above, shows that Stirling Community Food has
                provided (for free) to the community during the year:"]
                 [:ul
                  [:li "73.7 tonnes of food to people"]
                  [:li "2 tonnes of food to an animal sanctuary"]
                  [:li "1.4 tonnes of compost"]
                  [:li "(3.7 tonnes was unavoidably waste)"]]
                [:p "The graphs left, provide a month-by-month breakdown
               (with the second one using a non-linear scale to make the differences more obvious).
               [TODO: Overlay a received-material line.]"]]]]]

            [:div.tile
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month chart-data)
                {:actions false}]]
              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month-logarithmic chart-data)
                {:actions false}]]]]]))

(defn root []
      [charts
       @state/stirling-community-food-tonnes-derivation-tonnes-cursor])