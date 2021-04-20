(ns dcs.prototype-6.state
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [cljs.pprint :as pp]
            [kixi.stats.core :as stats]
            [dcs.prototype-6.data-shaping :as data-shaping])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (r/atom {:count 0}))

(defonce route-match (r/atom nil))

(defonce region-holder (r/atom "Please select a region..."))

(defonce geojson-holder (r/atom nil))

(defonce household-waste-derivation-generation-holder (r/atom nil))
(defonce household-waste-derivation-percent-recycled-holder (r/atom nil))
(defonce household-waste-derivation-management-holder (r/atom nil))
(defonce household-waste-derivation-composition-holder (r/atom nil))
(defonce household-waste-derivation-generation-positions-holder (r/atom nil))
(defonce household-waste-derivation-percent-recycled-positions-holder (r/atom nil))

(defonce household-co2e-derivation-generation-holder (r/atom nil))
(defonce household-co2e-derivation-generation-positions-holder (r/atom nil))

(defonce business-waste-by-region-derivation-generation-holder (r/atom nil))
(defonce business-waste-by-region-derivation-composition-holder (r/atom nil))

(defonce waste-site-derivation-holder (r/atom nil))

(defonce stirling-bin-collection-derivation-missed-holder (r/atom nil))

;; -----------------

(defonce population-holder (atom nil))
(defonce household-waste-holder (atom nil))
(defonce household-co2e-holder (atom nil))
(defonce business-waste-by-region-holder (atom nil))
(defonce waste-site-holder (atom nil))
(defonce stirling-bin-collection-holder (atom nil))

;; -----------------

;; Fetch data into holders

(defn now
  "Milliseconds since epoch"
  []
  (.now js/Date))

(defn secs-to-now
      [start-time]
      (double (/ (- (now) start-time) 1000)))

(defn fetch
      [url body-handler]
      (js/console.log (str "Fetching " url))
      (go (let [start-time (now)
                response (<! (http/get url))]
               (do
                 (js/console.log (str "Response from " url ": status=" (:status response) " success=" (:success response) " secs-taken=" (secs-to-now start-time)))
                 (body-handler (:body response))))))

(defn load-data
      []
      (js/console.log "Loading data files")

      (fetch "geojson.json"
             (fn [geojson] (->> geojson
                                clj->js
                                (reset! geojson-holder))))

      (fetch "population.json"
             (fn [population] (->> population
                                   data-shaping/rollup-population-regions
                                   (concat population)
                                   (reset! population-holder))))

      (fetch "household-waste.json"
             (fn [household-waste] (->> household-waste
                                        data-shaping/rollup-household-waste-regions
                                        (concat household-waste)
                                        (reset! household-waste-holder))))

      (fetch "household-co2e.json"
             (fn [household-co2e] (->> household-co2e
                                       data-shaping/rollup-household-co2e-regions
                                       (concat household-co2e)
                                       (reset! household-co2e-holder))))

      (fetch "business-waste-by-region.json"
             (fn [business-waste-by-region] (->> business-waste-by-region
                                                 data-shaping/rollup-business-waste-by-region-regions
                                                 (concat business-waste-by-region)
                                                 (reset! business-waste-by-region-holder))))

      (fetch "waste-site.json"
             (fn [waste-site] (->> waste-site
                                   (reset! waste-site-holder))))

      (fetch "stirling-bin-collection.json"
             (fn [stirling-bin-collection] (->> stirling-bin-collection
                                                (reset! stirling-bin-collection-holder)))))

;; ----------------------

;; Calc derived data

; Compute 'the trend of y'.
; (Returns the gradient of a linear approximation to the curve decribed by xy-pairs.)
(defn trend [xy-pairs]
      (let [rf (stats/simple-linear-regression first second)
            ^js jsobj (transduce identity rf xy-pairs)]
           (. jsobj -slope)))


