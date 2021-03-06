(ns dcs.prototype-6.router
  (:require [reitit.frontend :as rf]
            [reitit.coercion.spec :as rss]
            [reitit.frontend.easy :as rfe]
            [spec-tools.data-spec :as ds]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.home :as home-view]
            [dcs.prototype-6.view.regional-dashboard.main :as regional-dashboard-view]
            [dcs.prototype-6.view.stirling-bin-collection.main :as stirling-bin-collection-view]
            [dcs.prototype-6.view.stirling-community-food.main :as stirling-community-food-view]
            [dcs.prototype-6.view.fairshare.main :as fairshare-view]
            [dcs.prototype-6.view.ace-furniture.main :as ace-furniture-view]
            [dcs.prototype-6.view.household-waste-analysis.main :as household-waste-analysis-view]
            [dcs.prototype-6.view.experiment.main :as experiment-view]
            [dcs.prototype-6.view.todo :as todo-view]
            [dcs.prototype-6.view.data :as data-view]
            [dcs.prototype-6.view.about :as about-view]
            [dcs.prototype-6.view.waste-sites-map :as waste-sites-map-view]
            [dcs.prototype-6.view.waste-through-time-map :as waste-through-time-map-view]
            [dcs.prototype-6.view.pivot-drilldown-and-plot :as pivot-drilldown-and-plot-view]
            [dcs.prototype-6.view.guide.easier-open-data.main :as easier-open-data-view]
            [dcs.prototype-6.view.guide.regional-dashboard.main :as regional-dashboard-tutorial-view]
            [dcs.prototype-6.view.guide.exploring-business-waste.main :as exploring-business-waste-view]
            [dcs.prototype-6.view.guide.household-vs-business-waste.main :as household-vs-business-waste-view]))

(def routes
  [["/"
    {:name ::home-view
     :view home-view/root}]
   ["/todo"
    {:name ::todo-view
     :view todo-view/root}]
   ["/regional-dashboard"
    {:name ::dashboard-view
     :view regional-dashboard-view/root
     :parameters {:query {(ds/opt :region) string?}}}]
   ["/stirling-bin-collection"
    {:name ::stirling-bin-collection-view
     :view stirling-bin-collection-view/root}]
   ["/stirling-community-food"
    {:name ::stirling-community-food-view
     :view stirling-community-food-view/root}]
   ["/fairshare"
    {:name ::fairshare-view
     :view fairshare-view/root}]
   ["/ace-furniture"
    {:name ::ace-furniture-view
     :view ace-furniture-view/root}]
   ["/household-waste-analysis"
    {:name ::household-waste-analysis-view
     :view household-waste-analysis-view/root}]
   ["/waste-sites-map"
    {:name ::waste-sites-map-view
     :view waste-sites-map-view/root}]
   ["/waste-through-time-map/:preset"
    {:name ::waste-through-time-map-view
     :view waste-through-time-map-view/root
     :parameters {:path {:preset string?}}}]
   ["/pivot-drilldown-and-plot/:preset"
    {:name ::pivot-drilldown-and-plot-view
     :view pivot-drilldown-and-plot-view/root
     :parameters {:path {:preset string?}}}]
   ["/guide/easier-open-data"
    {:name ::easier-open-data-view
     :view easier-open-data-view/root
     :parameters {:query {(ds/opt :target) string?}}}]
   ["/guide/exploring-business-waste"
    {:name ::exploring-business-waste-view
     :view exploring-business-waste-view/root
     :parameters {:query {(ds/opt :target) string?}}}]
   ["/guide/household-vs-business-waste"
    {:name ::household-vs-business-waste-view
     :view household-vs-business-waste-view/root
     :parameters {:query {(ds/opt :target) string?}}}]
   ["/guide/regional-dashboard"
    {:name ::regional-dashboard-tutorial-view
     :view regional-dashboard-tutorial-view/root}]
   ["/data"
    {:name ::data-view
     :view data-view/root
     :parameters {:query {(ds/opt :target) string?}}}]
   ["/about"
    {:name ::about-view
     :view about-view/root}]
   ["/x"
    {:name ::experiment-view
     :view experiment-view/root}]
   ["*path"
    {:name ::catch-all
     :view home-view/root}]])

(defn init
      []
      (rfe/start!
        (rf/router routes
                   {:data      {:coercion rss/coercion}
                    :conflicts nil})
        (fn [m] (reset! state/route-match-cursor m))
        {:use-fragment true})) ;; So URLs looking like  base-path/#/other/paths
