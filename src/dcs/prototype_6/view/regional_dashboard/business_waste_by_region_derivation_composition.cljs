(ns dcs.prototype-6.view.regional-dashboard.business-waste-by-region-derivation-composition
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.annotation-mech :as anno-mech]
    [dcs.prototype-6.state :as state]))



  (defn chart-spec [title _region data]
    (let [year-count (count (group-by :year data))
          layer-normal {:mark {:type                 "bar"
                               :cornerRadiusTopLeft  3
                               :cornerRadiusTopRight 3}
                        :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                                     :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                                     :color   {:field "material" :type "nominal" :scale {:scheme "tableau20"} :legend nil #_{:orient "bottom" :columns 3}}
                                     :tooltip [{:field "material" :type "nominal"}
                                               {:field "year" :type "temporal"}
                                               {:field "tonnes" :type "quantitative"}]}}
          layer-annotations (-> anno-mech/layer-annotations
                                (assoc-in [:encoding :x] (-> layer-normal :encoding :x))
                                (assoc-in [:encoding :y] (-> layer-normal :encoding :y))
                                (assoc-in [:mark :dy] -50)
                                (assoc-in [:mark :dx] 0))]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :title      title
       :width      200
       :height     100
       :background "floralwhite"
       :data       {:values data}
       :layer [layer-normal
               layer-annotations]}))


(defn chart [region business-waste-by-region-derivation-composition]
      (let [;; filter
            business-waste-by-region-derivation-composition' (filter #(= region (:region %)) business-waste-by-region-derivation-composition)

            ;; stringify the year for Vega
            business-waste-by-region-derivation-composition'' (map #(assoc % :year (str (:year %)))
                                                                   business-waste-by-region-derivation-composition')
            
            ;; add annotation data
            business-waste-by-region-derivation-composition''' (anno-mech/apply-annotations business-waste-by-region-derivation-composition'' :business-waste-by-region-derivation-composition)]
 
        [:div
         [oz/vega-lite (chart-spec "Composition" region business-waste-by-region-derivation-composition''')
          util/vega-embed-opts]]))

(defn root []
      [chart @state/region-cursor @state/business-waste-by-region-derivation-composition-cursor])