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

(def component-holder (r/atom nil))
(def geojson-layer-holder (r/atom nil))

(defn style [feature]
      #js{
          ;; :fillColor getColor(feature.properties.density)
          :weight       1
          :opacity      0.7
          :color        "gray"
          "fillOpacity" 0.2})

(defn highlight-feature [e]
      (let [^js x (.. e -target)
            properties-map (js->clj (.. x -feature -properties))
            region (get properties-map "LAD13NM")]
           (do
             (.setStyle x #js{:weight       2
                              :color        "#666"
                              "dashArray"   ""
                              "fillOpacity" 0.4})
             (.bringToFront x)
             (js/console.log (str "entered " region)))))

(defn reset-highlight [e]
      (let [x (.. e -target)
            geojson-layer @geojson-layer-holder]
           (do
             (.resetStyle geojson-layer x))))

(defn zoom-to-feature [e]
      (let [^js x (.. e -target)
            component @component-holder

            properties-map (js->clj (.. x -feature -properties))
            region (get properties-map "LAD13NM")]
           (do
             (.fitBounds component (.getBounds x))
             (.setStyle x #js{:weight       1
                              :color        "blue"
                              "dashArray"   ""
                              "fillOpacity" 0.2})
             (.bringToFront x)
             (reset! state/region-holder region)
             (js/console.log (str "selected " region)))))

(defn on-each-feature [feature layer]
      (.on layer #js{:mouseover highlight-feature
                     :mouseout  reset-highlight
                     :click     zoom-to-feature}))

(defn did-mount []
      (let [component (.map js/L dom-id)
            basemap-layer (.tileLayer js/L basemap-url
                                      #js{"maxZoom"    basemap-maxzoom
                                          :attribution basemap-attribution})]
           (do
             (reset! component-holder component)
             (.setView component (array init-lat init-lng) init-zoom)
             (.addTo basemap-layer component)
             )))

(defn did-update [this prev-props]
      (let [geojson (:data (r/props this))
            geojson-layer (.geoJson js/L @state/geojson-holder
                                    #js{:style          style
                                        "onEachFeature" on-each-feature})
            component @component-holder]
           (do
             (reset! geojson-layer-holder geojson-layer)
             (.addTo geojson-layer component))))

(defn render []
      [:div#map-ui.map-container])

(defn component []
      (r/create-class {:reagent-render       render
                       :component-did-mount  did-mount
                       :component-did-update did-update}))

(defn create []
      [component {:data @state/geojson-holder}])

