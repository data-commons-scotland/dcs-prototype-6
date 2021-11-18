(ns dcs.prototype-6.view.fairshare.cars-worth
  (:require
    [reitit.frontend.easy :as rfe]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-cars-worth
      [data title]
      (let [layer-normal {:transform [{:calculate "datum.car==1 ? '🚗' : ''"
                                       :as        "emoji"}
                                      {:window  [{:op    "sum"
                                                  :field "car"
                                                  :as    "cars"}]
                                       :groupby ["date"]}]
                          :mark      {:type  "text"
                                      :align "center"}
                          :encoding  {:x       {:title "year"
                                                :field "date"
                                                :type  "temporal"
                                                :axis  {:format     "%Y"
                                                        :labelAngle 45
                                                        :labelBound 50}}
                                      :y       {:field "cars"
                                                :type  "quantitative"
                                                :axis  {:title "equivalent number of cars"}}
                                      :text    {:field "emoji"
                                                :type  "nominal"}
                                      :size    {:value 15}
                                      :tooltip [{:title  "year"
                                                 :field  "date"
                                                 :type   "temporal"
                                                 :format "%Y"}
                                                {:title  "tonnes of CO2e"
                                                 :field  "year-co2e"
                                                 :type   "quantitative"
                                                 :format ".3f"}
                                                {:title "equivalent number of cars"
                                                 :field "year-cars"
                                                 :type  "quantitative"}]}}]
        {:schema     "https://vega.github.io/schema/vega/v5.json"
         :width      300
         :height     450
         :title      title
         :background "#f2dfce"  ;  "#980f3d"  "#fff1e5"
         :data       {:values data}
         :layer      [layer-normal]
         :config     {:axisX {:grid false}}}))


(defn charts [co2e]
  (let [car-co2e (->> co2e
                          ;; roll-up to per-year
                      (group-by :year)
                      (map (fn [[year coll]]
                             {:year year
                              :co2e (apply + (map :tonnes coll))}))
                          ;; and calcuate the avoided CO2e in terms of cars
                          ;; 4.9 = average tonnes of CO2e per car per year (incorporates exhaust emissions, fuel supply chain, car material)
                      (map (fn [{:keys [year co2e]}]
                             {:year year
                              :co2e co2e
                              :cars (int (Math/round (/ co2e 4.1)))}))
                          ;; for a Vega emoji representation, create a record per car
                      (map (fn [{:keys [year co2e cars]}]
                             (repeat cars
                                     {:date      (str year "-01-01")
                                      :year-co2e co2e
                                      :year-cars cars
                                      :car       1})))
                      flatten
                          ;; workaround a Vega rendering issue by delimiting the year range with empty valued records
                      (cons {:date      "2012-01-01"
                             :year-co2e 0
                             :year-cars 0
                             :car       0})
                      (cons {:date      "2022-01-01"
                             :year-co2e 0
                             :year-cars 0
                             :car       0}))]

    [:div.columns
     [:column
      [oz/vega-lite (chart-spec-cars-worth car-co2e "Graph 1: Cars-worth of CO2e avoided per year")
       util/vega-embed-opts]
      ]
     [:column
      [:div.m-4.content
       [:p "Each item reused through The Fair Share, is an item that has avoided becoming waste/pollution."
        " Pollution is often estimated in terms of CO" [:sub "2"] "e,"
        " and The Fair Share data contains CO" [:sub "2"] "e" [:sup "a"] " values for this avoided waste/pollution."]
       [:p " But by themselves, CO" [:sub "2"] "e values can be difficult to relate to so"
        " we convert them into in a more relatable measure: " [:em "cars worth"] " of CO" [:sub "2"] "e (as shown in Graph 1)."]
       [:p "The average cost of using one car for one year is 4.1" [:sup "b"] " tonnes of CO" [:sub "2"] "e."
        " This incorporates as exhaust emissions, fuel supply chain and amortised car material for the average UK car."]
       [:p "So, we divide The Fair Share's CO" [:sub "2"] "e values by 4.1 to yield our " [:em "cars worth"] " of CO" [:sub "2"] "e values"
        " then use these to plot this graph."]
       [:p.has-text-success "This small store has enabled an impressive amount of CO" [:sub "2"] "e avoidance."]
       [:p.has-text-grey "The 2014 spike was caused by an unusually large donation of textiles" [:sup "c"] " (duvet covers, sheets, pillowcases)"
        " from the student halls of residence."]
       [:div.content.is-small.has-text-info
        [:ol.is-lower-alpha
         [:li "The Fair Share have calculated their CO" [:sub "2"] "e values using "
          [:a {:href   "https://ghgprotocol.org/Third-Party-Databases/Defra"
               :target "_blank"} "Defra"] " formulae."]
         [:li "A rough approximation based on figures from "
          [:a {:href   "https://www.brusselsblog.co.uk/carbon-emissions-in-the-lifetimes-of-cars/"
               :target "_blank"} "Carbon emissions in the lifetimes of cars"]
          " and "
          [:a {:href   "https://www.ovoenergy.com/ovo-newsroom/press-releases/2019/november/think-before-you-thank-if-every-brit-sent-one-less-thank-you-email-a-day-we-would-save-16433-tonnes-of-carbon-a-year-the-same-as-81152-flights-to-madrid.html"
               :target "_blank"} "OVO Energy"]
          "." [:sub "2"]]
         [:li "And textiles have the highest CO2e weighting according to "
          [:a {:href (rfe/href :dcs.prototype-6.router/data-view nil {:target "co2e-multiplier"})} "The Scottish Carbon Metric multiplier table"] "."]]]]]]))

(defn root []
      [charts
       @state/fairshare-derivation-co2e-cursor])