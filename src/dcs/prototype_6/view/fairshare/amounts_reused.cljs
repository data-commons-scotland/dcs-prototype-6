(ns dcs.prototype-6.view.fairshare.amounts-reused
  (:require
    [reagent.core :as r]
    [oz.core :as oz]
    [goog.string :as gstring]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))

(defn chart-spec-per-season
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      400
       :height     250
       :background "#fff1e5"
       :data       {:values data}
       :transform  [{:timeUnit "yearmonth" :field "yyyy-MM-dd" :as "month"}
                    {:aggregate [{:op "sum" :field "tonnes" :as "tonnes"}]
                     :groupby   ["month" "material"]}]
       :mark       {:type  "line"
                    :point {:filled false :fill "#fff1e5"}}
       :encoding   {:x       {:field "month" :type "temporal"
                              :axis  {:format     "%b %y"
                                      :labelAngle 60
                                      :labelBound 65
                                      :tickCount  {:interval "month" :step 3 :start 0}}}
                    :y       {:field "tonnes" :type "quantitative"}
                    :color   {:field "material" :type "nominal"
                              :scale {:scheme "tableau20"}
                              ;:legend {:orient "bottom" :columns 4}
                              }
                    :tooltip [{:field "material" :type "nominal"}
                              {:field "month" :type "temporal" :format "%b %y"}
                              {:field "tonnes" :type "quantitative"}]}})

(defn chart-spec-per-season-non-linear
      [data]
      (assoc-in (chart-spec-per-season data) [:encoding :y :scale] {:type "pow" :exponent 0.25}))


(defn charts [donations]
      [:div.tile.is-ancestor

       [:div.tile.is-8
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child
          [oz/vega-lite (chart-spec-per-season donations)
           util/vega-embed-opts]]
         [:div.tile.is-child
          [oz/vega-lite (chart-spec-per-season-non-linear donations)
           util/vega-embed-opts]]]]

       [:div.tile
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child.content
          [:blockquote
           [:p "TODO:"]
           [:ul
            [:li "The graph shows ...ETC ETC"]
            ]]]]]

       ])

(defn root []
      [charts
       @state/fairshare-donations-derivation-cursor])