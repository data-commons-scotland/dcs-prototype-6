(ns dcs.prototype-6.view.stirling-community-food.flow
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [dcs.prototype-6.state :as state]
    #_[dcs.prototype-6.view.stirling-community-food.map :as map]))

(def chart-config
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
                    :data         [["Foodcloud" "Would-be waste" 7.605]
                                   ["Other" "Would-be waste" 7.388097299999997]
                                   ["Neighbourly" "Would-be waste" 31.66009500000001]
                                   ["Sainsbury's" "Would-be waste" 1.5968]
                                   ["Donated as waste" "Would-be waste" 2.9908609999999998]
                                   ["Cooperative" "Would-be waste" 1.7901099999999994]
                                   ["Fairshare" "Would-be waste" 26.792366000000005]
                                   ["Donated not waste" "Not waste" 2.296225000000002]
                                   ["Purchased" "Not waste" 2.07525]
                                   ["Would-be waste" "Stirling Community Food" 79.82332930000001]
                                   ["Not waste" "Stirling Community Food" 4.371475000000002]
                                   ["Stirling Community Food" "Not wasted" 77.06230000000005]
                                   ["Not wasted" "Used as food" 73.69748999999999]
                                   ["Not wasted" "Donated to animal scantuary" 1.97098]
                                   ["Not wasted" "Used for compost" 1.39383]
                                   ["Stirling Community Food" "Disposed of as waste" 3.6506199999999973]]
                    :type         "sankey"
                    :name         "The flow of food material"}]
   :credits       {:enabled false}
   })

(defn home-did-mount [this]
      (js/Highcharts.Chart. (rdom/dom-node this) (clj->js chart-config)))

(defn home-render []
      [:div {:style {:min-width "310px" :max-width "700px"
                     :height    "400px" :margin "0"}}])

(defn root []
      (r/create-class {:reagent-render      home-render
                       :component-did-mount home-did-mount}))

