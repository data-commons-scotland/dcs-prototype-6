(ns dcs.prototype-6.deriver
  (:require [kixi.stats.core :as stats]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.data-shaping :as data-shaping]))


; Compute 'the trend of y'.
; (Returns the gradient of a linear approximation to the curve decribed by xy-pairs.)
(defn trend [xy-pairs]
      (let [rf (stats/simple-linear-regression first second)
            ^js jsobj (transduce identity rf xy-pairs)]
           (. jsobj -slope)))


(defn maybe-calc-household-waste-derivations []
      (let [household-waste @state/household-waste-holder
            population @state/population-holder]

           (when (and (some? household-waste)
                      (some? population))
                 (js/console.log "Calculating household-waste-derivations")

                 (let [start-time (util/now)

                       ;; Roll-up to get values for (region, year) pairs
                       household-waste-derivation-generation0 (data-shaping/rollup-household-waste-materials-and-management household-waste)

                       ;; Roll-up to get values for (region, year, management) triples
                       household-waste-derivation-management0 (data-shaping/rollup-household-waste-materials household-waste)

                       ;; Roll-up to get values for (region, year, material) triples
                       household-waste-derivation-composition0 (data-shaping/rollup-household-waste-managements household-waste)

                       ;; Prep for the per citizen calculation
                       population-for-lookup (group-by (juxt :region :year) population)
                       lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

                       ;; Calculate the per citizen values
                       household-waste-derivation-generation (map (fn [{:keys [region year tonnes]}] {:region region
                                                                                                      :year   year
                                                                                                      :tonnes (double (/ tonnes (lookup-population region year)))})
                                                                  household-waste-derivation-generation0)
                       household-waste-derivation-management (map (fn [{:keys [region year management tonnes]}] {:region     region
                                                                                                                 :year       year
                                                                                                                 :management management
                                                                                                                 :tonnes     (double (/ tonnes (lookup-population region year)))})
                                                                  household-waste-derivation-management0)
                       household-waste-derivation-composition (map (fn [{:keys [region year material tonnes]}] {:region   region
                                                                                                                :year     year
                                                                                                                :material material
                                                                                                                :tonnes   (double (/ tonnes (lookup-population region year)))})
                                                                   household-waste-derivation-composition0)

                       ;; Calculate the percentage recycled values
                       household-waste-derivation-percent-recycled (data-shaping/calc-household-waste-percentage-recycled household-waste)

                       ;; Calculate positions
                       latest-year (->> household-waste
                                        (map :year)
                                        (apply max))
                       household-waste-derivation-generation-positions {:latest-positions (->> household-waste-derivation-generation
                                                                                               (remove #(= "Scotland" (:region %)))
                                                                                               (filter #(= latest-year (:year %)))
                                                                                               (sort-by :tonnes)
                                                                                               (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                        :position (inc ix)
                                                                                                                        :year     latest-year})))
                                                                        :trend-positions  (->> household-waste-derivation-generation
                                                                                               (remove #(= "Scotland" (:region %)))
                                                                                               (group-by :region)
                                                                                               (map (fn [[region coll]] {:region region
                                                                                                                         :trend  (->> coll
                                                                                                                                      (map #(vector (double (:year %)) (:tonnes %)))
                                                                                                                                      trend)}))
                                                                                               (sort-by :trend)
                                                                                               (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                        :position (inc ix)
                                                                                                                        :trend    (:trend trend)})))}

                       household-waste-derivation-percent-recycled-positions {:latest-positions (->> household-waste-derivation-percent-recycled
                                                                                                     (remove #(= "Scotland" (:region %)))
                                                                                                     (filter #(= latest-year (:year %)))
                                                                                                     (sort-by :percentage)
                                                                                                     reverse
                                                                                                     (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                              :position (inc ix)
                                                                                                                              :year     latest-year})))
                                                                              :trend-positions  (->> household-waste-derivation-percent-recycled
                                                                                                     (group-by :region)
                                                                                                     (map (fn [[region coll]] {:region region
                                                                                                                               :trend  (->> coll
                                                                                                                                            (map #(vector (double (:year %)) (:percentage %)))
                                                                                                                                            trend)}))
                                                                                                     (sort-by :trend)
                                                                                                     reverse
                                                                                                     (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                              :position (inc ix)
                                                                                                                              :trend    (:trend trend)})))}]

                      (reset! state/household-waste-derivation-generation-cursor household-waste-derivation-generation)
                      (reset! state/household-waste-derivation-percent-recycled-cursor household-waste-derivation-percent-recycled)
                      (reset! state/household-waste-derivation-management-cursor household-waste-derivation-management)
                      (reset! state/household-waste-derivation-composition-cursor household-waste-derivation-composition)
                      (reset! state/household-waste-derivation-generation-positions-cursor household-waste-derivation-generation-positions)
                      (reset! state/household-waste-derivation-percent-recycled-positions-cursor household-waste-derivation-percent-recycled-positions)
                      (js/console.log (str "Calculating household-waste-derivations: secs-taken=" (util/secs-to-now start-time)))))))


