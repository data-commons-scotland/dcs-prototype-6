(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-percent-recycled
  (:require
   [oz.core :as oz]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.annotation-mech :as anno-mech]
   [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
  (let [year-count (count (group-by :year data))
        layer-normal {:mark       {:type "line" :point {:filled true}}
                      :encoding   {:x       {:field    "year"
                                             :type     "temporal"
                                             :timeUnit "year"
                                             :axis     {:tickCount year-count
                                                        :title     "year"}}
                                   :y       {:field "percentage"
                                             :type  "quantitative"
                                             :scale {:zero false}
                                             :axis  {:title "percentage"}}
                                   :strokeDash {:condition {:test  "datum.region == 'Scot gov target'"
                                                            :value [3 3]}
                                                :value     [0]}
                                   :color   {:field "region" :type "nominal"
                                             :scale {:domain [region "Scotland" "Scot gov target"]
                                                     :range  ["#fdae6b" "#1f77b4" "lightgrey"]}
                                             :legend nil #_{:orient "bottom" :columns 3}}
                                   :tooltip [{:field "region" :type "nominal" :title "subject"}
                                             {:field "year" :type "temporal"}
                                             {:field "percentage" :type "quantitative" :format ".3f"}]}}
        layer-annotations (-> anno-mech/layer-annotations
                              (assoc-in [:encoding :x] (-> layer-normal :encoding :x))
                              (assoc-in [:encoding :y] (-> layer-normal :encoding :y))
                              (assoc-in [:mark :dy] -8)
                              (assoc-in [:mark :dx] 0))]
    {:schema     "https://vega.github.io/schema/vega/v5.json"
     :title      title
     :width      200
     :height     100
     :background "floralwhite"
     :data       {:values data}
     :layer [layer-normal
             layer-annotations]}))

(def explain-scotGovTarget-percentageRecycled
  [:span "By 2025, the Scottish Government aims to recycle 70% of waste."])

(defn chart [region household-waste-derivation-percent-recycled]
  (let [;; filter
        household-waste-derivation-percent-recycled' (filter #(contains? #{region "Scotland" "Scot gov target"} (:region %)) household-waste-derivation-percent-recycled)

        ;; stringify the year for Vega
        household-waste-derivation-percent-recycled'' (map #(assoc % :year (str (:year %)))
                                                           household-waste-derivation-percent-recycled')

        ;; add annotation data
        household-waste-derivation-percent-recycled''' (anno-mech/apply-annotations household-waste-derivation-percent-recycled'' :household-waste-derivation-percent-recycled)]

    [:div
     [oz/vega-lite (chart-spec "% recycled" region household-waste-derivation-percent-recycled''')
      util/vega-embed-opts]]))

(defn root []
  [chart @state/region-cursor @state/household-waste-derivation-percent-recycled-cursor])