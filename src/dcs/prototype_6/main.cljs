(ns dcs.prototype-6.main
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.coercion.spec :as rss]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.navbar :as navbar]
            [dcs.prototype-6.view.home :as home-view]
            [dcs.prototype-6.view.regional-dashboard.main :as regional-dashboard-view]
            [dcs.prototype-6.view.todo :as todo-view]))

(defn page
      []
      [:div
       [navbar/root]
       (let [route @state/route-match]
            (js/console.log "route=" route)
            (let [view (or (-> route :data :view)
                           home-view/root)]
                 [view @state/route-match]))
       [:footer.footer
        [:p "Built by the " [:strong "Data Commons Scotland"] " project."]]])

(def routes
  [["/"
    {:name ::home-view
     :view home-view/root}]
   ["/todo"
    {:name ::todo-view
     :view todo-view/root}]
   ["/regional-dashboard"
    {:name ::dashboard-view
     :view regional-dashboard-view/root}]
   ["*path"
    {:name ::catch-all
     :view regional-dashboard-view/root}]])

;; called by init and after code reloading finishes
(defn ^:dev/after-load start
      []
      (js/console.log "Starting router")
      (rfe/start!
        (rf/router routes
                   {:data      {:coercion rss/coercion}
                    :conflicts nil})
        (fn [m] (reset! state/route-match m))
        {:use-fragment true})                               ;; So URLs looking like  base-path/#/other/paths
      (js/console.log "Starting render")
      (r/render [page] (.getElementById js/document "app")))

(defn ^:export init
      []
      ;; init is called ONCE when the page loads
      ;; this is called in the index.html and must be exported
      ;; so it is available even in :advanced release builds
      (state/load-data)
      (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop
      []
      (js/console.log "stop"))