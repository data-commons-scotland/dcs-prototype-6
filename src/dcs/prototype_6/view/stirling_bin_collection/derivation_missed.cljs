(ns dcs.prototype-6.view.stirling-bin-collection.derivation-missed
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      300
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type "bar"}
            :selection  {:my {:type   "multi"
                              :fields ["tonnes"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "date" :type "temporal" :timeUnit "yearquarter" :axis {:title "year quarter" :labelAngle 70 :labelOffset 10 :labelBound 10}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:value "#BF5748"}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "date" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [derivation-missed]
      (let [;; construct a date field for Vega
            derivation-missed' (map #(assoc % :date (str ({1 "31 Mar"
                                                           2 "30 Jun"
                                                           3 "31 Aug"
                                                           4 "31 Dec"} (:quarter %))
                                                         " "
                                                         (:year %)))
                                    derivation-missed)]
           [:div
            [oz/vega-lite (chart-spec "Missed bins" derivation-missed')
             {:actions false}]]))

(defn root []
      [chart
       @state/stirling-bin-collection-derivation-missed-holder])