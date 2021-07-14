(ns dcs.prototype-6.util
  (:require [clojure.string :as str]))

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
  ;; hide Vega's 3-dot menu
  {:actions false}
  ;; alternatively, to display Vega's 3-dot menu, uncomment...
  #_{:actions          {:export   true
                        :source   true
                        :compiled false
                        :editor   true}
     :downloadFileName "WasteMattersScotland-visualisation"
     :scaleFactor      2})

;; For dcs-easier-open-data URLs I want to use the stem
;;   https://github.com/data-commons-scotland/dcs-easier-open-data/raw/v1.0-beta/data/
;; but it results in a "redirect"+"cross site restriction" problem.
;; So, instead use the "more direct" stem:
(def easier-repo "https://raw.githubusercontent.com/data-commons-scotland/dcs-easier-open-data/v1.1-beta/")
(def easier-repo-data (str easier-repo "data/"))
(def easier-repo-metadata (str easier-repo "metadata/"))


(defn open-tab [event tab-group-suffix tab-id]
      (doseq [tab-content (.getElementsByClassName js/document (str "tab-content-" tab-group-suffix))]
             (set! (.-display (.-style tab-content)) "none"))
      (doseq [tab (.getElementsByClassName js/document (str "tab-" tab-group-suffix))]
             (set! (.-className tab) (str/replace (.-className tab) " is-active" "")))
      (set! (.-display (.-style (.getElementById js/document (str tab-id "-" tab-group-suffix)))) "block")
      (set! (.-className (.-currentTarget event)) (str (.-className (.-currentTarget event)) " is-active")))