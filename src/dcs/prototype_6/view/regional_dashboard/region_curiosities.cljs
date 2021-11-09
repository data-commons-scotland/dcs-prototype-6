(ns dcs.prototype-6.view.regional-dashboard.region-curiosities
  (:require [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]))

(defn ele [region]
  ;; Per-region (manually-curated) points-of-interest.
  ;; (NB didn't manage to get (rfe/href ...) to work inside a (def ..) - probably 'cause the router is initialised at that stage.)
  (let [points-of-interest 
        {"Aberdeen City"     [:div.content
                              [:span.has-text-success "The available data on Aberdeen City's household waste suggest that 
                               it is moving in the right direction in relation to reaching the Scottish Government's 
                               waste reduction targets."]]
         "Aberdeenshire"     [:div.content
                              "Aberdeenshire's household waste is now approaching the Scottish average."] 
         "City of Edinburgh" [:div.content
                              [:span.has-text-success "The City of Edinburgh has consistently produced less household waste per person than the"
                               " Scottish average, and is on track to be below the Government's target in 2025."]]
         "Clackmannanshire"  [:div.content
                              [:span.has-text-danger "The amount of household waste generated per person in Clackmannanshire is above both the Scottish average and" 
                               " the Government's target."]
                              [:div [:span.has-text-info "Also see: "] " the " [:a {:href (rfe/href :dcs.prototype-6.router/ace-furniture-view)} "ACE Furniture"] " reuse initative"]]
         "Dundee City"       [:div.content
                              [:span.has-text-warning "At first sight, Dundee City's household waste trends look concerning - the amount generated per person saw"
                               " a big increase in 2012, and the fraction going to recycling saw a big decrease."]]
         "East Renfrewshire" [:div.content
                              [:span.has-text-danger " East Renfrewshire generates significantly higher than average household waste per person."]]
         "Fife"              [:div.content
                              [:span.has-text-grey "Fife's  household waste figures have shown a sustained decrease in the amount generated per person, but they are still"
                               " above the Scotitsh average and Government target."]
                              [:div [:span.has-text-info "Related: "] 
                               "the Fife region in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hvb"})} "Waste through the decade"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/pivot-drilldown-and-plot-view {:preset "preset1"})} "Fife's four largest business wastes"] "."]]
         "Glasgow City"      [:div.content
                              [:span.has-text-danger "Glasgow City's recycling rates for household waste are low, and there's no sign of any increase."]]
         "Highland"          [:div.content
                              [:span.has-text-danger "Highland's household waste data shows that this region will struggle to meet the Scottish Government's targets for"
                               " waste generated per person and fraction being recycled."]]
         "Inverclyde"        [:div.content
                              [:span.has-text-success "Inverclyde's waste data show some interesting and positive features."]]
         "Outer Hebrides"    [:div.content
                              [:span.has-text-danger "Like other island and rural regions, the Outer Hebrides sends a lower-than-average fraction of its household waste for 
                               recycling."] 
                              [:div [:span.has-text-info "Related: "] "the large amount of " [:em "Soils"] " landfilled on the Isle of Lewis in 2019 in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites"]]]
         "Perth and Kinross" [:div.content
                              [:span.has-text-danger "Perth and Kinross has a higher than average amount of household waste generated per person, and altohugh the decreasing "
                               "trend broadly follows the national average trend, there is quite a way to go if the Government's target is to be reached in 2025."]]
         "Renfrewshire"      [:div.content
                              [:span.has-text-grey "Renfrewshire's waste data show both some successes and some areas for potential improvement."]]
         "Stirling"          [:div.content
                              [:span.has-text-grey "Stirling is pretty average when it comes to the amount of household waste generated per person - that means"
                               " it has seen a decrease over recent years, but bigger cuts will be needed if the Scottish Government's target is going to be met in 2025."]
                              [:div [:span.has-text-info "Related: "] [:a {:href (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)} "Stirling's bin collection"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-community-food-view)} "Stirling Community Food"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/fairshare-view)} "The Fair Share"]]]}]
(get points-of-interest region [:div " "])))

(defn root []
  [ele @state/region-cursor])
