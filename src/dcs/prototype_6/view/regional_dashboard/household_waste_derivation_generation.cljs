(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation
  (:require
   [oz.core :as oz]
   [goog.string :as gstring]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.annotation-mech :as anno-mech]
   [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
  (let [year-count (count (group-by :year data))
        layer-normal {:mark       {:type  "line"
                                   :point {:filled true}}
                      :encoding   {:x          {:field    "year"
                                                :type     "temporal"
                                                :timeUnit "year"
                                                :axis     {:tickCount year-count
                                                           :title     "year"}}
                                   :y          {:field "tonnes"
                                                :type  "quantitative"
                                                :scale {:zero false}
                                                :axis  {:title "tonnes"}}
                                   :strokeDash {:condition {:test  "datum.region == 'Scot gov target'"
                                                            :value [3 3]}
                                                :value     [0]}
                                   :color      {:field  "region"
                                                :type   "nominal"
                                                :scale  {:domain [region "Scotland" "Scot gov target"]
                                                         :range  ["#fdae6b" "#1f77b4" "lightgrey"]}
                                                :legend {:title   nil
                                                         :orient  "bottom"
                                                         :columns 3}}
                                   :tooltip    [{:field "region"
                                                 :type  "nominal"
                                                 :title "subject"}
                                                {:field "year"
                                                 :type  "temporal"}
                                                {:field "tonnes"
                                                 :type  "quantitative"
                                                 :format ".3f"}]}}
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

(defn chart [region household-waste-derivation-generation]
  (let [;; filter
        household-waste-derivation-generation' (filter #(contains? #{region "Scotland" "Scot gov target"} (:region %)) household-waste-derivation-generation)

        ;; stringify the year for Vega
        household-waste-derivation-generation'' (map #(assoc % :year (str (:year %)))
                                                     household-waste-derivation-generation')

        ;; add annotation data
        household-waste-derivation-generation''' (anno-mech/apply-annotations household-waste-derivation-generation'' :household-waste-derivation-generation)]

    [:div
     [oz/vega-lite (chart-spec "Waste generated per person" region household-waste-derivation-generation''')
      util/vega-embed-opts]
     [:div#footnote-ref.content.has-text-left.has-text-info "See footnote" (gstring/unescapeEntities "&nbsp;") "[a] about the " [:em "Scot gov target"] "."]]))

(defn root []
  [chart @state/region-cursor @state/household-waste-derivation-generation-cursor])