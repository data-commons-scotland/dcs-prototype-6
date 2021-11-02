(ns dcs.prototype-6.view.household-waste-analysis.detail
  (:require
   [oz.core :as oz]
   [goog.string :as gstring]
   [dcs.prototype-6.util :as util]
   [dcs.prototype-6.state :as state]))

(def stratum-labels ["urban £" "urban ££" "urban £££" "rural £/££" "rural £££"])

(def layer-normal {:mark "bar"
                 :encoding {:x {:field "kg" :type "quantitative" :scale {:type "sqrt"} :axis {:title "avg kg/hh/wk"}}
                            :y {:field "stream" :type "nominal" :axis {:title ""}}
                            :color {:field "material-L2" :type "nominal"
                                    :scale {:scheme "tableau20"}
                                    :legend nil}
                            :fillOpacity {:value 0.5}
                            :tooltip [{:field "stratum" :type "nominal" :title "house type (location & CTax)"}
                                      {:field "material-L1" :type "nominal" :title "material (high level)"}
                                      {:field "material-L2" :type "nominal" :title "material (in detail)"}
                                      {:field "stream" :type "nominal" :title "(actual) disposal"}
                                      {:field "idealStream" :type "nominal" :title "ideal disposal"}
                                      {:field "kg" :type "quantitative" :format ".4f" :title "kg per household per week"}]}})
(def layer-red-outlines (-> layer-normal
                            (assoc :transform [{:filter "(datum.kg > 0) && (datum.stream != datum.idealStream)"}])
                            (assoc-in [:encoding :fillOpacity :value] 0)
                            (assoc-in [:encoding :stroke :value] "red")
                            (assoc-in [:encoding :strokeWidth :value] 1)))
(def layer-annotations (-> layer-normal
                         (assoc :transform [{:filter "datum.annotation != null"}])
                         (assoc-in [:encoding :fillOpacity :value] 1)
                         (assoc :mark {:type "text" :align "left" :dx 10 #_:baseline #_"bottom" :fontWeight "bold" :fontSize 14})
                         (assoc-in [:encoding :text :field] "annotation")
                         (assoc-in [:encoding :tooltip #_6] {:field "annotation-text" :type "nominal"})
                         (assoc-in [:encoding :href] {:field "annotation-url" :type "nominal"})))

(defn chart-spec
      [chart-data title]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :background "floralwhite"
       :spacing 3
       :config {:title {:anchor "center"}}
       :title title
       :data       {:values chart-data}
       :transform  [{:aggregate [{:op "mean" :field "kgPerHhPerWk" :as "kg"}]
                     :groupby ["stratum" "material-L1" "material-L2" "stream" "idealStream" "annotation" "annotation-text" "annotation-url"]}]
       :facet     {:column {:field "stratum" :type "nominal"
                            :sort stratum-labels
                            :header {:title "household type (location type & council tax band)"}}
                   :row {:field "material-L2" :type "nominal"
                         :header {:title "material (in detail)" :labelAngle 0 :labelAlign "left"}
                         :sort {:field "kg" :op "max" :order "descending"}}}
       :spec {:width 130
              :height 20
              :layer [layer-normal
                      layer-red-outlines
                      layer-annotations]}})

(defn find-indexes [coll pred]
  (keep-indexed #(if (pred %2) %1 nil) coll))

(def annotations [{:text "We can see that rural £/££ households dispose of a lot of fine-grained material, i.e. Fines (<10mm). 
                        Also, they dispose of a sizable portion of it inappropriately in recycling bins."
                 :pred #(and (= "Fines (<10mm)" (:material-L2 %))
                             (= "rural £/££" (:stratum %))
                             (= "recycling bin" (:stream %)))}
                {:text "Urban £££ households dispose of a lot of Green garden waste inappropriately in comparison to their rural £££ peers. 
                        Is that because urban £££ households have fewer convenient spaces in which to heap their garden waste! 
                        Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."
                 :pred #(and (= "Green garden waste" (:material-L2 %)) 
                             (= "urban £££" (:stratum %)) 
                             (= "grey bin" (:stream %)))}])

(defn add-annotation [data-vector {:keys [text pred]}]
  (let [indexes (find-indexes data-vector pred)]
    (loop [indexes-todo indexes
           data-vector-updated data-vector]
      (if (empty? indexes-todo)
        data-vector-updated
        (recur (rest indexes-todo)
               (let [ix (first indexes-todo)]
                 (-> data-vector-updated
                   (assoc-in [ix :annotation] "ℹ️")
                   (assoc-in [ix :annotation-text] text)
                   (assoc-in [ix :annotation-url] "#/household-waste-analysis") ;;TODO maybe replace with View API click event listener
                   )))))))

(defn charts [derivation]
  (let [chart-data (loop [annotations-todo annotations
                          data-with-annotations derivation]
                     (if (empty? annotations-todo)
                       data-with-annotations
                       (recur (rest annotations-todo)
                              (add-annotation data-with-annotations (first annotations-todo)))))] 
    
    [:div.columns
     [:column
      [:div.m-4.content
       [:p "Graph 3 [below] conveys a fair amount of information!"]
       [:p.has-text-info "As before, hover/click on a bar in the graph to be shown more detailed information."
        [:br]
        " The bars with a " [:span.has-text-danger.has-text-weight-bold "red"] " outline represent " 
        [:em.has-text-danger "inappropriate disposal"] "."
        [:br]
        [:p "The categories are ordered by: heaviest " [:em "sub-breakdown"] " first."]]
       [:p "Again we can see [on the top row] that " [:span.has-text-warning "rural £/££"] " households dispose of a lot of fine-grained material,"
        " i.e." (gstring/unescapeEntities "&nbsp;") [:span.has-text-warning "Fines" (gstring/unescapeEntities "&nbsp;") "(<10mm)"] "."
        " Also, they dispose of a sizable portion of it " [:em.has-text-danger "inappropriately"] " in " [:span.has-text-warning "recycling bin"] "s."]
       [:p "For some categories (e.g. "
        [:span.has-text-warning "Unavoidable food waste"]
        ", " [:span.has-text-warning "Avoidable food waste"]
        " and " [:span.has-text-warning "Clear container glass"]
        ") " [:b "both"] " bars ("
        [:span.has-text-warning "grey bin"]
        " and " [:span.has-text-warning "recycling bin"]
        ") are outlined in " [:span.has-text-danger "red"] ". Why?"
        " ...Probably because the bins that are appropriate for those categories,"
        " were not covered by the dataset."
        " (E.g. because the appropriate bins are actually fixed containers at recyling points; or because they were collected at times outside of the sampling periods.)"
        " We will ask ZWS to confirm this interpretation."]
       [:p [:span.has-text-warning "urban £££"] " households dispose of a lot of " 
        [:span.has-text-warning "Green garden waste"] " " [:em.has-text-danger "inappropriately"] 
        " in comparison to their " [:span.has-text-warning "rural £££"] " peers [see the 18" [:sup "th"] " category down]."
        " Is that because " [:span.has-text-warning "urban £££"] " households have fewer convenient spaces in which to heap their garden waste!"
        " Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."]]
      
      [oz/vega-lite (chart-spec chart-data "Graph 3: Detailed disposal decisions per household type")
       util/vega-embed-opts]
      ]]))

(defn root []
      [charts
       @state/household-waste-analysis-derivation-cursor])
