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

