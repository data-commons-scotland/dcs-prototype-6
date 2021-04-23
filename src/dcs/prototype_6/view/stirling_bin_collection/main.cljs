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
        [:h1.title.is-5 "Stirling's bin collection of household waste"]]

       [:div.tile.is-ancestor

        [:div.tile.is-6
         [:div.tile.is-vertical.is-parent
          [:div.tile.is-child.box
            [:figure
             [derivation-generation/root]]
           [:h2.title.is-6.has-text-danger "Amount"]
           [:h2.subtitle.is-5 "How much material was in the bins?"]
            [:p
             "NB: Bin collection data for 2021 contains only Q1 data so remove 2021.
             Population data for 2020 and 2021 not available so using 2019 figures for those years.
             Is low compared to general so other amounts must have been been accounted for in general."]
            ]
           [:div.tile.is-child.box
            [:figure
             [derivation-composition/root]]
            [:h2.title.is-6.has-text-danger "Composition"]
            [:h2.subtitle.is-5 "What materials were in the bins?"]
            [:p
             "NB: did a coarse grained categorisation of the material in bins"]
            ]
          ]
         ]

        [:div.tile.is-6
         [:div.tile.is-vertical.is-parent
         [:div.tile.is-child.box
          [:figure
           [derivation-percent-recycled/root]]
          [:h2.title.is-6.has-text-danger "Recycling"]
          [:h2.subtitle.is-5 "What was the percentage in the recycling collection?"]
          [:p
           "NB: % in bin recycling collection is low compared to general so other recycling must have been taking place"]
          ]

         [:div.tile.is-child.box
          [:figure
           [derivation-missed/root]]
          [:h2.title.is-6.has-text-danger "Missed bins"]
          [:h2.subtitle.is-5 "How many bins were missed?"]
          [:p
           "NB: trend is good"]
          ]]
          ]
         ]




       ])

