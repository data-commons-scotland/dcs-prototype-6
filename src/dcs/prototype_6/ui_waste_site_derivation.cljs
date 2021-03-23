(ns dcs.prototype-6.ui-waste-site-derivation
  (:require [clojure.string :as str]
            [dcs.prototype-6.state :as state]))


(defn ele [region
           waste-site-derivation]

      (let [m (->> waste-site-derivation
                   (filter #(= region (:region %)))
                   first)]

           [:div
            [:table.positions
             [:tbody
              [:tr
               [:td "Sites accepting household waste"]
               [:td.position (:household m)]]
              [:tr
               [:td "Other waste sites"]
               [:td.position (:non-household m)]]]]]))


(defn create []
      [ele
       @state/region-holder
       @state/waste-site-derivation-holder])