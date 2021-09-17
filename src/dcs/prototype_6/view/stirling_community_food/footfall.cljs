(ns dcs.prototype-6.view.stirling-community-food.footfall
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-day
      [data title]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "floralwhite"
       :title title
       :data       {:values data}
       :transform  [{:aggregate [{:op "sum" :field "count" :as "footfall"}]
                     :groupby ["day"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:field "day" :type "nominal"
                              :axis {:title nil
                                     :labelAngle 60
                                     :labelBound 45}
                              :sort ["Sun" "Mon" "Tue" "Wed" "Thu" "Fri" "Sat"]}
                    :y       {:field "footfall" :type "quantitative"}
                    :color {:value "#8DD9E3"}
                    :tooltip [{:field "day" :type "nominal"}
                              {:field "footfall" :type "quantitative"}]}})

(defn chart-spec-per-month
      [data title]
  {:schema     "https://vega.github.io/schema/vega/v5.json"
   :width      370
   :height     200
   :background "floralwhite"
   :title title
   :data       {:values data}
   :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                {:aggregate [{:op "sum" :field "count" :as "footfall"}]
                 :groupby ["month"]}]
   :mark       {:type "line"
                :point {:filled false :fill "floralwhite"}}
   :encoding   {:x       {:field "month" :type "temporal"
                          :axis {:format "%b %y"
                                 :labelAngle 60
                                 :labelBound 45}}
                :y       {:field "footfall" :type "quantitative"}
                :color   {:value "#8DD9E3"}
                :tooltip [{:field "month" :type "temporal" :format "%b %Y"}
                          {:field "footfall" :type "quantitative"}]}})

(defn chart-spec-day-per-month
      [data title]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "floralwhite"
       :title title
       :data       {:values data}
       :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                    {:aggregate [{:op "sum" :field "count" :as "footfall"}]
                     :groupby ["month" "day"]}]
       :mark       {:type "line"
                    :point {:filled false :fill "floralwhite"}}
       :encoding   {:x       {:field "month" :type "temporal"
                              :axis {:format "%b %y"
                                     :labelAngle 60
                                     :labelBound 45}}
                    :y       {:field "footfall" :type "quantitative" :scale {:zero false}}
                    :color   {:field "day" :type "nominal"
                              :sort ["Sun" "Mon" "Tue" "Wed" "Thu" "Fri" "Sat"]
                              :scale {:scheme "tableau20"}
                              :legend {:orient "bottom"}}
                    :tooltip [{:field "day" :type "nominal"}
                              {:field "month" :type "temporal" :format "%b %Y"}
                              {:field "footfall" :type "quantitative"}]}})


(defn charts [derivation]
      (let [chart-data derivation]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-day chart-data "Graph 8: Footfall per day-of-the-week")
                util/vega-embed-opts]]
              [:div.tile.is-child.content
               [:p "Graph 8 shows that there is
               no significant differences in footfall between days of the week."]
               [:p "Graph 9 provides a month-by-month breakdown of footfall."]
               [:p "Graph 10 provides a month-by-month breakdown
               of the footfall per day-of-the-week. There is no discernible
               correlation between a day-of-the-week and a month-of-the-year."]]]]

            [:div.tile
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month chart-data "Graph 9: Footfall per month")
                util/vega-embed-opts]]
              [:div.tile.is-child
               [oz/vega-lite (chart-spec-day-per-month chart-data "Graph 10: Footfall per day-of-the-week, per month")
                util/vega-embed-opts]]]]]))

(defn root []
      [charts
       @state/stirling-community-food-footfall-derivation-cursor])