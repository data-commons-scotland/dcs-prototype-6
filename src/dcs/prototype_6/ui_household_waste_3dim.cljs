(ns dcs.prototype-6.ui-household-waste-3dim
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
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

(defn chart [region household-waste-3dim population]
      (let [;; filter
            household-waste-3dim (filter #(contains? #{"Scotland" region} (:region %)) household-waste-3dim)

            ;; calculate the per citizen values
            population-for-lookup (group-by (juxt :region :year) population)
            lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

            household-waste-3dim (map (fn [{:keys [region year tonnes]}] {:region region
                                                                          :year   year
                                                                          :tonnes (double (/ tonnes (lookup-population region year)))})
                                      household-waste-3dim)

            ;; stringify the year for Vega
            household-waste-3dim (map #(assoc % :year (str (:year %)))
                                      household-waste-3dim)]
           [:div
            [oz/vega-lite (chart-spec "Household waste per citizen" region household-waste-3dim)
             {:actions false}]]))

(defn create []
      [chart @state/region-holder @state/household-waste-3dim-holder @state/population-holder])