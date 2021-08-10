(ns dcs.prototype-6.view.regional-dashboard.region-curiosities
  (:require [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]))

(defn ele [region]
  ;; Per-region (manually-curated) points-of-interest.
  ;; (NB didn't manage to get (rfe/href ...) to work inside a (def ..) - probably 'cause the router is initialised at that stage.)
  (let [points-of-interest 
        {"Aberdeen City"     [:div.content
                              [:span.has-text-success "Aberdeen City is doing well."]
                              " In 2017 its % recycled for household waste, overtook the Scottish average."
                              " Also in 2017, it started to manage a significant proportion of household waste as " 
                              [:em "Other Diversion"] " (i.e. through inceneration, bio gas generation, etc.)."]
         "Aberdeenshire"     [:div.content
                              [:span.has-text-warning "Aberdeenshire latest household waste figures aren't so good."]
                              " Also not so good is its generation of business waste."
                              " However, its household waste " [:em "trend"] " figures are very positive."]
         "City of Edinburgh" [:div.content
                              [:span.has-text-grey "The City of Edinburgh is broadly following the trends of Scotland as a whole, for household waste."]
                              " But there are a few noticable peaks and toughs in its data graphs."
                              " In household waste during 2019, " [:em "Combustable wastes"] 
                              " spiked as did, as did waste management by " [:em "Other Diversion"] " (i.e. through inceneration, bio gas generation, etc.)."
                              " And in 2016, its % recycled reeached the Scottish average but then fell away in subsequent years."]
         "Clackmannanshire"  [:div.content
                              [:span.has-text-danger "Clackmannanshire's graphs show curious spikes."]
                              " In 2016 there were spikes in " [:em "Mixed and undifferentiated materials"] " for both household and business waste."
                              " There is also a noticable drip in household waste recyling around 2016.​"
                              [:span.has-text-grey [:em "Why?"]]
                              [:div.has-text-centered "Also see: the " [:a {:href (rfe/href :dcs.prototype-6.router/ace-furniture-view)} "ACE Furniture"] " reuse initative"]]
         "Dundee City"       [:div.content
                              [:span.has-text-warning "Dundee City's household waste trends look concerning."]
                              " But these trends are adversely affected by its irregular 2011 figures which might be due to an old accounting method."
                              " Also, in its management of household waste, its sends the majority to " [:em "Other Diversion"] " (i.e. to inceneration, bio gas generation, etc.)"
                              " and surprisingly low proportions are " [:em "Landfilled"] "."]
         "Fife"              [:div.content
                              [:span.has-text-warning "Fife's latest household waste figures aren't so good."]
                              " But, it does have the best trend in household waste reduction."
                              " Also interesting is that the signature of Fife's Longannet power station (including its closing in 2016) can be seen in its business " 
                              [:em "Combustion wastes"] " data."
                              [:div.has-text-centered "Related: " "the Fife region in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hvb"})} "Waste through the decade"]]]
         "Glasgow City"      [:div.content
                              [:span.has-text-danger "Glasgow City doesn't seem to be doing so great"]
                              " Although their latest data about the generation of household waste (an important metric) does place them noticably better than the Scottish average."]
         "Highland"          [:div.content
                              [:span.has-text-danger "Highland doesn't seem to be doing so well."]
                              " But its positions are similar to most of the other rural and island regions."
                              " In particular, its % recycled of household waste is falling behind the Scottish average."]
         "Inverclyde"        [:div.content
                              [:span.has-text-success "Inverclyde is doing well."]
                              " In the latest data (2019), it generates the fewest tonnes of household waste (per citizen) of any of the council areas."
                              " And its same 1st position for CO" [:sub "2"] "e indicates the close relation between the amount of waste generated and its carbon impact."
                              " Also, in its latest year's business waste data, " [:em "Common sludges"] " has reduced significantly."]
         "Outer Hebrides"    [:div.content
                              [:span.has-text-danger "The Outer Hebrides doesn't seem to be doing so well."]
                              " But its positions are similar to most of the other rural and island regions."
                              " One curiousity is the large amount of " [:em "Soils"] " that came into the Bennadrove landfill site on the Isle of Lewis in 2019."
                              [:div.has-text-centered "Related: " "the Isle of Lewis in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites"]]]
         "Perth and Kinross" [:div.content
                              [:span.has-text-grey "Perth and Kinross is broadly following the trends of Scotland as a whole, for household waste."]
                              " Its data shows an anomaly in its business waste " [:em "Common sludges"] " in 2016 (perhaps some of the 2017 amount ought to have been accounted against the 2015 year?)." ]
         "Stirling"          [:div.content
                              [:span.has-text-grey "Stirling is broadly following the trends of Scotland as a whole, for household waste."]
                              [:div.has-text-centered "Related: " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)} "Stirling's bin collection"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-community-food-view)} "Stirling Community Food"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/fairshare-view)} "The Fair Share"]]]}]
(get points-of-interest region [:div " "])))

(defn root []
  [ele @state/region-cursor])