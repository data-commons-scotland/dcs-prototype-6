(ns dcs.prototype-6.view.regional-dashboard.household-co2e-derivation-generation
  (:require
   [oz.core :as oz]
   [goog.string :as gstring]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.state :as state]))


(defn chart-spec [title region data]
      (let [year-count (count (group-by :year data))]
           {:schema     "https://vega.github.io/schema/vega/v5.json"
            :title      title
            :width      200
            :height     100
            :background "floralwhite"
            :data       {:values data}
            :mark       {:type "line" :point {:filled true}}
            :selection  {:my {:type   "multi"
                              :fields ["region"]
                              :bind   "legend"}}
            :encoding   {:x       {:field "year" :type "temporal" :timeUnit "year" :axis {:tickCount year-count :title "year"}}
                         :y       {:field "tonnes" :type "quantitative" :scale {:zero false} :axis {:title "tonnes"}}
                         :color   {:field "region" :type "nominal" :scale {:domain ["Scotland" region] :range ["#1f77b4" "#fdae6b"]} :legend nil #_{:orient "bottom" :columns 3}}
                         :opacity {:condition {:selection "my" :value 1}
                                   :value     0.2}
                         :tooltip [{:field "region" :type "nominal" :title "subject"}
                                   {:field "year" :type "temporal"}
                                   {:field "tonnes" :type "quantitative"}]}}))

(defn chart [region household-co2e-derivation-generation]
      (let [;; filter
            household-co2e-derivation-generation' (filter #(contains? #{"Scotland" region} (:region %)) household-co2e-derivation-generation)

            ;; stringify the year for Vega
            household-co2e-derivation-generation'' (map #(assoc % :year (str (:year %)))
                                             household-co2e-derivation-generation')]
           [:div
            [oz/vega-lite (chart-spec "Carbon impact per person" region household-co2e-derivation-generation'')
             util/vega-embed-opts]
            [:div#footnote-ref.content.has-text-left.has-text-info "See footnote" (gstring/unescapeEntities "&nbsp;") "[b] about " [:em "Carbon impact"] "."]
            ]))

(defn root []
      [chart @state/region-cursor @state/household-co2e-derivation-generation-cursor])