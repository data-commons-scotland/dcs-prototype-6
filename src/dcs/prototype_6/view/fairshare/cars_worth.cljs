(ns dcs.prototype-6.view.fairshare.cars-worth
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-cars-worth
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      300
       :height     350
       :background "#f2dfce"
       :data       {:values data}
       :transform  [{:calculate "datum.car==1 ? '🚗' : ''" :as "emoji"}
                    {:window [{:op "sum" :field "car" :as "cars"}]
                     :groupby ["year"]}]
       :mark       {:type "text"
                    :align "center"}
       :encoding   {:x       {:field "year" :type "temporal"
                              :axis {:format "%Y"
                                     :labelAngle 45
                                     :labelBound 50
                                     }}
                    :y       {:field "cars"
                              :type "quantitative"
                              :axis {:title "equivalent number of cars"}}
                    :text {:field "emoji" :type "nominal"}
                    :size {:value 15}
                    :tooltip [{:field "year" :type "temporal" :format "%Y"}
                              {:title "equivalent number of cars" :field "num-of-cars" :type "quantitative"}]}
       :config {
                :axisX {:grid false}}})


(defn charts [co2e]
      (let [car-co2e (->> co2e
                          ;; calcuate the avoided CO2e in terms of car-co2e
                          ;; 4.9 = average tonnes of CO2e per car per year (incorporates exhaust emissions, fuel supply chain, car material)
                          (map (fn [{:keys [yyyy-MM-dd tonnes]}]
                                   {:year (str (subs yyyy-MM-dd 0 4) "-01-01")
                                    :car-co2e (/ tonnes 4.9)}))
                          ;; roll-up to per-year
                          (group-by :year)
                          (map (fn [[year coll]]
                                   {:year year
                                    :car-co2e (->> coll
                                                   (map :car-co2e)
                                                   (apply +))}))
                          ;; for a Vega emoji representation, create a record per car
                          (map (fn [{:keys [year car-co2e]}]
                                   (let [num-of-cars (int (Math/round car-co2e))]
                                        (repeat num-of-cars
                                                {:year year
                                                 :car 1
                                                 :num-of-cars num-of-cars}))))
                          flatten
                          ;; workaround a Vega rendering issue by delimiting the year range with empty valued records
                          (cons {:year "2011-01-01"
                                 :car 0
                                 :num-of-cars 0})
                          (cons {:year "2020-01-01"
                                 :car 0
                                 :num-of-cars 0}))]

      [:div.tile.is-ancestor

       [:div.tile.is-4
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child
          [oz/vega-lite (chart-spec-cars-worth car-co2e)
           util/vega-embed-opts]]
         ]]

       [:div.tile
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child.content
          [:blockquote
           [:p "TODO:"]
           [:ul
            [:li "The graph shows the equivalent amounts of CO2e, in terms of cars"]
            [:li "The calculation incorporates exhaust emissions, fuel supply chain and amortised car material for the average UK car"]
            ]]]]]]))

(defn root []
      [charts
       @state/fairshare-co2e-derivation-cursor])