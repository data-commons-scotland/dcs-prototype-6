(ns dcs.prototype-6.browser
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.coercion.spec :as rss]
            [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.ui-nav :as ui-nav]
            [dcs.prototype-6.ui-root :as ui-root]))


(defn view0
      []
      [:div
       [:h1 "VIEW - HOME"]
       [:div.content.is-red [:p "Some text."]]
       [:ul
        [:li [:a.button.is-primary.is-small {:href (rfe/href ::dashboard-page)} "Dashboard page"]]
        [:li [:a {:href (rfe/href ::route1-page)} "Route1 page"]]]])

(defn view1
      []
      [:div
       [:h1 "VIEW - 1"]
       [:ul
        [:li [:a {:href (rfe/href ::home-page)} "Home page"]]
        [:li [:a {:href (rfe/href ::dashboard-page)} "Dashboard page"]]]])

(defn todo
      []
      [:div
       [:h1 "TODO"]])

(defn current-page
      []
      [:div
       [ui-nav/navbar]
       (let [view (:view (:data @state/route-match))]
            [view @state/route-match])
       [:footer.footer
        [:div.content.has-text-centered
         [:p "By the " [:strong "Data Commons Scotland"] " project."]]]])

(def routes
  [["/"
    {:name ::home-page
     :view   view0}]
   ["/dashboard"
    {:name ::dashboard-page
     ;;:parameters {:path {:region string?}}
     :view   ui-root/app}]
   ["/route1"
    {:name ::route1-page
     :view   view1}]
   ["/todo"
    {:name ::todo-page
     :view   todo}]])

;; called by init and after code reloading finishes
(defn ^:dev/after-load start
      []
      (js/console.log "Starting router")
      (rfe/start!
        (rf/router routes {:data {:coercion rss/coercion}})
        (fn [m] (reset! state/route-match m))
        {:use-fragment false})                              ;; So normal looking URLs
      (js/console.log "Starting render")
      (r/render [current-page] (.getElementById js/document "app")))

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