(defn maybe-calc-household-waste-derivations []
      (let [household-waste @household-waste-holder
            population @population-holder]

           (when (and (some? household-waste)
                      (some? population))
                 (js/console.log "Calculating household-waste-derivations")

                 (let [start-time (now)

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

                      (reset! household-waste-derivation-generation-holder household-waste-derivation-generation)
                      (reset! household-waste-derivation-percent-recycled-holder household-waste-derivation-percent-recycled)
                      (reset! household-waste-derivation-management-holder household-waste-derivation-management)
                      (reset! household-waste-derivation-composition-holder household-waste-derivation-composition)
                      (reset! household-waste-derivation-generation-positions-holder household-waste-derivation-generation-positions)
                      (reset! household-waste-derivation-percent-recycled-positions-holder household-waste-derivation-percent-recycled-positions)
                      (js/console.log (str "Calculating household-waste-derivations: secs-taken=" (secs-to-now start-time)))))))


(defn maybe-calc-household-co2e-derivations []
      (let [household-co2e @household-co2e-holder
            population @population-holder]

           (when (and (some? household-co2e)
                      (some? population))
                 (js/console.log "Calculating household-co2e-derivations")

                 (let [start-time (now)

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

                      (reset! household-co2e-derivation-generation-holder household-co2e-derivation-generation)
                      (reset! household-co2e-derivation-generation-positions-holder household-co2e-derivation-generation-positions)
                      (js/console.log (str "Calculating household-co2e-derivations: secs-taken=" (secs-to-now start-time)))))))


(defn maybe-calc-business-waste-by-region-derivations []
      (let [business-waste-by-region @business-waste-by-region-holder]

           (when (some? business-waste-by-region))
           (js/console.log "Calculating business-waste-by-region-derivations")

           (let [start-time (now)

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

                (reset! business-waste-by-region-derivation-generation-holder business-waste-by-region-derivation-generation)
                (reset! business-waste-by-region-derivation-composition-holder business-waste-by-region-derivation-composition)
                (js/console.log (str "Calculating business-waste-by-region-derivations: secs-taken=" (secs-to-now start-time))))))


(defn maybe-calc-waste-site-derivations []
      (let [waste-site @waste-site-holder]

           (when (some? waste-site))
           (js/console.log "Calculating waste-site-derivations")

           (let [start-time (now)

                 waste-site-derivation (data-shaping/count-waste-sites-per-category-per-region waste-site)]

                (reset! waste-site-derivation-holder waste-site-derivation)
                (js/console.log (str "Calculating waste-site-derivations: secs-taken=" (secs-to-now start-time))))))


(defn maybe-calc-stirling-bin-collection-derivations []
      (let [stirling-bin-collection @stirling-bin-collection-holder]

           (when (some? stirling-bin-collection))
           (js/console.log "Calculating stirling-bin-collection-derivations")

           (let [start-time (now)

                 ;; Filter for missed-bin? then roll-up to get values for (year, quarter) pairs
                 stirling-bin-collection-derivation-missed (->> stirling-bin-collection
                                                                (filter :missed-bin?)
                                                                (group-by (juxt :year :quarter))
                                                                (map (fn [[[year quarter] coll]] {:year year
                                                                                                  :quarter quarter
                                                                                                  :tonnes (->> coll
                                                                                                               (map :tonnes)
                                                                                                               (apply +))})))]

                (reset! stirling-bin-collection-derivation-missed-holder stirling-bin-collection-derivation-missed)
                (js/console.log (str "Calculating stirling-bin-collection-derivations: secs-taken=" (secs-to-now start-time))))))

;; -------------------

;; Watch for data updates

(add-watch population-holder :household-waste-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-derivations))))

(add-watch population-holder :household-co2e-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-derivations))))

(add-watch household-waste-holder :household-waste-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-waste-derivations))))

(add-watch household-co2e-holder :household-co2e-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-household-co2e-derivations))))

(add-watch business-waste-by-region-holder :business-waste-by-region-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-business-waste-by-region-derivations))))

(add-watch waste-site-holder :waste-site-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-waste-site-derivations))))

(add-watch stirling-bin-collection-holder :stirling-bin-collection-derivations-dependency
           (fn [_key _atom old-state new-state]
               (when new-state
                     (maybe-calc-stirling-bin-collection-derivations))))


