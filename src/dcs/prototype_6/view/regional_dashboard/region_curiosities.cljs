(ns dcs.prototype-6.view.regional-dashboard.region-curiosities
  (:require [dcs.prototype-6.state :as state]))

;; Per-region (manually-curated) points-of-interest.
(def points-of-interest
 {"Inverclyde" [:div.content
                [:span.has-text-success "Inverclyde is doing well."]
                " In the latest data (2019), it generates the fewest tonnes of household waste (per citizen) of any of the council areas."
                " And its same 1st position for CO" [:sub "2"] "e indicates the close relation between the amount of waste generated and its carbon impact."
                [:span.has-text-grey " …​But why is Inverclyde doing so well?"]]})

(defn root []
  (get points-of-interest @state/region-cursor ""))