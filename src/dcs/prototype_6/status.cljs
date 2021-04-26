(ns dcs.prototype-6.status
  (:require [dcs.prototype-6.state :as state]))

(defn ele [a b c d e f g h i j k l m n o p]
      (let [num (->> [a b c d e f g h i j k l m n o p]
                     (filter nil?)
                     count)
            s (if (= 0 num)
                ""
                (str "Loading/deriving " num " datasets..."))]
           [:div
            [:p s]]))

(defn root []
      [ele

       @state/geojson-cursor

       @state/household-waste-derivation-generation-cursor
       @state/household-waste-derivation-percent-recycled-cursor
       @state/household-waste-derivation-management-cursor
       @state/household-waste-derivation-composition-cursor
       @state/household-waste-derivation-generation-positions-cursor
       @state/household-waste-derivation-percent-recycled-positions-cursor

       @state/household-co2e-derivation-generation-cursor
       @state/household-co2e-derivation-generation-positions-cursor

       @state/business-waste-by-region-derivation-generation-cursor
       @state/business-waste-by-region-derivation-composition-cursor

       @state/waste-site-derivation-cursor

       @state/stirling-bin-collection-derivation-generation-cursor
       @state/stirling-bin-collection-derivation-composition-cursor
       @state/stirling-bin-collection-derivation-percent-recycled-cursor
       @state/stirling-bin-collection-derivation-missed-bins-cursor])
