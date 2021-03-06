(ns dcs.prototype-6.view.fairshare.per-quarter
  (:require
   [oz.core :as oz]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.state :as state]))

(defn chart-spec-per-season
  [data title]
  (let [layer-normal      {:mark     {:type                 "bar"
                                      :cornerRadiusTopLeft  3
                                      :cornerRadiusTopRight 3}
                           :encoding {:x       {:title "year quarter"
                                                :field "quarter"
                                                :type  "temporal"
                                                :axis  {:labelExpr  "timeFormat(datum.value, '%q') == '1' ? timeFormat(datum.value, 'Q%q %Y') : timeFormat(datum.value, 'Q%q')"
                                                        :labelAngle 90
                                                        :tickCount  {:interval "month"
                                                                     :step     3
                                                                     :start    0}}}
                                      :y       {:field "tonnes"
                                                :type  "quantitative"}
                                      :color   {:value "#A2CAC1"}
                                      :tooltip [{:title  "year quarter"
                                                 :field  "quarter"
                                                 :type   "temporal"
                                                 :format "Q%q %Y"}
                                                {:field  "tonnes"
                                                 :type   "quantitative"
                                                 :format ".3f"}]}}]
    {:schema     "https://vega.github.io/schema/vega/v5.json"
     :width      370
     :height     200
     :background "#f2dfce"
     :title      title
     :data       {:values data}
     :transform  [{:timeUnit "yearquarter"
                   :field    "yyyy-MM-dd"
                   :as       "quarter"}
                  {:aggregate [{:op    "sum"
                                :field "tonnes"
                                :as    "tonnes"}]
                   :groupby   ["quarter"]}]
     :layer      [layer-normal]}))


(defn charts [material]
  [:div.columns
   [:column
    [oz/vega-lite (chart-spec-per-season material "Graph 4: Donations per quarter")
     util/vega-embed-opts]]
   [:column
    [:div.m-4.content
     [:p "Q3 donations are, by far, the most pronounced."
      " Q3 starts in June which - at Stirling, the host university - is the end of the academic year for most courses."
      " So the high Q3 donations are likely to be the results of students donating possessions before leaving for the summer."]
     [:p "For the period Q2 2013 to Q1 2014 the data has less date resolution which partially accounts for the irregular donation pattern during this period."
      " Another factor, Q1 2014 during this period, was the unusually large donation of textiles"
      " (duvet covers, sheets, pillowcases) from the student halls of residence [also evident in Graph 1]."]
     [:p "During the period Q1 2020 to Q2 2021 there were no donations because the store's normal operation was suspended due to Covid-19."]]]])

(defn root []
      [charts
       @state/fairshare-derivation-material-cursor])