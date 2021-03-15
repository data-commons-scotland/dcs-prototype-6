(ns dcs.prototype-6.browser
      (:require [reagent.core :as r]
                [dcs.prototype-6.views :as views]))


(def URL-OSM "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")

(defn create-map []
      (let [m (-> js/L
                  (.map "map-ui")
                  (.setView (array 56.12 -3.87) 7))       ;; Polmaise
            tile1 (-> js/L (.tileLayer URL-OSM
                                       #js{:maxZoom     18
                                           :attribution "OOGIS RL, OpenStreetMap &copy;"}))
            base (clj->js {"OpenStreetMap" tile1})
            ctrl (-> js/L (.control.layers base nil))]
           (.addTo tile1 m)
           (.addTo ctrl m)))


;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
      (js/console.log "start")
      (r/render-component [views/app]
                          (.getElementById js/document "app"))
      (create-map))

(defn ^:export init []
      ;; init is called ONCE when the page loads
      ;; this is called in the index.html and must be exported
      ;; so it is available even in :advanced release builds
      (js/console.log "init")
      (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
      (js/console.log "stop"))