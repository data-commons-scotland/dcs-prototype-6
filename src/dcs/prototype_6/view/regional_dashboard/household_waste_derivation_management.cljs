(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-management
  (:require
   [oz.core :as oz]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.annotation-mech :as anno-mech]
   [dcs.prototype-6.state :as state]))


(defn chart-spec [title _region data]
  (let [year-count (count (group-by :year data))
        layer-normal {:mark       {:type                 "bar"
                                   :cornerRadiusTopLeft  3
                                   :cornerRadiusTopRight 3}
                      :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                                   :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                                   :color   {:field  "management"
                                             :type   "nominal"
                                             :scale  {:domain ["Recycled" "Landfilled" "Other Diversion"]
                                                      :range  ["#bdbd8d" "#c49c49" "#c7c7c7"]}
                                             :legend nil}
                                   :tooltip [{:field "management" :type "nominal"}
                                             {:field "year" :type "temporal"}
                                             {:field "tonnes" :type "quantitative" :format ".3f"}]}}
        layer-annotations (-> anno-mech/layer-annotations
                              (assoc-in [:encoding :x] (-> layer-normal :encoding :x))
                              (assoc-in [:encoding :y] (-> layer-normal :encoding :y))
                              (assoc-in [:mark :dy] -8)
                              #_(assoc-in [:mark :dx] 0))]
    {:schema     "https://vega.github.io/schema/vega/v5.json"
     :title      title
     :width      200
     :height     100
     :background "floralwhite"
     :data       {:values data}
     :layer [layer-normal
             layer-annotations]}))

(defn chart [region household-waste-derivation-management annotations]
  (let [;; filter
        household-waste-derivation-management' (filter #(= region (:region %)) household-waste-derivation-management)

        ;; stringify the year for Vega
        household-waste-derivation-management'' (map #(assoc % :year (str (:year %)))
                                                     household-waste-derivation-management')

        ;; add annotation data
        household-waste-derivation-management''' (anno-mech/apply-annotations annotations household-waste-derivation-management'' :household-waste-derivation-management)]

    [:div
     [oz/vega-lite (chart-spec "Management" region household-waste-derivation-management''')
      util/vega-embed-opts]]))

(defn root []
  [chart @state/region-cursor @state/household-waste-derivation-management-cursor @state/annotations-derivation-cursor])