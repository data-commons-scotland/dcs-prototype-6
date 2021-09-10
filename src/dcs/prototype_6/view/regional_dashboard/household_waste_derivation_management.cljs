(ns dcs.prototype-6.view.regional-dashboard.household-waste-derivation-management
  (:require
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec [title _region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type                 "bar"
                         :cornerRadiusTopLeft  3
                         :cornerRadiusTopRight 3}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field  "management"
                                   :type   "nominal"
                                   :scale  {:domain ["Recycled" "Landfilled" "Other Diversion"]
                                            :range  ["#bdbd8d" "#c49c49" "#c7c7c7"]}
                                   :legend nil}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "management" :type "nominal"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [region household-waste-derivation-management]
      (let [;; filter
            household-waste-derivation-management' (filter #(= region (:region %)) household-waste-derivation-management)

            ;; stringify the year for Vega
            household-waste-derivation-management'' (map #(assoc % :year (str (:year %)))
                                                         household-waste-derivation-management')]
           [:div
            [oz/vega-lite (chart-spec "Management" region household-waste-derivation-management'')
             util/vega-embed-opts]]))

(defn root []
      [chart @state/region-cursor @state/household-waste-derivation-management-cursor])