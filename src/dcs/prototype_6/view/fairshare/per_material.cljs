(ns dcs.prototype-6.view.fairshare.per-material
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-per-material
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "#fff1e5"
       :data       {:values data}
       :transform  [{:timeUnit "year" :field "yyyy-MM-dd" :as "year"}
                    {:aggregate [{:op "average" :field "tonnes" :as "avg-tonnes"}]
                     :groupby ["materialx"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:title "material"
                              :field "materialx" :type "nominal"
                              :axis {:labelAngle 60
                                     :labelBound 65}
                              :sort {:field "avg-tonnes" :order "descending"}}
                    :y       {:title "average tonnes per year"
                              :field "avg-tonnes" :type "quantitative"}
                    :color   {:value "#A2CAC1"}
                    :tooltip [{:title "material" :field "materialx" :type "nominal"}
                              {:title "tonnes" :field "avg-tonnes" :type "quantitative"}]}})



(defn charts [material co2e]
      [:div.tile.is-ancestor
       [:div.tile.is-vertical.is-parent

        [:div.tile.is-parent ;.is-align-items-center
       [:div.tile.is-child.is-5
        [oz/vega-lite (chart-spec-per-material material)
         util/vega-embed-opts]
        ]
         [:div.tile.is-child.content
          [:p "This graph depicts the (average) tonnes per year of each (category of) material reused."
           " Textiles & footwear is by far the highest, followed by books then aggregates."]]]

       [:div.tile.is-parent ;.is-align-items-center
        [:div.tile.is-child.is-5
         [oz/vega-lite (assoc-in (chart-spec-per-material co2e) [:encoding :color :value] "#BF5748")
                          util/vega-embed-opts]]
        [:div.tile.is-child.content
         [:p "This graph has the same basis as the previous one but this time it orders the material amounts "
          " by their CO" [:sub "2"] "e impacts."]
         [:p "Tonne-for-tonne, textiles & footwear accounts more CO" [:sub "2"] "e"
          " than any of the other materials, so it is good that textiles & footwear component is (by weight) the most reused of the materials!"]
         ]]

       ]])

(defn root []
      [charts
       @state/fairshare-material-derivation-cursor
       @state/fairshare-co2e-derivation-cursor])