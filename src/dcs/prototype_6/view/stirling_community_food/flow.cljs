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
   ;:plotOptions {:sankey {:label { :minFontSize 4}}}
   :series        [{:keys         ["from" "to" "weight"]
                    :minLinkWidth 17
                    :label        {:minFontSize 6}
                    :nodes        [{:id "Would-be waste" :color "red"}
                                   {:id "Not waste" :color "#00C9A9"}
                                   {:id "Stirling Community Food" :color "#B3AA99"}
                                   {:id "Not wasted" :color "#00C9A9"}
                                   {:id "Used as food" :color "#B6E6F6"}
                                   {:id "Donated to animal scantuary" :color "#AC8E00"}
                                   {:id "Used for compost" :color "#A16A51"}
                                   {:id "Disposed of as waste" :color "red" :level 4}]
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
      [:div {:style {:min-width "310px" :max-width "800px"
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
          " On the left are the sources: supermarkets, collection aggregators, donors."
           " On the right are the outcomes, broadly: not wasted, or waste was unavoidable."
           " (Hover over a part to display the tonnes of food material flowing through that part of the process, during the year.)"]]]]])

