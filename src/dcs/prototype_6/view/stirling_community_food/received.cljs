(ns dcs.prototype-6.view.stirling-community-food.received
  (:require
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-per-source
  [data title]
  {:schema     "https://vega.github.io/schema/vega/v5.json"
   :width      370
   :height     200
   :background "#fff1e5"
   :title      title
   :data       {:values data}
   :transform  [{:calculate "datum['counter-party']"
                 :as        "source"}
                {:aggregate [{:op    "sum"
                              :field "tonnes"
                              :as    "tonnes"}]
                 :groupby   ["source"]}]
   :mark       {:type                 "bar"
                :cornerRadiusTopLeft  3
                :cornerRadiusTopRight 3}
   :encoding   {:x       {:field "source"
                          :type  "nominal"
                          :axis  {:labelAngle 45
                                  :labelBound 45}
                          :sort  {:field "tonnes"
                                  :order "descending"}}
                :y       {:field "tonnes"
                          :type  "quantitative"}
                :color   {:field  "source"
                          :type   "nominal"
                          :scale  {:domain ["Purchased"
                                            "Donated not waste"
                                            "Local supermarkets"
                                            "Fareshare"
                                            "Donated as waste"
                                            "Other"]
                                   :range  ["#FFC473"
                                            "#7158A1"
                                            "#006CAE"
                                            "#59896A"
                                            "#BF5748"
                                            "#928E85"]}
                          :legend {:symbolType "circle"
                                   :orient     "bottom"
                                   :columns    4}}
                :tooltip [{:field "source"
                           :type  "nominal"}
                          {:field "tonnes"
                           :type  "quantitative"}]}})

(defn chart-spec-per-month
  [data title]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
                        :width      370
                        :height     200
                        :background "#fff1e5"
                        :title title
                        :data       {:values data}
                        :transform  [{:calculate "datum['counter-party']" :as "source"}
                                     {:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
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
                                               :scale {:domain ["Purchased"
                                                                "Donated not waste"
                                                                "Local supermarkets"
                                                                "Fareshare"
                                                                "Donated as waste"
                                                                "Other"]
                                                       :range  ["#FFC473"
                                                                "#7158A1"
                                                                "#006CAE"
                                                                "#59896A"
                                                                "#BF5748"
                                                                "#928E85"]}
                                               :legend nil #_{:orient "bottom" :columns 4}}
                                     :tooltip [{:field "source" :type "nominal"}
                                               {:field "month" :type "temporal" :format "%b %Y"}
                                               {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-month-logarithmic
      [data title]
      (assoc-in (chart-spec-per-month data title) [:encoding :y :scale] {:type "log"}))


(defn charts [derivation-tonnes]
      (let [chart-data (->> derivation-tonnes
                            (filter #(= "in" (:io-direction %))))]

           [:div.tile.is-ancestor

            [:div.tile.is-6
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-source chart-data "Graph 2: Incoming amount per source")
                util/vega-embed-opts]]
              [:div.tile.is-child.content
               [:p "Graph 2 shows " [:em "Local supermarkets"] " to be the source that supplied the most food material during the year. "
                "(" [:em "Local supermarkets"] " is an aggregation of the collection agencies Neighbourly & Foodiverse,"
                " and of the supermarkets Sainsbury's & Cooperative.)"]
               [:p "Graphs 3 & 4 provide month-by-month breakdowns, with Graph 4  
               using a non-linear scale to make differences more obvious.
               The high value for " [:em "Local supermarkets"] " in Apr" (gstring/unescapeEntities "&nbsp;") "21 is because one of its constituent"
                " organisations - Foodiverse - does not report its figures on a monthly basis; instead it reports on a batch of previous months every so often,"
               " with Apr" (gstring/unescapeEntities "&nbsp;") "21 being an instance of that."]]]]

            [:div.tile
             [:div.tile.is-vertical.is-parent

              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month chart-data "Graph 3: Incoming amounts per month")
                util/vega-embed-opts]]
              [:div.tile.is-child
               [oz/vega-lite (chart-spec-per-month-logarithmic chart-data "Graph 4: Incoming amounts per month (non-linear)")
                util/vega-embed-opts]]]]]))

(defn root []
      [charts
       @state/stirling-community-food-tonnes-derivation-tonnes-cursor])