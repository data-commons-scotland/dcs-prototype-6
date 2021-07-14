(ns dcs.prototype-6.main
  (:require [reagent.dom :as rdom]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.deriver :as deriver] ; execute add-watch calls 
            [dcs.prototype-6.loader :as loader]
            [dcs.prototype-6.router :as router]
            [dcs.prototype-6.navbar :as navbar]
            [dcs.prototype-6.status :as status]))

(defn page
      []
      [:div
       [navbar/root]
       [status/root]
       (let [route @state/route-match-cursor
             view (-> route :data :view)]
            (js/console.log "page route=" route)
            [view route])
       [:footer.footer
        [:p "Built by the " [:strong "Data Commons Scotland"] " project."]]])

;; called by init and after code reloading finishes
(defn ^:dev/after-load start
      []
      (js/console.log "Starting router")
      (router/init)
      (js/console.log "Starting render")
      (rdom/render [page] (.getElementById js/document "app")))

(defn ^:export init
      []
      ;; init is called ONCE when the page loads
      ;; this is called in the index.html and must be exported
      ;; so it is available even in :advanced release builds
      (loader/load-data)
      (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop
      []
      (js/console.log "stop"))