(ns dcs.prototype-6.status
  (:require [dcs.prototype-6.state :as state]))

(defn ele [a b c d e f g h i j k]
      (let [n (->> [a b c d e f g h i j k]
                   (filter nil?)
                   count)
            s (if (= 0 n)
                ""
                (str "Loading/deriving " n " datasets..."))]
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

                     @state/waste-site-derivation-holder])
