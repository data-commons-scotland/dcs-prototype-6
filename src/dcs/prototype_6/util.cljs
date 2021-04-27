(ns dcs.prototype-6.util)

(defn now
      "Milliseconds since epoch"
      []
      (.now js/Date))

(defn secs-to-now
      [start-time]
      (double (/ (- (now) start-time) 1000)))

(defn date-str
      [year quarter]
      (let [q-map {1 "31 Mar"
                   2 "30 Jun"
                   3 "31 Aug"
                   4 "31 Dec"}]
      (str (q-map quarter) " " year)))