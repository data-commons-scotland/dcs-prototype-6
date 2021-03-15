(ns dcs.prototype-6.ui-map
  (:require [reagent.core :as r]))

(def dom-id "map-ui")
(def init-lat 58.30)
(def init-lng -3.70)
(def init-zoom 6)
(def basemap-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
(def basemap-maxzoom 18)
(def basemap-attribution "TODO")

(defn did-mount []
      (let [component (.map js/L dom-id)
            tile (.tileLayer js/L basemap-url
                             #js{:maxZoom     basemap-maxzoom
                                 :attribution basemap-attribution})]
           (do
             (.setView component (array init-lat init-lng) init-zoom)
             (.addTo tile component))))

(defn did-update [this prev-props]
      (println "todo"))

(defn render []
      [:div#map-ui.map-container])

(defn create []
      (r/create-class {:reagent-render       render
                       :component-did-mount  did-mount
                       :component-did-update did-update}))

