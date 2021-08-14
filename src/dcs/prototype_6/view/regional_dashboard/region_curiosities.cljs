(ns dcs.prototype-6.view.regional-dashboard.region-curiosities
  (:require [reitit.frontend.easy :as rfe]
            [dcs.prototype-6.state :as state]))

(defn ele [region]
  ;; Per-region (manually-curated) points-of-interest.
  ;; (NB didn't manage to get (rfe/href ...) to work inside a (def ..) - probably 'cause the router is initialised at that stage.)
  (let [points-of-interest 
        {"Aberdeen City"     [:div.content
                              [:span.has-text-success "The available data on Aberdeen City's household waste suggests that 
                               it is moving in the right direction in relation to reaching the Scottish Government's 
                               waste reduction targets."]
                              " In 2017/18 the fraction of household waste that went to recycling overtook the Scottish average. 
                              However, it still has a way to go before it reaches the Government's goal of 70% recycling, 
                              which is set for 2025."
                              " The data raise some interesting questions. For example, they show that in 2017, a significant 
                              proportion of Aberdeen City's household waste that was previously going to landfill was instead
                              managed through " 
                              [:em "Other Diversion"] ". We might ask, 
                              what is happening to this waste - is it going to, e.g., energy generation, or is it simply being 
                              incinerated?"
                              " Another, related questio relates to the increasing percentage of recycled waste after 2017. Looking at 
                              the absolute numbers, there has been a small increase in the amounts of waste going to recycling. However, 
                              there has also been a reduction in the total amount of non-recycled waste (i.e. the sum of " [:em "Landfill"] " and 
                              " [:em "Other Diversion"] "). What has caused these changes? Is it something other regions can learn from, such 
                              as changes to waste collections or public awareness and education campaigns?"]
         "Aberdeenshire"     [:div.content
                              "Aberdeenshire's household waste data shows what the region has been achieving 
                               in terms of waste generated per person since 2011."
                              [:span.has-text-success " It is now approaching the Scottish average"] 
                              ", although the rate of this reduction will need to increase further if it is going to meet the Government's overall target by 2025."
                              " However, Aberdeenshire's business waste data shows that it is not only 
                                significantly higher than the Scottish average - it has also been generally increasing since around 2012."
                              " This increase seems to be largely driven by increases in waste in the " [:em "Vegetal"] " and 
                              " [:em "Animal and mixed food waste"] " categories."
                              " The data also show that both the total amount of domestic waste going to recycling and the fraction of all waste
                              being managed has remained fairly static for the past few years."
                              " This raises some intersting questions. Aberdeenshire is a rich farming area, which may account for the significant 
                              contributions of " [:em "Vegetal"] " and " [:em "Animal and mixed food waste"] " to the business waste figures. 
                              So, could a reduction in 
                              business waste be achieved by targeting the agricultural sector? And what can be done to increase 
                              the fraction of domestic waste going to recycling, to help aberdeenshire head towards the Government's targets?
                              Is it simply a matter of education, or do we need to accept that some regions have contextual factors such as
                              geography, access to recycling facilities, etc., and make the targets more sensitive to these factors?"]
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
                              [:div [:span.has-text-info "Also see: "] " the " [:a {:href (rfe/href :dcs.prototype-6.router/ace-furniture-view)} "ACE Furniture"] " reuse initative"]]
         "Dundee City"       [:div.content
                              [:span.has-text-warning "Dundee City's household waste trends look concerning."]
                              " But these trends are adversely affected by its irregular 2011 figures which might be due to an old accounting method."
                              " Also, in its management of household waste, its sends the majority to " [:em "Other Diversion"] " (i.e. to inceneration, bio gas generation, etc.)"
                              " and surprisingly low proportions are " [:em "Landfilled"] "."]
         "East Renfrewshire" [:div.content
                              [:span.has-text-success "East Renfrewshire's recycling of household waste is impressive."]
                              [:span.has-text-warning " However, that acheivement is tempered by its high household waste generation figures."]]
         "Fife"              [:div.content
                              [:span.has-text-warning "Fife's latest household waste figures aren't so good."]
                              " But, it does have the best trend in household waste reduction."
                              " Also interesting is that the signature of Fife's Longannet power station (including its closing in 2016) can be seen in its business " 
                              [:em "Combustion wastes"] " data."
                              [:div [:span.has-text-info "Related: "] "the Fife region in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hvb"})} "Waste through the decade"]]]
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
                              [:div [:span.has-text-info "Related: "] "the Isle of Lewis in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites"]]]
         "Perth and Kinross" [:div.content
                              [:span.has-text-grey "Perth and Kinross is broadly following the trends of Scotland as a whole, for household waste."]
                              " Its data shows an anomaly in its business waste " [:em "Common sludges"] " in 2016 (perhaps some of the 2017 amount ought to have been accounted against the 2015 year?)." ]
         "Stirling"          [:div.content
                              [:span.has-text-grey "Stirling is broadly following the trends of Scotland as a whole, for household waste."]
                              [:div [:span.has-text-info "Related: "] [:a {:href (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)} "Stirling's bin collection"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-community-food-view)} "Stirling Community Food"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/fairshare-view)} "The Fair Share"]]]}]
(get points-of-interest region [:div " "])))

(defn root []
  [ele @state/region-cursor])
