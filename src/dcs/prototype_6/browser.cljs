(ns dcs.prototype-6.browser
      (:require [reagent.core :as r]
                [dcs.prototype-6.ui-root :as ui-root]))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
      (js/console.log "start")
      (r/render [ui-root/app] (.getElementById js/document "app")))

(defn ^:export init []
      ;; init is called ONCE when the page loads
      ;; this is called in the index.html and must be exported
      ;; so it is available even in :advanced release builds
      (js/console.log "init")
      (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
      (js/console.log "stop"))