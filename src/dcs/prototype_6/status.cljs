(ns dcs.prototype-6.status
  (:require [dcs.prototype-6.state :as state]))

(defn ele [a b c d e f g h i j k l m n o]
      (let [num (->> [a b c d e f g h i j k l m n o]
                     (filter nil?)
                     count)
            s (if (= 0 num)
                ""
                (str "Loading/deriving " num " datasets..."))]
           [:div
            [:p s]]))

(defn root []
      [ele @state/household-waste-derivation-generation-holder
       @state/household-waste-derivation-percent-recycled-holder
       @state/household-waste-derivation-management-holder
       @state/household-waste-derivation-composition-holder
       @state/household-waste-derivation-generation-positions-holder
       @state/household-waste-derivation-percent-recycled-positions-holder

       @state/household-co2e-derivation-generation-holder
       @state/household-co2e-derivation-generation-positions-holder

       @state/business-waste-by-region-derivation-generation-holder
       @state/business-waste-by-region-derivation-composition-holder

       @state/waste-site-derivation-holder

       @state/stirling-bin-collection-derivation-generation-holder
       @state/stirling-bin-collection-derivation-composition-holder
       @state/stirling-bin-collection-derivation-percent-recycled-holder
       @state/stirling-bin-collection-derivation-missed-holder])
