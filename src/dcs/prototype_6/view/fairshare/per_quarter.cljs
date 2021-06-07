(ns dcs.prototype-6.view.fairshare.per-quarter
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-per-season
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      370
       :height     200
       :background "#f2dfce"
       :data       {:values data}
       :transform  [{:timeUnit "yearquarter" :field "yyyy-MM-dd" :as "quarter"}
                    {:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby ["quarter"]}]
       :mark       {:type "bar"
                    :cornerRadiusTopLeft  3
                    :cornerRadiusTopRight 3}
       :encoding   {:x       {:title "year quarter"
                              :field "quarter" :type "temporal"
                              :axis {:labelExpr "timeFormat(datum.value, '%q') == '1' ? timeFormat(datum.value, 'Q%q %Y') : timeFormat(datum.value, 'Q%q')"
                                     :labelAngle 90
                                     :tickCount {:interval "month" :step 3 :start 0}}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :color   {:value "#A2CAC1"}
                    :tooltip [{:title "year quarter" :field "quarter" :type "temporal" :format "Q%q %Y"}
                              {:field "tonnes" :type "quantitative"}]}})


(defn charts [material]
      [:div.tile.is-ancestor

       [:div.tile.is-8
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child
          [oz/vega-lite (chart-spec-per-season material)
           util/vega-embed-opts]]
         ]]

       [:div.tile
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child.content
         [:blockquote
          [:p "TODO:"]
          [:ul
           [:li "The graph shows ...ETC ETC"]
           [:li "Would also be interesting to calculate using the Carbon Metric"]
           ]]]
        ]]

       ])

(defn root []
      [charts
       @state/fairshare-material-derivation-cursor])