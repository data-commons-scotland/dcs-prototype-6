(ns dcs.prototype-6.view.household-waste-analysis.composition
  (:require
   [oz.core :as oz]
   [goog.string :as gstring]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.state :as state]))

(def stratum-labels ["urban £" "urban ££" "urban £££" "rural £/££" "rural £££"])

(defn chart-spec
  [chart-data]
  {:schema     "https://vega.github.io/schema/vega/v5.json"
   :background "#f2dfce"
   :width      400
   :height     250
   :data       {:values chart-data}
   :transform  [{:aggregate [{:op "sum" :field "kgPerHhPerWk" :as "kgPer2Wks"}]
                 :groupby ["stratum" "material-L1"]}
                {:calculate "datum.kgPer2Wks / 2" :as "kg"}] ;; to average over the 2 phases
   :mark       {:type "bar"
                :cornerRadiusTopLeft 3
                :cornerRadiusTopRight 3}
   :encoding   {:x       {:field "stratum" :type "nominal"
                          :sort stratum-labels
                          :axis {:title "household type (location type & council tax band)"
                                 :labelAngle 0}}
                :y       {:field "kg" :type "quantitative"
                          :axis {:title "average kg/hh/wk"}}
                :color {:field "material-L1" :type "nominal"
                        :legend {:title "material (high level)"}}
                :tooltip [{:field "stratum" :type "nominal" :title "house type (location & CTax)"}
                          {:field "material-L1" :type "nominal" :title "material (high level)"}
                          {:field "kg" :type "quantitative" :title "avg kg per household per wk"}]}})

(defn charts [derivation]
  (let [chart-data derivation]
    [:div.columns
     [:column
      [oz/vega-lite (chart-spec chart-data)
       util/vega-embed-opts]
      ]
     [:column
      [:div.m-4.content
       [:p.has-text-info.has-text-weight-bold "Hover/click on part of the graph to be shown more detail."]
       [:p "This graph shows that " [:span.has-text-warning "Food wastes"] " and " [:span.has-text-warning "Paper and cardboard"] 
        " form significant components of binned material, across all 5 household types."]
       [:p [:span.has-text-warning "rural £/££"]" households dispose of a lot of fine-grained material," 
        " i.e." (gstring/unescapeEntities "&nbsp;") [:span.has-text-warning "Fines" (gstring/unescapeEntities "&nbsp;") "(<10mm)"] "."
        " ...What is it? ...Soil/dust/dirt?"]
       ]]]))

(defn root []
      [charts
       @state/household-waste-analysis-derivation-cursor])