(defn maybe-calc-household-co2e-derivations []
      (let [household-co2e @state/household-co2e-holder
            population @state/population-holder]

           (when (and (some? household-co2e)
                      (some? population))
                 (js/console.log "Calculating household-co2e-derivations")

                 (let [start-time (util/now)

                       ;; Prep for the per citizen calculation
                       population-for-lookup (group-by (juxt :region :year) population)
                       lookup-population (fn [region year] (-> population-for-lookup (get [region year]) first :population))

                       ;; Calculate the per citizen values
                       household-co2e-derivation-generation (map (fn [{:keys [region year tonnes]}] {:region region
                                                                                                     :year   year
                                                                                                     :tonnes (double (/ tonnes (lookup-population region year)))})
                                                                 household-co2e)

                       ;; Calculate positions
                       latest-year (->> household-co2e
                                        (map :year)
                                        (apply max))
                       household-co2e-derivation-generation-positions {:latest-positions (->> household-co2e-derivation-generation
                                                                                              (remove #(= "Scotland" (:region %)))
                                                                                              (filter #(= latest-year (:year %)))
                                                                                              (sort-by :tonnes)
                                                                                              (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                       :position (inc ix)
                                                                                                                       :year     latest-year})))
                                                                       :trend-positions  (->> household-co2e-derivation-generation
                                                                                              (remove #(= "Scotland" (:region %)))
                                                                                              (group-by :region)
                                                                                              (map (fn [[region coll]] {:region region
                                                                                                                        :trend  (->> coll
                                                                                                                                     (map #(vector (double (:year %)) (:tonnes %)))
                                                                                                                                     trend)}))
                                                                                              (sort-by :trend)
                                                                                              (map-indexed (fn [ix m] {:region   (:region m)
                                                                                                                       :position (inc ix)
                                                                                                                       :trend    (:trend trend)})))}]

                      (reset! state/household-co2e-derivation-generation-cursor household-co2e-derivation-generation)
                      (reset! state/household-co2e-derivation-generation-positions-cursor household-co2e-derivation-generation-positions)
                      (js/console.log (str "Calculating household-co2e-derivations: secs-taken=" (util/secs-to-now start-time)))))))


(defn maybe-calc-business-waste-by-region-derivations []
      (let [business-waste-by-region @state/business-waste-by-region-holder]

           (when (some? business-waste-by-region))
           (js/console.log "Calculating business-waste-by-region-derivations")

           (let [start-time (util/now)

                 region-count (->> business-waste-by-region
                                   (map :region)
                                   distinct
                                   count)

                 ;; Roll-up to get values for (region, year) pairs
                 business-waste-by-region-derivation-generation0 (data-shaping/rollup-business-waste-by-region-materials business-waste-by-region)

                 ;; Scotland (total) -> Scotland average
                 business-waste-by-region-derivation-generation (map (fn [{:keys [region year tonnes] :as original}] (if (= "Scotland" region)
                                                                                                                       {:region "Scotland average"
                                                                                                                        :year   year
                                                                                                                        :tonnes (double (/ tonnes region-count))}
                                                                                                                       original))
                                                                     business-waste-by-region-derivation-generation0)

                 ;; No actual deriving needed for the composition
                 business-waste-by-region-derivation-composition business-waste-by-region]

                (reset! state/business-waste-by-region-derivation-generation-cursor business-waste-by-region-derivation-generation)
                (reset! state/business-waste-by-region-derivation-composition-cursor business-waste-by-region-derivation-composition)
                (js/console.log (str "Calculating business-waste-by-region-derivations: secs-taken=" (util/secs-to-now start-time))))))


(defn maybe-calc-waste-site-derivations []
      (let [waste-site @state/waste-site-holder]

           (when (some? waste-site))
           (js/console.log "Calculating waste-site-derivations")

           (let [start-time (util/now)

                 waste-site-derivation (data-shaping/count-waste-sites-per-category-per-region waste-site)]

                (reset! state/waste-site-derivation-cursor waste-site-derivation)
                (js/console.log (str "Calculating waste-site-derivations: secs-taken=" (util/secs-to-now start-time))))))


(defn maybe-calc-stirling-bin-collection-derivations []
      (let [stirling-bin-collection @state/stirling-bin-collection-holder
            population @state/population-holder]

           (when (and (some? stirling-bin-collection)
                      (some? population))
                 (js/console.log "Calculating stirling-bin-collection-derivations")

                 (let [start-time (util/now)

                       ;; Roll-up to get values for (region, year, quarter) triples
                       derivation-generation0 (data-shaping/rollup-stirling-bin-collection-ma-re-mi stirling-bin-collection)

                       ;; Roll-up to get values for (region, year, quarter, material) quadruples
                       derivation-composition0 (data-shaping/rollup-stirling-bin-collection-re-mi stirling-bin-collection)

                       ;; Calculate the percentage recycled values
                       derivation-percent-recycled (data-shaping/calc-stirling-bin-collection-percentage-recycled stirling-bin-collection)

                       ;; Filter for missed-bin? then roll-up to get values for (year, quarter) pairs
                       derivation-missed (->> stirling-bin-collection
                                              (filter :missed-bin?)
                                              (group-by (juxt :year :quarter))
                                              (map (fn [[[year quarter] coll]] {:year    year
                                                                                :quarter quarter
                                                                                :tonnes  (->> coll
                                                                                              (map :tonnes)
                                                                                              (apply +))})))

                       ;; Prep for the per citizen calculation
                       population-max-year (->> population (map :year) (apply max)) ;; assume all regions have the same max year
                       population-for-lookup (group-by (juxt :region :year) population)
                       lookup-population (fn [region year] (-> population-for-lookup
                                                               (get [region (min year population-max-year)]) ;; use population-max-year to avoid an out-of-bounds
                                                               first
                                                               :population))

                       ;; Calculate the per citizen values
                       derivation-generation (map (fn [{:keys [region year quarter tonnes]}] {:region  region
                                                                                              :year    year
                                                                                              :quarter quarter
                                                                                              :tonnes  (double (/ tonnes (lookup-population region year)))})
                                                  derivation-generation0)
                       derivation-composition (map (fn [{:keys [region year quarter material tonnes]}] {:region   region
                                                                                                        :year     year
                                                                                                        :quarter  quarter
                                                                                                        :material material
                                                                                                        :tonnes   (double (/ tonnes (lookup-population region year)))})
                                                   derivation-composition0)]

                      (reset! state/stirling-bin-collection-derivation-generation-cursor derivation-generation)
                      (reset! state/stirling-bin-collection-derivation-composition-cursor derivation-composition)
                      (reset! state/stirling-bin-collection-derivation-percent-recycled-cursor derivation-percent-recycled)
                      (reset! state/stirling-bin-collection-derivation-missed-bins-cursor derivation-missed)
                      (js/console.log (str "Calculating stirling-bin-collection-derivations: secs-taken=" (util/secs-to-now start-time)))))))

;; -------------------

;; Watch for data updates

(add-watch state/population-holder :household-waste-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-derivations))))

(add-watch state/population-holder :household-co2e-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-derivations))))

(add-watch state/population-holder :stirling-bin-collection-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-stirling-bin-collection-derivations))))

(add-watch state/household-waste-holder :household-waste-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-derivations))))

(add-watch state/household-co2e-holder :household-co2e-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-derivations))))

(add-watch state/business-waste-by-region-holder :business-waste-by-region-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-business-waste-by-region-derivations))))

(add-watch state/waste-site-holder :waste-site-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-waste-site-derivations))))

(add-watch state/stirling-bin-collection-holder :stirling-bin-collection-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-stirling-bin-collection-derivations))))


