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
                              "Aberdeenshire's household waste data show what the region has been achieving 
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
                              " But there are a few noticable peaks and troughs in its data graphs."
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
                              [:span.has-text-success "Inverclyde's waste data show some interesting and positive features."]
                              " As one of Scotland's smallest unitary authorities, we might expect the total waste it produces to be lower than in other regions. However, 
                              it's important to note that the waste generated per person is also relatively small.  Inverclyde's household waste per person has been
                              consistently below the SCottish average and has been decreasing - in 2019, it reported the lowest household waste per person of any 
                              of the reporting areas, at just under 0l.36 tonnes per person."
                              " The fraction of household waste sent for recycling is also higher than the Scotitsh average, with less than 50% going to landfill 
                              every year since 2012. "
                              " It would be interesting to know what is behind these positive figures.  The generally high recycling rates compared to the national average 
                              might be a result of Inverclyde's small size, good transport links and therefore accessiblity of recycling facilities compared to more dispersed and 
                              geographically remote regions. However, the significant increase in recycling rates seen between 2011 and 
                              2013 suggests that something changed in the region in this period.  Were there changes to waste collections, processing, or perhaps people's 
                              awareness of the importance of waste? Or was there some other factor?  "
                              " The data on business waste also raise some questions.  For example, between 2014 and 2017, " [:em "Vegetal wastes"] " appeared to almost 
                              vanish from Inverclyde's business, only to return in 2018. Was this real, and if so what caused it - or was it because of a temporary change in 
                              reporting? Similarly, in 2018, the category " [:em "Common sludges"] " (which refers to sludges produced from waste water treatment and from food
                              preparation and processing) has reduced significantly.  Is this because of improved processes or because of changes to what businesses are operating
                              in the region - for example, has a food processing business closed down or moved away?"]
         "Outer Hebrides"    [:div.content
                              [:span.has-text-danger "Like other island and rural regions, the Outer Hebrides sends a lower-than-average fraction of its household waste for 
                               recycling. However it is important to remember that collecting and processing recycling is not so easy for a distributed community without major
                               transport infrastructure. It is also important to realise that shipping materials long distances to recycling facilitaites bears its own
                               environmental costs in carbon emissions and pollution associated with transport."]
                              " For household waste, overall voluments of waste per person, waste desitnations and waste composition have all remained broadly similar in 
                              the period hsown in the graphs on this page. "
                              " However, there are some intersting features in the business waste data. There was a large drop in the amount of business waste reported between 
                              2011 and 2012, followed by a sustained increase up to 2019, when levels had almost returned to those of 2011. These changes are almost entirely down 
                              to changesin the category " [:em "Other mineral wastes"] ". Were these changes real, or are they a result of a change in the way a certain type of waste was reported? "
                              " A related curiousity is the large amount of " [:em "Soils"] " that came into the Bennadrove landfill site on the Isle of Lewis in 2019."
                              [:div [:span.has-text-info "Related: "] "the Isle of Lewis in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites"]]]
         "Perth and Kinross" [:div.content
                              [:span.has-text-grey "Perth and Kinross is broadly following the trends of Scotland as a whole, for household waste."]
                              " Its data shows an anomaly in its business waste " [:em "Common sludges"] " in 2016 (perhaps some of the 2017 amount ought to have been 
                              accounted against the 2015 year?)." ]
         "Renfrewshire"      [:div.content
                              [:span.has-text-grey "Renfrewshire's waste data show both some successes and some areas for potential improvement."]
                              " Although the amount of waste generated per person has been above the Scottish average since 2013, and there is some way to go to reach the 
                              Governemnt's target, the fraction of household waste going to recycling is above the Scottish averge and has broadly been increasing since 2012. "
                              " There has also been a sustained decrease in the amount of waste going to landfill.  However, this has been as a result of an increase in the
                              amount of waste going to " [:em "Other diversion"] ". "
                              " Since 2015, there seems to have been a reduction in the amount of waste in the category "[:em "Household and similar wastes"]" but this has 
                              largely been compensated for by an increase in " [:em "Mixed and undifferentiated materials"] ". Perhaps this change in the data is a result 
                              of a change in the way collections are organised or the types of bins people have access to - or perhaps it is due to a change to the waste 
                              management services procured by the local authority."
                              " This region also seems to have quite large fluctuations in the amounts of business waste being reported.  Between 2011 and 2019, business 
                              waste has varied between just under 60,000 tonnes and just over 100,000 tonnes. "
                              " As well as quetsions as to the source of these variations, the data from Renfrewshire raise intersting questions when compared to other, 
                              neighbouring regions such as Inverclyde.  For example, why is Renfrewshire's waste generated per person substantially higher than Inverclyde's? "]
         "Stirling"          [:div.content
                              [:span.has-text-grey "Stirling is broadly following the trends of Scotland as a whole, for household waste."]
                              [:div [:span.has-text-info "Related: "] [:a {:href (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)} "Stirling's bin collection"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-community-food-view)} "Stirling Community Food"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/fairshare-view)} "The Fair Share"]]]}]
(get points-of-interest region [:div " "])))

(defn root []
  [ele @state/region-cursor])
