(ns dcs.prototype-6.status
  (:require [dcs.prototype-6.state :as state]))

(defn ele [a b c d e f g h i j k l m n o p q r s t u v w x y z a2 b2 c2]
      (let [num (->> [a b c d e f g h i j k l m n o p q r s t u v w x y z a2 b2 c2]
                     (filter nil?)
                     count)
            s (if (= 0 num)
                ""
                [:span.icon-text
                 [:span.icon [:i.fas.fa-spinner.fa-pulse]]
                 (str " please wait - loading/deriving " num " datasets")])]
           [:div
            [:p s]]))

(defn root []
      [ele

       @state/geojson-cursor

       @state/meta-derivation-cursor

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
       @state/stirling-bin-collection-derivation-missed-bins-cursor

       @state/stirling-community-food-tonnes-derivation-tonnes-cursor
       @state/stirling-community-food-tonnes-derivation-flow-cursor
       @state/stirling-community-food-footfall-derivation-cursor

       @state/fairshare-derivation-material-cursor
       @state/fairshare-derivation-co2e-cursor

       @state/ace-furniture-derivation-count-cursor
       @state/ace-furniture-derivation-category-trends-cursor
       @state/ace-furniture-derivation-item-trends-cursor
       @state/ace-furniture-derivation-weight-cursor
       @state/ace-furniture-derivation-flights-per-category-cursor
       @state/ace-furniture-derivation-flights-per-item-cursor

       @state/household-waste-analysis-derivation-cursor])
