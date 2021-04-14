(ns dcs.prototype-6.ui-region-position
  (:require [clojure.string :as str]
            [dcs.prototype-6.state :as state]))


(defn suffix [n]
      (let [s (str n)]
           (cond
             (str/ends-with? s "11") "th"
             (str/ends-with? s "12") "th"
             (str/ends-with? s "13") "th"
             (str/ends-with? s "1") "st"
             (str/ends-with? s "2") "nd"
             :else "th")))


(defn ele [region
           household-waste-derivation-generation-positions
           household-waste-derivation-percent-recycled-positions
           household-co2e-derivation-generation-positions]

      [:div
       [:table#league-table.table.is-hoverable
        [:thead
         [:tr
          [:th ""]
          [:th {:colSpan 2} "Position"]]
         [:tr
          [:th "Aim"]
          [:th "Latest"]
          [:th "Trend"]]]

        [:tbody

         (let [latest-position (->> household-waste-derivation-generation-positions
                                    :latest-positions
                                    (filter #(= region (:region %)))
                                    first
                                    :position)
               trend-position (->> household-waste-derivation-generation-positions
                                   :trend-positions
                                   (filter #(= region (:region %)))
                                   first
                                   :position)]
              [:tr [:td "Reduce waste generation"]
               [:td (str latest-position (suffix latest-position))]
               [:td (str trend-position (suffix trend-position))]])

         (let [latest-position (->> household-waste-derivation-percent-recycled-positions
                                    :latest-positions
                                    (filter #(= region (:region %)))
                                    first
                                    :position)
               trend-position (->> household-waste-derivation-percent-recycled-positions
                                   :trend-positions
                                   (filter #(= region (:region %)))
                                   first
                                   :position)]
              [:tr [:td "Increase percentage recycled"]
               [:td (str latest-position (suffix latest-position))]
               [:td (str trend-position (suffix trend-position))]])

         (let [latest-position (->> household-co2e-derivation-generation-positions
                                    :latest-positions
                                    (filter #(= region (:region %)))
                                    first
                                    :position
                                    )
               trend-position (->> household-co2e-derivation-generation-positions
                                   :trend-positions
                                   (filter #(= region (:region %)))
                                   first
                                   :position)]
              [:tr [:td "Reduce carbon impact"]
               [:td (str latest-position (suffix latest-position))]
               [:td (str trend-position (suffix trend-position))]])]]])


(defn create []
      [ele
       @state/region-holder
       @state/household-waste-derivation-generation-positions-holder
       @state/household-waste-derivation-percent-recycled-positions-holder
       @state/household-co2e-derivation-generation-positions-holder])