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


(defn find-indexes [coll pred]
  (->> coll
       (keep-indexed #(if (pred %2) %1 nil))
       (remove nil?)))


;; 👋  ✋  ❗  ⁉️  🟢  ℹ️  🔍  📍 📈 📉 📊  ✅  ❌  👎  👍  🤔 🤨 🚩 💬  
(def annotation-symbol "👋")

(defn add-annotation [data-vector {:keys [id text pred]}]
  (let [indexes (find-indexes data-vector pred)]
    (loop [indexes-todo indexes
           data-vector-updated data-vector]
      (if (empty? indexes-todo)
        data-vector-updated
        (recur (rest indexes-todo)
               (let [ix (first indexes-todo)]
                 (-> data-vector-updated
                   
                   (assoc-in [ix :annotation] (str annotation-symbol id)) 
                   (assoc-in [ix :annotation-text] text)
                   (assoc-in [ix :annotation-url] "#") ;;TODO maybe replace with View API click event listener
                   )))))))


(defn add-annotations
  ([data-vector]
   (loop [annotations-todo anno-data/ANNOTATIONS
          data-vector-with-annotations data-vector]
     (if (empty? annotations-todo)
       data-vector-with-annotations
       (recur (rest annotations-todo)
              (add-annotation data-vector-with-annotations (first annotations-todo))))))
  ([data-coll record-type]
   (add-annotations (vec (map #(assoc % :record-type record-type) data-coll)))))


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