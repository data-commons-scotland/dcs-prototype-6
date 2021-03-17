(ns dcs.prototype-6.ui-chart
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title data]
      (let [year-count (count (group-by :year data))]
           {:schema "https://vega.github.io/schema/vega/v5.json"
            :title     title
            :width     200
            :height    150
            :background "floralwhite"
            :data      {:values data}
            :mark      "line"
            :selection {:my {:type "multi"
                             :fields ["region"]
                             :bind "legend"}}
            :encoding  {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                        :y       {:field "tonnage" :type "quantitative" :scale {:zero false} :axis {:title "tonnage"}}
                        :color   {:field "region" :type "nominal"}
                        :opacity {:condition {:selection "my" :value 1}
                                  :value     0.2}
                        :tooltip [{:field "region" :type "nominal"}
                                  {:field "year" :type "temporal"}
                                  {:field "tonnage" :type "quantitative"}]}}))

(defn chart [region]
      [:div
       [oz/vega-lite (chart-spec "Trends" [{:year "2017" :tonnage 5 :region region}
                                         {:year "2018" :tonnage 4 :region region}
                                         {:year "2019" :tonnage 3 :region region}
                                         {:year "2017" :tonnage 6 :region "Average"}
                                         {:year "2018" :tonnage 6 :region "Average"}
                                         {:year "2019" :tonnage 4 :region "Average"}])
        {:actions false}]])

(defn create []
      [chart @state/region-holder])