(ns dcs.prototype-6.annotation-mech
  (:require [dcs.prototype-6.annotation-data :as anno-data]))


(def layer-annotations {:transform [{:filter "datum.annotation != null"}]
                        :mark {:type "text"
                               :align "left"
                               :dx 10
                               ;; :dy -5
                               ;; _:baseline "bottom"
                               :fontWeight "bold"
                               :fontSize 14
                               :fill "blue"
                               :fillOpacity 1
                               :stroke "white"
                               :strokeOpacity 0
                               :strokeWidth 5}
                        :encoding {:text {:field "annotation"}
                                   :tooltip [{:field "annotation"
                                              :type  "nominal"
                                              :title "id"}
                                             {:field "annotation-text"
                                              :type  "nominal"
                                              :title "info"}]
                                   ;;:href {:field "annotation-url" :type "nominal"}
                                   }})



;; 👋  ✋  ❗  ⁉️  🟢  ℹ️  🔍  📍 📈 📉 📊  ✅  ❌  👎  👍  🤔 🤨 🚩 💬  
(def annotation-symbol "👋")


(defn datapoint-matches? [criteria datapoint]
  (every? true?
         (map (fn [[k v]]
                (= v
                   (k datapoint))) 
              criteria)))


(defn apply-annotation [data-coll [id {:keys [applications text]}]]
  (loop [applications-todo applications
         data-coll-updated data-coll]
    (if (empty? applications-todo)
      data-coll-updated
      (recur (rest applications-todo)
             (let [application        (first applications-todo)
                   datapoint-criteria (:datapoint application)
                     ;;:dx (:dx application)
                     ;;:dy (:dy application)
                   ]
               (map (fn [datapoint]
                      (if (datapoint-matches? datapoint-criteria datapoint)
                        (-> datapoint
                            (assoc :annotation (str annotation-symbol id))
                            (assoc :annotation-text text)
                            (assoc :annotation-url "#") ;;TODO maybe replace with View API click event listener
                            )
                        datapoint)) 
                    data-coll))))))



(defn apply-annotations
  ([data-coll]
   (loop [annotations-todo (map-indexed vector anno-data/ANNOTATIONS) ;; use an annotation's index as its ID
          data-coll-with-annotations data-coll]
     (if (empty? annotations-todo)
       data-coll-with-annotations
       (recur (rest annotations-todo)
              (apply-annotation data-coll-with-annotations (first annotations-todo))))))
  ([data-coll record-type] 
   (apply-annotations (->> data-coll
                           (map #(assoc % :record-type record-type)) ;; decorate each data point with the given record-type
                           ))))



(defn first-annotation-whose-datapoint-criteria-matches [datapoint]
  (->> anno-data/ANNOTATIONS
       (map-indexed vector)
       (filter (fn [[_ix annotation]]
                 (some true?
                       (map (fn [datapoint-criteria]
                              (datapoint-matches? datapoint-criteria datapoint))
                            (map :datapoint
                                 (:applications annotation))))))
       first))



(defn vega-like-tooltip [anchor text]
  [:span.dropdown.is-hoverable
   [:span.dropdown-trigger
    [:span.is-ghost [:b {:style {:color "blue" :font-size "small"}} anchor]]]
   [:span.dropdown-menu
    [:div#vg-tooltip-like
     [:table
      [:tbody
       [:tr
        [:td.key "id:"]
        [:td.value anchor]]
       [:tr
        [:td.key "info:"]
        [:td.value text]]]]]]])