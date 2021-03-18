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

