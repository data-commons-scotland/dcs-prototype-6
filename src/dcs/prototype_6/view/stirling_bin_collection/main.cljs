(ns dcs.prototype-6.view.stirling-bin-collection.main
  (:require [reagent.core :as r]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.view.stirling-bin-collection.derivation-generation :as derivation-generation]
            [dcs.prototype-6.view.stirling-bin-collection.derivation-composition :as derivation-composition]
            [dcs.prototype-6.view.stirling-bin-collection.derivation-percent-recycled :as derivation-percent-recycled]
            [dcs.prototype-6.view.stirling-bin-collection.derivation-missed :as derivation-missed]))

(defn root []
      [:section.section
       [:div.content.has-text-centered
        [:h1.title.is-5 "Stirling's bin collection"]]

       [derivation-generation/root]
       [derivation-composition/root]
       [derivation-percent-recycled/root]
       [derivation-missed/root]])

