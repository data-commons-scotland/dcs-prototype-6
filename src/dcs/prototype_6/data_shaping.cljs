(ns dcs.prototype-6.data-shaping)

;; Roll-up to get values for Scotland as a whole
(defn rollup-population-regions [population]
      (->> population
           (group-by :year)
           (map (fn [[year coll]] {:region     "Scotland"
                                   :year       year
                                   :population (->> coll
                                                    (map :population)
                                                    (apply +))}))))

;; Roll-up to get values for Scotland as a whole
(defn rollup-household-co2e-regions [household-co2e]
      (->> household-co2e
           (group-by :year)
           (map (fn [[year coll]] {:region "Scotland"
                                   :year   year
                                   :tonnes (->> coll
                                                (map :tonnes)
                                                (apply +))}))))

;; Roll-up to get values for Scotland as a whole
(defn rollup-household-waste-regions [household-waste]
      (->> household-waste
           (group-by (juxt :year :material :management))
           (map (fn [[[year material management] coll]] {:region     "Scotland"
                                                         :year       year
                                                         :material   material
                                                         :management management
                                                         :tonnes     (->> coll
                                                                          (map :tonnes)
                                                                          (apply +))}))))

;; Roll-up to get values for Scotland as a whole
(defn rollup-business-waste-by-region-regions [business-waste-by-region]
      (->> business-waste-by-region
           (group-by (juxt :year :material))
           (map (fn [[[year material] coll]] {:region   "Scotland"
                                              :year     year
                                              :material material
                                              :tonnes   (->> coll
                                                             (map :tonnes)
                                                             (apply +))}))))

;; Roll-up to get values for (region, year) pairs
(defn rollup-household-waste-materials-and-management [household-waste]
      (->> household-waste
           (group-by (juxt :region :year))
           (map (fn [[[region year] coll]] {:region region
                                            :year   year
                                            :tonnes (->> coll
                                                         (map :tonnes)
                                                         (apply +))}))))

;; Roll-up to get values for (region, year, management) triples
(defn rollup-household-waste-materials [household-waste]
      (->> household-waste
           (group-by (juxt :region :year :management))
           (map (fn [[[region year management] coll]] {:region     region
                                                       :year       year
                                                       :management management
                                                       :tonnes     (->> coll
                                                                        (map :tonnes)
                                                                        (apply +))}))))

;; Roll-up to get values for (region, year, material) triples
(defn rollup-household-waste-managements [household-waste]
      (->> household-waste
           (group-by (juxt :region :year :material))
           (map (fn [[[region year material] coll]] {:region     region
                                                       :year       year
                                                       :material   material
                                                       :tonnes     (->> coll
                                                                        (map :tonnes)
                                                                        (apply +))}))))

