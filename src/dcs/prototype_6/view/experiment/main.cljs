(ns dcs.prototype-6.view.experiment.main
  (:require [dcs.prototype-6.state :as state]
    #_[dcs.prototype-6.view.experiment.map :as map]))

(defn root []
      [:section.section
       [:div.content.has-text-centered
        [:h1.title.is-5 "Experiment"]
        [:p
         [:span.icon.is-small.has-tooltip-bottom.has-tooltip-multiline.has-tooltip-link.has-text-warning
          {:data-tooltip "Humpty-dumpty sat on a wall, Humpty-dumpty had a great fall...."}
          [:i.fas.fa-info]]]
        #_[map/root]]])

