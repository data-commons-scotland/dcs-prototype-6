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

(def vega-embed-opts
      ;; alternatively, to hide Vega's 3-dot menu, use: {:actions false}
      {:actions {:export true
                 :source true
                 :compiled false
                 :editor true}
       :downloadFileName "WasteMattersScotland-visualisation"
       :scaleFactor 2})

;; For dcs-easier-open-data URLs I want to use the stem
;;   https://github.com/data-commons-scotland/dcs-easier-open-data/raw/v1.0-beta/data/
;; but it results in a "redirect"+"cross site restriction" problem.
;; So, instead use the "more direct" stem:
(def easier-url-stem "https://raw.githubusercontent.com/data-commons-scotland/dcs-easier-open-data/v1.0-beta/data/")