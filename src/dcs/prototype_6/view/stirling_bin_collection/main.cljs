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

       [:div.container
        [:div.content
         [:p "Stirling Council set a precedent by being the first (and " [:em "still"] " only) Scottish local authority
        to have published " [:b "open data"] " about their bin collection of household waste.
        This data is contained in the "
          [:a {:href "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/index.html#stirling-bin-collection"}
           "stirling-bin-collection"] " dataset."]
         [:p "The council are currently working on increasing the fidelity of this dataset,
        e.g. by adding spatial data to describe collection routes.
        However, we can still squeeze from its current version, several interesting pieces of information..."]
         [:br]]]

       [:div.tile.is-ancestor

        [:div.tile.is-6
         [:div.tile.is-vertical.is-parent

          [:div.tile.is-child.message.has-background-white.is-info
           [:div.message-header
            [:div
             [:h2.title.is-6.has-text-weight-bold "Amount"]
             [:h2.subtitle.is-5.has-text-primary-light "How much material was in the bins?"]]]
           [:div.message-body
            [:figure
             [derivation-generation/root]]
            [:div.content.has-text-warning-dark.has-text-weight-bold
             [:p "The amount per citizen of household waste in bin collections,
           has increased slightly." [:sup "[1][2]"]]]
            [:p "The amount peaks occurring during the summer months.
            The cause seems to be the seasonality of the garden waste collection
            (included under the category \"Animal and mixed food waste\" in the " [:strong "Composition"] " graph)."]
            [:br]
            [:div.content.is-small.has-text-info
             [:ol
              [:li "To provide context, the graph also depicts the estimated amounts accounted for through "
               [:em "all means"] " for Stirling and Scotland."]
              [:li "The population data for 2020 and 2021 was not available to us,
           so we're using 2019 population figures for those years."]]]]]

          [:div.tile.is-child.message.has-background-white.is-link
           [:div.message-header
            [:div
             [:h2.title.is-6.has-text-weight-bold "Missed bins"]
             [:h2.subtitle.is-5.has-text-primary-light "How many bins were missed?"]]]
           [:div.message-body
            [:figure
             [derivation-missed/root]]
            [:div.content.has-text-success-dark.has-text-weight-bold
             [:p "The amount of household waste attributed to "
              [:em "missed bins"] ", fell significantly between Q1 2018 and Q3 2019."]]]]]]

        [:div.tile.is-6
         [:div.tile.is-vertical.is-parent

          [:div.tile.is-child.message.has-background-white.is-link
           [:div.message-header
            [:div
             [:h2.title.is-6.has-text-weight-bold "Recycling"]
             [:h2.subtitle.is-5.has-text-primary-light "What percentage was put into the recycling collection?"]]]
           [:div.message-body
            [:figure
             [derivation-percent-recycled/root]]
            [:div.content.has-text-success-dark.has-text-weight-bold
             [:p "The percentage of household waste in recycling-bin collections,
           has increased slightly." [:sup "[1]"]]]
            [:p
             "The recycling percentage at the point of bin collections is still substantially less than the overall percentage.
             Perhaps that is due to the " [:em "sorting & recycling"]
             " that occurs downstream from bin collection and at tip sites."]
            [:div.content.is-small.has-text-info
             [:ol
              [:li "To provide context, the graph also depicts the "
               [:em "overall"] " percentages of household waste recycled in Stirling and Scotland."]]]]]

          [:div.tile.is-child.message.has-background-white.is-info
           [:div.message-header
            [:div
             [:h2.title.is-6.has-text-weight-bold "Composition"]
             [:h2.subtitle.is-5.has-text-primary-light "What types of material were in the bins?"]]]
           [:div.message-body
            [:figure
             [derivation-composition/root]]
            [:div.content.has-text-warning-dark.has-text-weight-bold
             [:p [:em "Unsorted"] " household waste is the biggest component in bin-collected waste." [:sup "[1]"]]]
            [:div.content.is-small.has-text-info
             [:ol
              [:li "These figures are based on a coarse-grained mapping from the values
            supplied in Stirling council's bin collection dataset to SEPA's "
               [:a {:href "https://data-commons-scotland.github.io/dcs-wcs-prototype-3/pages-output/data/about/index.html#material-coding"} "material-coding"] "."]]]]]]]]])

