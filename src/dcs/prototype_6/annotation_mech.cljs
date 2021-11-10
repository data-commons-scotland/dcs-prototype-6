(ns dcs.prototype-6.annotation-mech)


(def layer-annotations {:transform [{:filter "datum.annotation != null"}]
                        :mark {:type "text"
                               :align "left"
                               ;; :dx 0
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
                                   :tooltip [{:field "annotation-text" ;; tooltip's tabular format is nicer
                                              :type  "nominal"         
                                              :title ":"}]             ;; make the title fairly unnoticable
                                   ;;:href {:field "annotation-url" :type "nominal"}
                                   }})


;;
;; 👋  ✋  ❗  ⁉️  🟢  ℹ️  🔍  📍 📌 📈 📉 📊  ✅  ❌  👎  👍  🤔 🤨  😕  🚩 💬  🎯 🧭 🥇 🥈 🥉 ⚠️
;;
(def default-annotation-symbol "📍")


(defn record-matches? [criteria record]
  (every? true?
         (map (fn [[k v]]
                (= v
                   (k record))) 
              criteria)))

(def non-coord-keys [:emoji :text])

(defn apply-annotation [annotation target-coll]
  (let [criteria (apply dissoc annotation non-coord-keys)] ;; ignore anything that isn't contributing to the specification of datapoint coordinates 
    (map (fn [record]
           (if (record-matches? criteria record)
             (-> record
                 (assoc :annotation (get annotation :emoji default-annotation-symbol))
                 (assoc :annotation-text (str (get annotation :emoji default-annotation-symbol) " " (:text annotation)))
                 (assoc :annotation-url "#") ;;TODO maybe replace with View API click event listener
                 )
             record))
         target-coll)))


(defn apply-annotations
  ([annotations target-coll]
   (loop [annotations-todo    annotations
          target-coll-updated target-coll]
     (if (empty? annotations-todo)
       target-coll-updated
       (recur (rest annotations-todo)
              (apply-annotation (first annotations-todo) target-coll-updated)))))
  ([annotations target-coll record-type] 
   (apply-annotations annotations (->> target-coll
                                       (map #(assoc % :record-type record-type)) ;; decorate each data point with the given record-type
                                       ))))



(defn first-annotation-whose-record-criteria-matches [annotations record]
  (->> annotations
       (filter (fn [annotation]
                 (record-matches? (apply dissoc annotation non-coord-keys) record)))
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
        [:td.key "::"]
        [:td.value (str anchor " " text)]]]]]]])