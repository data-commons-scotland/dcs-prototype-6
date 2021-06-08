(ns dcs.prototype-6.view.stirling-community-food.flow
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [dcs.prototype-6.state :as state]
    #_[dcs.prototype-6.view.stirling-community-food.map :as map]))

(defn chart-config [flow]
  {
   :chart         {:backgroundColor "#980f3d"}
   :navigation    {:buttonOptions {:enabled false}}
   :title         nil                                       ;{:text "The flow of food material"}
   :subtitle      nil                                       ;{:text "subtitle does here"}
   :accessibility {:point {:valueDescriptionFormat "{index}. {point.from} to {point.to}, {point.weight}."}}
   :tooltip       {:headerFormat nil
                   :pointFormat  "{point.fromNode.name} \u2192 {point.toNode.name}: {point.weight:.2f} tonnes"
                   :nodeFormat   "{point.name}: {point.sum:.2f} tonnes"}
   :plotOptions {:sankey {
                          ;:label { :minFontSize 4}
                          :nodePadding 20
                          :dataLabels {
                          ;             :crop false
                          ;             :overflow "allow"
                                        :allowOverlap true}
                          }}
   :series        [{:keys         ["from" "to" "weight"]
                    :minLinkWidth 6
                    :label        {:minFontSize 6}
                    :nodes        [{:id "Purchased" :color "#FFC473"}
                                   {:id "Donated not waste" :color "#7158A1"}
                                   {:id "Donated as waste" :color "#BF5748"}
                                   {:id "Other" :color "#928E85"}
                                   {:id "Local supermarkets" :color "#006CAE"}
                                   {:id "Fareshare" :color "#59896A"}
                                   {:id "Would-be waste" :color "#EF0606"}
                                   {:id "Not waste" :color "#00C9A9"}
                                   {:id "Stirling Community Food" :color "#009790"}
                                   {:id "Used as food" :color "#00AC8F" :level 4 :offset -100}
                                   {:id "Not used as food" :color "#98B0A9" :offset 105}
                                   {:id "Donated to animal sanctuary" :color "#006AC7" :offset 280}
                                   {:id "Used by individuals for compost" :color "#B49531" :offset 280}
                                   {:id "Council compost, Energen biogas, etc." :color "#E27E44" :offset 280}]
                    :data         flow
                    :type         "sankey"
                    :name         "The flow of food material"}]
   :credits       {:enabled false}
   })

(defn did-mount [this]
      (let [flow (:data (r/props this))]
        (js/Highcharts.Chart. (rdom/dom-node this) (clj->js (chart-config flow)))))

(defn did-update [this prev-props]
      (did-mount this)) ;; TODO figure out a more surgical means to update the chart

(defn render []
      [:div {:style {:min-width "340px" :max-width "880px"
                     :height    "400px" :margin "0"}}])

(defn component []
      (r/create-class {:reagent-render       render
                       :component-did-mount  did-mount
                       :component-did-update did-update}))

(defn root []

      [:div.tile.is-ancestor

       [:div.tile
        [:div.tile.is-vertical.is-parent

         [:div.tile.is-child
          [component {:data @state/stirling-community-food-tonnes-derivation-flow-cursor}]]
         [:div.tile.is-child.has-text-primary-light.content
          [:p "Food materials flow left-to-right through Stirling Community Food's process."
          " On the left are the sources: aggregations of supermarkets, collection agencies, and donors."
           " On the right are the outcomes, broadly: used as food, or not."
           " (Hover over a part to display the tonnes of food material flowing through that part of the process, during the year.)"]
          ]]]])

