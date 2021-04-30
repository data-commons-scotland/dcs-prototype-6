(ns dcs.prototype-6.view.experiment.main
  (:require [dcs.prototype-6.state :as state]
            #_[dcs.prototype-6.view.experiment.map :as map]))

(defn root []
      [:section.section
        [:div.content.has-text-centered
         [:h1.title.is-5 "Experiment"]]
       #_[map/root]])