;; Calculate the percentage recycled values for (region, year) pairs
(defn calc-household-waste-percentage-recycled [household-waste]
      (->> household-waste
           (group-by (juxt :region :year))
           (map (fn [[[region year] coll]] {:region     region
                                            :year       year
                                            :percentage (let [total-tonnes (->> coll
                                                                                (map :tonnes)
                                                                                (apply +))
                                                              recycled-tonnes (->> coll
                                                                                   (filter #(= "Recycled" (:management %)))
                                                                                   (map :tonnes)
                                                                                   (apply +))]
                                                             (double (* 100 (/ recycled-tonnes total-tonnes))))}))))

;; Roll-up to get values for (region, year) pairs
(defn rollup-business-waste-by-region-materials [business-waste-by-region]
      (->> business-waste-by-region
           (group-by (juxt :region :year))
           (map (fn [[[region year] coll]] {:region region
                                            :year   year
                                            :tonnes (->> coll
                                                         (map :tonnes)
                                                         (apply +))}))))

;; Count the waste sites per category in each region
(defn count-waste-sites-per-category-per-region [waste-site]
      (->> waste-site
           (remove #(= "Not operational" (:status %)))
           (group-by :region)
           (map (fn [[region coll]] (let [all (count coll)
                                          household (->> coll
                                                         (filter #(contains? (set (:accepts %)) "Household"))
                                                         count)
                                          non-household (- all household)]
                                         {:region        region
                                          :household     household
                                          :non-household non-household})))))


;; Roll-up to get values for (region, year, quarter) triples
(defn rollup-stirling-bin-collection-ma-re-mi [stirling-bin-collection]
      (->> stirling-bin-collection
           (group-by (juxt :region :year :quarter))
           (map (fn [[[region year quarter] coll]] {:region  region
                                                    :year    year
                                                    :quarter quarter
                                                    :tonnes  (->> coll
                                                                  (map :tonnes)
                                                                  (apply +))}))))


;; Roll-up to get values for (region, year, quarter, material) quadruples
(defn rollup-stirling-bin-collection-re-mi [stirling-bin-collection]
      (->> stirling-bin-collection
           (group-by (juxt :region :year :quarter :material))
           (map (fn [[[region year quarter material] coll]] {:region   region
                                                             :year     year
                                                             :quarter  quarter
                                                             :material material
                                                             :tonnes   (->> coll
                                                                            (map :tonnes)
                                                                            (apply +))}))))


;; Calculate the percentage recycled values for (region, year, quarter) triples
(defn calc-stirling-bin-collection-percentage-recycled [stirling-bin-collection]
      (->> stirling-bin-collection
           (group-by (juxt :region :year :quarter))
           (map (fn [[[region year quarter] coll]] {:region     region
                                                    :year       year
                                                    :quarter    quarter
                                                    :percentage (let [total-tonnes (->> coll
                                                                                        (map :tonnes)
                                                                                        (apply +))
                                                                      recycled-tonnes (->> coll
                                                                                           (filter #(:recycling? %))
                                                                                           (map :tonnes)
                                                                                           (apply +))]
                                                                     (double (* 100 (/ recycled-tonnes total-tonnes))))}))))


;; Calculate the flow (paths and amounts) of food material through the Stirling Community Food system
(defn calc-stirling-community-food-flow [stirling-community-food-tonnes]
      (letfn [(sum-counter-party-tonnes [counter-party]
                                           (->> stirling-community-food-tonnes
                                                (filter #(and (= "in" (:io-direction %))
                                                              (= counter-party (:counter-party %))))
                                                (map :tonnes)
                                                (apply +)))

              (sum-subflows-tonnes [subflows]
                                      (->> subflows
                                           (map #(nth % 2))
                                           (apply +)))

              (sum-counter-parties-tonnes [counter-parties]
                                             (->> stirling-community-food-tonnes
                                                  (filter #(and (= "out" (:io-direction %))
                                                                (contains? counter-parties (:counter-party %))))
                                                  (map :tonnes)
                                                  (apply +)))]

             (let [source-keys (->> stirling-community-food-tonnes
                                    (filter #(= "in" (:io-direction %)))
                                    (map :counter-party)
                                    distinct)

                   not-waste-sources #{"Purchased" "Donated not waste"}
                   waste-sources (remove #(contains? not-waste-sources %) source-keys)

                   used-as-food-outcomes #{"Used as food"}
                   not-used-as-food-outcomes #{"Donated to animal sanctuary" "Used by individuals for compost" "Council compost, Energen biogas, etc."}

                   subflows-1a
                   (for [from waste-sources]
                        [from "Would-be waste" (sum-counter-party-tonnes from)])

                   subflows-1b
                   (for [from not-waste-sources]
                        [from "Not waste" (sum-counter-party-tonnes from)])

                   subflows-2
                   [["Not waste" "Stirling Community Food" (sum-subflows-tonnes subflows-1b)]
                    ["Would-be waste" "Stirling Community Food" (sum-subflows-tonnes subflows-1a)]]

                   subflows-3
                   [["Stirling Community Food" "Used as food" (sum-counter-parties-tonnes used-as-food-outcomes)]
                    ["Stirling Community Food" "Not used as food" (sum-counter-parties-tonnes not-used-as-food-outcomes)]]

                   subflows-4
                   (for [to not-used-as-food-outcomes]
                        ["Not used as food" to (sum-counter-parties-tonnes #{to})])

                   ;; concat and order them

                   ordered-froms ["Purchased"
                             "Donated not waste"
                             "Local supermarkets"
                             "Fareshare"
                             "Donated as waste"
                             "Other"
                             "Not waste"
                             "Would-be waste"
                             "Stirling Community Food"]

                   ordered-tos ["Used as food" ;; should be no need to worry about the earlier ones in the flow
                                "Not used as food"
                                "Donated to animal sanctuary"
                                "Used by individuals for compost"
                                "Council compost, Energen biogas, etc."]

                   comparator (fn [[a-from a-to] [b-from b-to]] (if (not= a-from b-from)
                                                      (< (.indexOf ordered-froms a-from) (.indexOf ordered-froms b-from))
                                                      (< (.indexOf ordered-tos a-to) (.indexOf ordered-tos b-to))))

                   flow (sort-by (juxt first second)
                                 comparator
                                 (concat subflows-1a subflows-1b subflows-2 subflows-3 subflows-4))]

                  flow)))



