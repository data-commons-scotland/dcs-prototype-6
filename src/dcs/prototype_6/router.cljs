(ns dcs.prototype-6.router
  (:require [reitit.frontend :as rf]
            [reitit.coercion.spec :as rss]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.navbar :as navbar]
            [dcs.prototype-6.view.home :as home-view]
            [dcs.prototype-6.view.regional-dashboard.main :as regional-dashboard-view]
            [dcs.prototype-6.view.stirling-bin-collection.main :as stirling-bin-collection-view]
            [dcs.prototype-6.view.todo :as todo-view]))

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
   ["/stirling-bin-collection"
    {:name ::stirling-bin-collection-view
     :view stirling-bin-collection-view/root}]
   ["*path"
    {:name ::catch-all
     :view regional-dashboard-view/root}]])

(defn init
      []
      (js/console.log "Starting router")
      (rfe/start!
        (rf/router routes
                   {:data      {:coercion rss/coercion}
                    :conflicts nil})
        (fn [m] (reset! state/route-match m))
        {:use-fragment true})) ;; So URLs looking like  base-path/#/other/paths
