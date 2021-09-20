(ns dcs.prototype-6.view.regional-dashboard.map
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]))

(def dom-id "map-ui")
(def init-lat 58.30)
(def init-lng -3.70)
(def init-zoom 6)
(def basemap-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
(def basemap-maxzoom 18)
(def basemap-attribution "<a href='/#/data'>Attributions</a>")

(def component-holder (r/atom nil))
(def geojson-layer-holder (r/atom nil))


(def x-for-region-holder (atom nil)) ;; A hack. Replace the nedd for this with a function that find this in the geojson-layer using the name of the region.

(def style-neutral #js{;; :fillColor getColor(feature.properties.density)
                       :weight       1
                       :opacity      0.7
                       :color        "gray"
                       "fillOpacity" 0.2})

(def style-selected #js{:weight       1
                        :color        "#fdae6b"
                        "dashArray"   ""
                        "fillOpacity" 0.2})

(def style-highlighted #js{:weight       2
                           :color        "#666"
                           "dashArray"   ""
                           "fillOpacity" 0.4})

(defn style [^js feature]
      (let [properties-map (js->clj (.. feature -properties))
            region (get properties-map "LAD13NM")]
             #_(js/console.log (str "styling " region))
             (if (and (some? @state/region-cursor)
                      (= @state/region-cursor region))
               style-selected
               style-neutral)))

(defn highlight-feature [e]
      (let [^js x (.. e -target)
            ;properties-map (js->clj (.. x -feature -properties))
            ;region (get properties-map "LAD13NM")
            ]
             (.openTooltip x)
             (.setStyle x style-highlighted)
             (.bringToFront x)
             #_(js/console.log (str "entered " region))))

(defn reset-highlight [e]
      (let [x (.. e -target)
            geojson-layer @geojson-layer-holder]
             (.closeTooltip x)
             (.resetStyle geojson-layer x)))

(defn style-neutral-the-previously-selected []
      (when-let [x-for-region @x-for-region-holder]
                (.setStyle x-for-region style-neutral)))

(defn zoom-to-feature [e]
      (let [^js x (.. e -target)
            ;component @component-holder
            properties-map (js->clj (.. x -feature -properties))
            region (get properties-map "LAD13NM")]
             (when (not= @state/region-cursor region)
                     (style-neutral-the-previously-selected) ;; Hack! There will be a more elegant way to achieve this
                     ;(.fitBounds component (.getBounds x))
                     (.setStyle x style-selected)
                     (reset! x-for-region-holder x)
                     (reset! state/region-cursor region)
                     (set! (.-location js/window) (rfe/href :dcs.prototype-6.router/dashboard-view nil {:region region})))
             #_(js/console.log (str "selected " region))))

(defn on-each-feature [^js feature layer]
      (let [properties-map (js->clj (.. feature -properties))
            region (get properties-map "LAD13NM")]
             (.bindTooltip layer region)
             (.on layer #js{:mouseover highlight-feature
                            :mouseout  reset-highlight
                            :click     zoom-to-feature})))

(defn did-mount []
      (let [component (.map js/L dom-id)
            basemap-layer (.tileLayer js/L basemap-url
                                      #js{"maxZoom"    basemap-maxzoom
                                          :attribution basemap-attribution})]
             (reset! component-holder component)
             (.setView component (array init-lat init-lng) init-zoom)
             (.addTo basemap-layer component)

             ;; If we have the geojson already then add it to the map
             (when-let [_geojson @state/geojson-cursor]
                       (let [geojson-layer (.geoJson js/L @state/geojson-cursor
                                                     #js{:style          style
                                                         "onEachFeature" on-each-feature})
                             component @component-holder]
                              (reset! geojson-layer-holder geojson-layer)
                              (.addTo geojson-layer component)))
             ))

(defn did-update [_this _prev-props]
      (let [;geojson (:data (r/props this))
            geojson-layer (.geoJson js/L @state/geojson-cursor
                                    #js{:style          style
                                        "onEachFeature" on-each-feature})
            component @component-holder]
             (reset! geojson-layer-holder geojson-layer)
             (.addTo geojson-layer component)))

(defn render []
      [:div#map-ui])

(defn component []
      (r/create-class {:reagent-render       render
                       :component-did-mount  did-mount
                       :component-did-update did-update}))

(defn root []
      [component {:data @state/geojson-cursor}])

