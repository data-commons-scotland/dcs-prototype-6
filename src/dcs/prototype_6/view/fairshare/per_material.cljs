(ns dcs.prototype-6.view.fairshare.per-material
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-per-material
      [data title]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "#fff1e5"
       :title      title
       :data       {:values data}
       :transform  [{:timeUnit "year"
                     :field    "yyyy-MM-dd"
                     :as       "year"}
                    {:aggregate [{:op    "average"
                                  :field "tonnes"
                                  :as    "avg-tonnes"}]
                     :groupby   ["material"]}]
       :mark       {:type                 "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:title "material"
                              :field "material"
                              :type  "nominal"
                              :axis  {:labelAngle 60
                                      :labelBound 65}
                              :sort  {:field "avg-tonnes"
                                      :order "descending"}}
                    :y       {:title "average tonnes per year"
                              :field "avg-tonnes"
                              :type  "quantitative"}
                    :color   {:value "#A2CAC1"}
                    :tooltip [{:title "material"
                               :field "material"
                               :type  "nominal"}
                              {:title "tonnes"
                               :field "avg-tonnes"
                               :type  "quantitative"
                               :format ".3f"}]}})



(defn charts [material co2e]
       [:div
        [:div.columns
         [:column
          [oz/vega-lite (chart-spec-per-material material "Graph 2: Weight per material")
           util/vega-embed-opts]
          ]
         [:column
          [:div.m-4.content
           [:p "Graph 2 depicts the (all years average) tonnes per year of each (category of) material reused."
            " Textiles & footwear is by far the highest, followed by books then aggregates."]]
          ]]
        [:div.columns
         [:column
          [oz/vega-lite (assoc-in (chart-spec-per-material co2e "Graph 3: CO2e per material") [:encoding :color :value] "#BF5748")
           util/vega-embed-opts]]
         [:column
          [:div.m-4.content
           [:p "Graph 3 has the same basis as the previous one but this time it orders the material amounts "
            " by their CO" [:sub "2"] "e impacts."]
           [:p "Tonne-for-tonne, textiles & footwear accounts more CO" [:sub "2"] "e"
            " than any of the other materials, so it is good that textiles & footwear component is (by weight) the most reused of the materials!"]]]]])

(defn root []
      [charts
       @state/fairshare-derivation-material-cursor
       @state/fairshare-derivation-co2e-cursor])