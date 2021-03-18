(ns dcs.prototype-6.ui-household-waste-4dim
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-OLD [title region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       "line"
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field "region" :type "nominal" :scale {:domain ["Scotland" region] :range ["#1f77b4" "#fdae6b"]}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart-spec [title region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type "bar"
                         :cornerRadiusTopLeft 3
                         :cornerRadiusTopRight 3}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field "management" :type "nominal" :scale {:domain ["Recycled" "Landfilled" "Other Diversion"] :range ["#bdbd8d" "#c49c49" "#c7c7c7"]}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "management" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [region household-waste-4dim]
      (let [;; filter
            household-waste-4dim' (filter #(= region (:region %)) household-waste-4dim)

            ;; stringify the year for Vega
            household-waste-4dim'' (map #(assoc % :year (str (:year %)))
                                        household-waste-4dim')]
           [:div
            [oz/vega-lite (chart-spec "Management" region household-waste-4dim'')
             {:actions false}]]))

(defn create []
      [chart @state/region-holder @state/household-waste-4dim-holder])