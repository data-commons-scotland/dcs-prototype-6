(ns dcs.prototype-6.view.regional-dashboard.waste-site-derivation
  (:require [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]))


(defn ele [region
           waste-site-derivation]

      (let [m (->> waste-site-derivation
                   (filter #(= region (:region %)))
                   first)]

        [:div
         [:table#league-table.table.is-hoverable
          [:tbody
           [:tr
            [:td "Sites accepting household waste"]
            [:td (:household m)]]
           [:tr
            [:td "Other waste sites"]
            [:td (:non-household m)]]]]
         [:span.content.is-size-7.has-text-left.has-text-info "See also, the " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites tool"] "."]]))


(defn root []
      [ele
       @state/region-cursor
       @state/waste-site-derivation-cursor])