(ns dcs.prototype-6.annotation)




(def annotations
  [
   ;; household-waste-analysis
   
   {:id   "1"
    :text "We can see that rural £/££ households dispose of a lot of fine-grained material, i.e. Fines (<10mm). 
                        Also, they dispose of a sizable portion of it inappropriately in recycling bins."
    :pred #(and (= "Fines (<10mm)" (:material-L2 %))
                (= "rural £/££" (:stratum %))
                (= "recycling bin" (:stream %)))}
   {:id   "2"
    :text "Urban £££ households dispose of a lot of Green garden waste inappropriately in comparison to their rural £££ peers. 
                        Is that because urban £££ households have fewer convenient spaces in which to heap their garden waste! 
                        Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."
    :pred #(and (= "Green garden waste" (:material-L2 %))
                (= "urban £££" (:stratum %))
                (= "grey bin" (:stream %)))}
   
   ;; regional-dashboard

   ;; Scot gov target

   {:id "3"
    :text "By 2025, the Scottish Government aims to reduce total waste arising in Scotland by 15% against 2011 levels."
    :pred #(and (= :household-waste-derivation-generation (:record-type %))
                (= "Scot gov target" (:region %))
                (= "2019" (:year %)))}
   
   {:id "4"
    :text "By 2025, the Scottish Government aims to recycle 70% of remaining waste."
    :pred #(and (= :household-waste-derivation-percent-recycled (:record-type %))
                (= "Scot gov target" (:region %))
                (= "2019" (:year %)))}

   ;; Stirling

   {:id "5"
    :text "There are some fluctuations in Stirling's business waste, including an interesting change in the Soils category which almost entirely disappears from 2015 onwards."
    :pred #(and (= "Stirling" (:region %))
                (= "2015" (:year %))
                (= "Soils" (:material %)))}
   
   ;; Fife

   {:id "6"
    :text "There is a big change in the amount of Combustion wastes between 2015 and 2017, 
           with this category going from the biggest contributor to business waste to one of the smallest. 
           Is this the result of the closure of Longannet power station in 2016?"
    :pred #(and (= "Fife" (:region %))
                (= "2016" (:year %))
                (= "Combustion wastes" (:material %)))}])




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
                                   :href {:field "annotation-url" :type "nominal"}}})


(defn find-indexes [coll pred]
  (->> coll
       (keep-indexed #(if (pred %2) %1 nil))
       (remove nil?)))


(defn add-annotation [data-vector {:keys [id text pred]}]
  (let [indexes (find-indexes data-vector pred)]
    (loop [indexes-todo indexes
           data-vector-updated data-vector]
      (if (empty? indexes-todo)
        data-vector-updated
        (recur (rest indexes-todo)
               (let [ix (first indexes-todo)]
                 (-> data-vector-updated
                   (assoc-in [ix :annotation] (str "👋" id)) ; 👋  ✋  ❗  ⁉️  🔴  ℹ️  🔍  📍 📈 📉 📊 
                   (assoc-in [ix :annotation-text] text)
                   (assoc-in [ix :annotation-url] "#/household-waste-analysis") ;;TODO maybe replace with View API click event listener
                   )))))))


(defn add-annotations
  ([data-vector]
   (loop [annotations-todo annotations
          data-vector-with-annotations data-vector]
     (if (empty? annotations-todo)
       data-vector-with-annotations
       (recur (rest annotations-todo)
              (add-annotation data-vector-with-annotations (first annotations-todo))))))
  ([data-coll record-type]
   (add-annotations (vec (map #(assoc % :record-type record-type) data-coll)))))
