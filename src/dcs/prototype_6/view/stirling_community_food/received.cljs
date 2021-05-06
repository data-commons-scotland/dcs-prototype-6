(ns dcs.prototype-6.view.stirling-community-food.received
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-source
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      400
       :height     200
       :background "#fff1e5"
       :data       {:values data}
       :transform  [{:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby   ["source"]}]
       :mark       {:type                 "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:field "source" :type "nominal"
                              :axis  {:labelAngle 60
                                      :labelBound 45}
                              :sort  {:field "tonnes" :order "descending"}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :color   {:field  "source" :type "nominal"
                              :scale  {:scheme "tableau20"}
                              :legend {:symbolType "circle"
                                       :orient "bottom" :columns 4}}
                    :tooltip [{:field "source" :type "nominal"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
                        :width      400
                        :height     200
                        :background "#fff1e5"
                        :data       {:values data}
                        :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                                     {:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                                      :groupby ["month" "source"]}]
                        :mark       {:type "line"
                                     :point {:filled false :fill "#fff1e5"}}
                        :encoding   {:x       {:field "month" :type "temporal"
                                               :axis {:format "%b %y"
                                                      :labelAngle 60
                                                      :labelBound 45}}
                                     :y       {:field "tonnes" :type "quantitative"}
                                     :color   {:field "source" :type "nominal"
                                               :scale {:scheme "tableau20"}
                                               :legend nil #_{:orient "bottom" :columns 4}}
                                     :tooltip [{:field "source" :type "nominal"}
                                               {:field "month" :type "temporal" :format "%b %Y"}
                                               {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month-logarithmic
      [data]
      (assoc-in (chart-spec-per-month data) [:encoding :y :scale] {:type "log"}))


(defn charts [derivation-tonnes]
      (let [chart-data (->> derivation-tonnes
                            (filter #(= "in" (:io-direction %)))
                            (map #(assoc % :source (:counter-party %))))]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-source chart-data)
                {:actions false}]]
              [:div.tile.is-child
               [:p "Some text"]]]]

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