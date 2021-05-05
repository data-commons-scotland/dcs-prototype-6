(ns dcs.prototype-6.view.stirling-community-food.footfall
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-day
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      40
       :height     200
       :background "floralwhite"
       :data       {:values data}
       :transform  [{:aggregate [{:op "sum" :field "footfall" :as "footfall"}]
                     :groupby ["day" "period-in-day"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:column {:field "day" :type "nominal"
                             :sort {:op "sum" :field "footfall" :order "descending"}
                             :axis {:title nil}}
                    :x       {:field "period-in-day" :type "nominal"
                              :sort ["Morning" "Evening"]
                              :axis {:title nil
                                     :labelAngle 60
                                     :labelBound 45}}
                    :y       {:field "footfall" :type "quantitative"}
                    :color {:field "period-in-day" :type "nominal"
                            :scale {:domain ["Morning"
                                             "Evening"]
                                    :range  ["#8DD9E3"
                                             "#E8CAE3"]}
                            :legend {:title "period in day" :symbolType "circle"}}
                    :tooltip [{:field "period-in-day" :type "nominal" :title "period in day"}
                              {:field "day" :type "nominal"}
                              {:field "footfall" :type "quantitative"}]}})

(defn chart-spec-period-in-day-per-month
      [data]
  {:schema     "https://vega.github.io/schema/vega/v5.json"
   :width      400
   :height     200
   :background "floralwhite"
   :data       {:values data}
   :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                {:aggregate [{:op "sum" :field "footfall" :as "footfall"}]
                 :groupby ["month" "period-in-day"]}]
   :mark       {:type "line"
                :point {:filled false :fill "floralwhite"}}
   :encoding   {:x       {:field "month" :type "temporal"
                          :axis {:format "%b %y"
                                 :labelAngle 60
                                 :labelBound 45}}
                :y       {:field "footfall" :type "quantitative"}
                :color   {:field "period-in-day" :type "nominal"
                          :scale {:domain ["Morning"
                                           "Evening"]
                                  :range  ["#8DD9E3"
                                           "#E8CAE3"]}
                          :legend {:title "period in day"}}
                :tooltip [{:field "period-in-day" :type "nominal" :title "period in day"}
                          {:field "month" :type "temporal" :format "%b %Y"}
                          {:field "footfall" :type "quantitative"}]}})

(defn chart-spec-day-per-month
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      400
       :height     200
       :background "floralwhite"
       :data       {:values data}
       :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                    {:aggregate [{:op "sum" :field "footfall" :as "footfall"}]
                     :groupby ["month" "day"]}]
       :mark       {:type "line"
                    :point {:filled false :fill "floralwhite"}}
       :encoding   {:x       {:field "month" :type "temporal"
                              :axis {:format "%b %y"
                                     :labelAngle 60
                                     :labelBound 45}}
                    :y       {:field "footfall" :type "quantitative" :scale {:zero false}}
                    :color   {:field "day" :type "nominal"
                              :scale {:scheme "tableau20"}}
                    :tooltip [{:field "day" :type "nominal"}
                              {:field "month" :type "temporal" :format "%b %Y"}
                              {:field "footfall" :type "quantitative"}]}})


(defn charts [derivation]
      (let [chart-data derivation]

           [:div
            [oz/vega-lite (chart-spec-per-day chart-data)
             {:actions false}]
            [oz/vega-lite (chart-spec-period-in-day-per-month chart-data)
             {:actions false}]
            [oz/vega-lite (chart-spec-day-per-month chart-data)
             {:actions false}]]))

(defn root []
      [charts
       @state/stirling-community-food-footfall-derivation-cursor])