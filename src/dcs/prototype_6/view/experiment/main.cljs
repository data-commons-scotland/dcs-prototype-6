(ns dcs.prototype-6.view.experiment.main
  (:require [reagent.core :as r]
            [dcs.prototype-6.util :as util]
            #_[dcs.prototype-6.view.experiment.map :as map]
            [dcs.prototype-6.view.experiment.region-title :as region-title]))


(defn root []
  (r/after-render (util/scroll-fn))

  [:section.section
   [:div.content.has-text-centered
    [:h1.title.is-5 "Experiment"]
    [:p
     [:span.icon.is-small.has-tooltip-bottom.has-tooltip-multiline.has-tooltip-link.has-text-warning
      {:data-tooltip "Humpty-dumpty sat on a wall, Humpty-dumpty had a great fall...."}
      [:i.fas.fa-info]]]
    #_[map/root]]


   [:div.tabs
    [:ul
     [:li.tab-xyz.is-active {:on-click (fn [e] (util/open-tab e "xyz" "humpty"))} [:a "Humpty"]]
     [:li.tab-xyz {:on-click (fn [e] (util/open-tab e "xyz" "dumpty"))} [:a "Dumpty"]]
     [:li.tab-xyz {:on-click (fn [e] (util/open-tab e "xyz" "other"))} [:a "Other"]]]]

   [:div#humpty-xyz.tab-content-xyz
    [:p
     "Humpty" [:br]
     "Walls and sitting."]]
   [:div#dumpty-xyz.tab-content-xyz {:style {:display "none"}}
    [:p
     "Dumpty" [:br]
     "Falling and reconstruction."]]
   [:div#other-xyz.tab-content-xyz {:style {:display "none"}}
    [:p
     "Other" [:br]
     "Blah, blah, blah."]]

   [region-title/root]
   ])

