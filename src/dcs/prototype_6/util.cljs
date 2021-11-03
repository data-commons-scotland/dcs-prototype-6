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
  #_{:actions false}
  ;; alternatively, to display Vega's 3-dot menu, uncomment...
  {:actions          {:export   true
                      :source   false
                      :compiled false
                      :editor   false}
   :downloadFileName "WasteMattersScotland-visualisation"
   :scaleFactor      2
   ;; experiment to access and use Vega's View API, requires metasoarous/oz 1.6.0-alpha35-SNAPSHOT (or later)
   #_:view-callback #_(fn [view]
                    (js/console.log "executing view-callback option to oz/vega-lite component")
                    (js/console.log "view object" view)
                    (.addEventListener view "mouseover" (fn [event item]
                                                          (js/console.log "item: " item)
                                                          (when (some-> item .-datum .-annotation)
                                                            (js/console.log "It's an annotation")
                                                            (let [datum (.-datum item)]
                                                              (js/console.log "datum: " datum))
                                                            (.preventDefault event) ;; hmmm, does this do anything?
                                                            ))))
   })

;; For dcs-easier-open-data URLs I want to use the stem
;;   https://github.com/data-commons-scotland/dcs-easier-open-data/raw/v1.0-beta/data/
;; but it results in a "redirect"+"cross site restriction" problem.
;; So, instead use the "more direct" stem:
(def easier-repo "https://raw.githubusercontent.com/data-commons-scotland/dcs-easier-open-data/v1.5-beta/")
(def easier-repo-data (str easier-repo "data/"))


(defn open-tab [event tab-group-suffix tab-id]
      (doseq [tab-content (.getElementsByClassName js/document (str "tab-content-" tab-group-suffix))]
             (set! (.-display (.-style tab-content)) "none"))
      (doseq [tab (.getElementsByClassName js/document (str "tab-" tab-group-suffix))]
             (set! (.-className tab) (str/replace (.-className tab) " is-active" "")))
      (set! (.-display (.-style (.getElementById js/document (str tab-id "-" tab-group-suffix)))) "block")
      (set! (.-className (.-currentTarget event)) (str (.-className (.-currentTarget event)) " is-active")))


(defn scroll-fn
  ([id]
   (fn []
     (when-let [ele (.getElementById js/document id)]
       (.scrollIntoView ele true)
       (.add (.-classList ele) "is-selected")
      ;; a hacky way of de-selecting
       (letfn [(mousedown-handler [_e]
                 (.remove (.-classList ele) "is-selected")
                 (.removeEventListener js/document "mousedown" mousedown-handler))]
         (.addEventListener js/document "mousedown" mousedown-handler))
      ;; allow for the navbar area when scrolling-into-view a specific element
       (when-let [scrolledY (.-scrollY js/window)]
         (.scroll js/window 0 (- scrolledY 65))))))
  ([]
   (fn []
     (.scroll js/window 0 0))))

