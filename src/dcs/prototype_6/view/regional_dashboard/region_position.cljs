(ns dcs.prototype-6.view.regional-dashboard.region-position
  (:require [clojure.string :as str]
            [dcs.prototype-6.state :as state]))

(defn colour [n]
      (if (< n 17)
        "has-text-success-dark"
        "has-text-danger-dark"))

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
          [:th "Trend"]
          [:th "Latest"]]]

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
               [:td {:class (colour latest-position)} (str latest-position (suffix latest-position))]
               [:td {:class (colour trend-position)} (str trend-position (suffix trend-position))]])

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
               [:td {:class (colour latest-position)} (str latest-position (suffix latest-position))]
               [:td {:class (colour trend-position)} (str trend-position (suffix trend-position))]])

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
               [:td {:class (colour latest-position)} (str latest-position (suffix latest-position))]
               [:td {:class (colour trend-position)} (str trend-position (suffix trend-position))]])]]])


(defn root []
      [ele
       @state/region-cursor
       @state/household-waste-derivation-generation-positions-cursor
       @state/household-waste-derivation-percent-recycled-positions-cursor
       @state/household-co2e-derivation-generation-positions-cursor])