(ns dcs.prototype-6.ui-household-waste
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

(defn chart [region household-waste population]
      (let [;; calculate rolled-up values and add 'em into the dataset
            population (concat population
                               (->> population
                                    (group-by :year)
                                    (map (fn [[year coll]] {:region     "Scotland"
                                                            :year       year
                                                            :population (->> coll
                                                                             (map :population)
                                                                             (apply +))}))))

            ;; calculate rolled-up (:management and :material) values
            household-waste (->> household-waste
                                 (group-by (juxt :region :year))
                                 (map (fn [[[region year] coll]] {:region region
                                                                  :year   year
                                                                  :tonnes (->> coll
                                                                               (map :tonnes)
                                                                               (apply +))})))

            ;; calculate rolled-up (:region) values and add 'em into the dataset
            household-waste (concat household-waste
                                    (->> household-waste
                                         (group-by :year)
                                         (map (fn [[year coll]] {:region "Scotland"
                                                                 :year   year
                                                                 :tonnes (->> coll
                                                                              (map :tonnes)
                                                                              (apply +))}))))

            ;; filter
            household-waste (filter #(contains? #{"Scotland" region} (:region %)) household-waste)

            ;; calculate the per citizen values
            population-for-lookup (group-by (juxt :region :year) population)
            lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

            household-waste (map (fn [{:keys [region year tonnes]}] {:region region
                                                                     :year   year
                                                                     :tonnes (double (/ tonnes (lookup-population region year)))})
                                 household-waste)

            ;; stringify the year for Vega
            household-waste (map #(assoc % :year (str (:year %)))
                                 household-waste)]
           [:div
            [oz/vega-lite (chart-spec "Household waste per citizen" region household-waste)
             {:actions false}]]))

(defn create []
      [chart @state/region-holder @state/household-waste-holder @state/population-holder])