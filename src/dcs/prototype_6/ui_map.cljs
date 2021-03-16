(ns dcs.prototype-6.ui-map
  (:require [reagent.core :as r]
            [dcs.prototype-6.state :as state]))

(def dom-id "map-ui")
(def init-lat 58.30)
(def init-lng -3.70)
(def init-zoom 6)
(def basemap-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
(def basemap-maxzoom 18)
(def basemap-attribution "TODO")


(defn style [feature]
  #js{
   ;; :fillColor getColor(feature.properties.density)
   :weight 1
   :opacity 0.7
   :color "grey"
   :fill-opacity 0.2})

(defn did-mount []
      (let [component (.map js/L dom-id)
            basemap (.tileLayer js/L basemap-url
                             #js{:max-zoom     basemap-maxzoom
                                 :attribution basemap-attribution})
            regions (.geoJson js/L @state/geojson
                              #js{:style style
                                  #_:on-each-feature #_on-each-feature})]
           (do
             (.setView component (array init-lat init-lng) init-zoom)
             (.addTo basemap component)
             (.addTo regions component)
             )))

(defn did-update [this prev-props]
      (println "todo"))

(defn render []
      [:div#map-ui.map-container])

(defn create []
      (r/create-class {:reagent-render       render
                       :component-did-mount  did-mount
                       :component-did-update did-update}))

