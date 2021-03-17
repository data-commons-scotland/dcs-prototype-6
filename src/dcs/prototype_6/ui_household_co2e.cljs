(ns dcs.prototype-6.ui-household-co2e
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

(defn chart [region household-co2e population]
      (let [;; calculate rolled-up values and add 'em into the dataset
            population (concat population
                               (->> population
                                    (group-by :year)
                                    (map (fn [[year coll]] {:region     "Scotland"
                                                            :year       year
                                                            :population (->> coll
                                                                             (map :population)
                                                                             (apply +))}))))

            ;; calculate rolled-up values and add 'em into the dataset
            household-co2e (concat household-co2e
                                   (->> household-co2e
                                        (group-by :year)
                                        (map (fn [[year coll]] {:region "Scotland"
                                                                :year   year
                                                                :tonnes (->> coll
                                                                             (map :tonnes)
                                                                             (apply +))}))))

            ;; filter
            household-co2e (filter #(contains? #{"Scotland" region} (:region %)) household-co2e)

            ;; calculate the per citizen values
            population-for-lookup (group-by (juxt :region :year) population)
            lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

            household-co2e (map (fn [{:keys [region year tonnes]}] {:region region
                                                                    :year   year
                                                                    :tonnes (double (/ tonnes (lookup-population region year)))})
                                household-co2e)

            ;; stringify the year for Vega
            household-co2e (map #(assoc % :year (str (:year %)))
                                household-co2e)]
           [:div
            [oz/vega-lite (chart-spec "Household CO2e per citizen" region household-co2e)
             {:actions false}]]))

(defn create []
      [chart @state/region-holder @state/household-co2e-holder @state/population-holder])