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
                              [:span.has-text-success "The City of Edinburgh has consistently produced less household waste per person than the"
                               " Scottish average, and is on track to be below the Government's target in 2025."]
                              [:span.has-text-warning "However, the fraction of waste going to recycling is below both the average and the"
                               " Government's target."]
                              " Recycling in Edinburgh will have to increase substantially if that target is to be met by 2025."
                              " There are also some interesting changes that you can see in the graphs."
                              " For example, the amount of business waste seems to vary quite a lot between different years. Also, in 2019, " [:em "Combustable wastes"]
                              " in household waste spiked - as did waste management by " [:em "Other Diversion"] " (i.e. through incineration, bio gas generation, etc.)."
                              " Will that increase be maintained in 2020 and 2021?"]
         "Clackmannanshire"  [:div.content
                              [:span.has-text-danger "The amount of household waste generated per person i Clackmannanshire is above both the Scottish average and" 
                               " the Government's target."]
                              " The trend is in the right direction but there is still some way to go."
                              [:span.has-text-success "On the other hand, Clackmannashire recycles a larger fraction of its household waste than the Scottish average."]
                              " In 2016 there were spikes in " [:em "Mixed and undifferentiated materials"] " for both household and business waste."
                              " There are also some unusual, abrupt changes in some of the data. For example, the fraction of household waste going to recycling"
                              " dropped in 2015, and there was a big spike in business waste in 2016."
                              [:span.has-text-grey [:em "Why?"]]
                              [:div [:span.has-text-info "Also see: "] " the " [:a {:href (rfe/href :dcs.prototype-6.router/ace-furniture-view)} "ACE Furniture"] " reuse initative"]]
         "Dundee City"       [:div.content
                              [:span.has-text-warning "At first sight, Dundee City's household waste trends look concerning - the amount generated per person saw"
                               " a big increase in 2012, and the fraction going to recycling saw a big decrease."]
                              " What is behind these changes? It could be that the way waste was measured changed - or that there was a change in Dundee City's waste collection"
                              " and/or waste management services."
                              " Dundee City also seems unusual in its management of household waste: unlike most other regions, it sends the majority to " [:em "Other Diversion"] " (i.e. to inceneration, bio gas generation, etc.)"
                              " and surprisingly low proportions are " [:em "Landfilled"] ".  The lower-than-average percentage going to recycling is partly a result of the"
                              " use of " [:em "Other Diversion"] " - but without know what that means, we cannot say whether it's good or bad for the environment."]
         "East Renfrewshire" [:div.content
                              [:span.has-text-danger " East Renfrewshire generates significantly higher than average household waste per person."]
                              " It will have a lot of work to do to reach the Government's target by 2025."
                              [:span.has-text-success "On the other hand, a large fraction of that waste is recycled."]
                              " We might therefore ask, does it matter if the total amount of waste is high, if most of it is going to recycling?  The answer is probably yes "
                              " - it does still matter, as recycling uses up energy and resources even if it is better than landfill."
                              " In terms of business waste, there are large fluctuations in the amounts produced between different years.  Is this real, or is it something"
                              " to do with the way the amount of waste was counted?"
                             ]
         "Fife"              [:div.content
                              [:span.has-text-grey "Fife's  household waste figures have shown a sustained decrease in the amount generated per person, but they are still"
                               " above the Scotitsh average and Government target."]
                              " Until 2017, Fife was recycling a higher fraction of its household waste than the Scottish average - but that fraction has seen a decline"
                              " since then. This seems to be mainly because more of Fife's waste has been going to " [:em "Other diversion"] " - that is, incineration,"
                              " biomass generation, or similar."
                              " Looking at Fife's business waste data, there is a big change in the amount of " [:em "Combustion wastes"] " between 2015 and 2017, with"
                              " this category going from the biggest contributor to business waste to one of the smallest.  Is this the result of the closure of"
                              " Longannet power station in 2016?"
                              [:div [:span.has-text-info "Related: "] "the Fife region in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-through-time-map-view {:preset "hvb"})} "Waste through the decade"]]]
         "Glasgow City"      [:div.content
                              [:span.has-text-danger "Glasgow City's recycling rates for household waste are low, and there's no sign of any increase."]
                              " In recent years, there has been an increase in the amount of waste sent to " [:em "Other diversion"] " - which could be energy generation,"
                              " as Glasgow has invested in a new  Recycling and Renewable Energy Centre which become fully operational in 2019.  Does it make sense to"
                              " have a Government target for the fraction of waste that goes to recycling, when there are other desitnations that can be environmentally"
                              " positive such as waste to energy plants?"
                              " It's also worth pointing out that Glasgow's amount of waste generated per person is significantly lower than the Scottish average, although"
                              " recent trends may make it harder for them to reach the Goverment target by 2025."]
         "Highland"          [:div.content
                              [:span.has-text-danger "Highland's household waste data shows that this region will struggle to meet the Scottish Government's targets for"
                               " waste generated per person and fraction being recycled."]
                              " However, like other rural and island regions, Highland has to cope with a dispersed geography, a much less dense transport network, and"
                              " larger distances to recycling facilities than areas in the Central Belt."
                              " With such regional variation, does it make sense to have the same targets for all areas?."
                              " In relation to business waste, Highland saw a big decrease after 2011, which is mostly the result of decreases in the categories"
                              " " [:em "Chemical wastes"] " and " [:em "Other mineral wastes"] " .  What caused this sudden drop - is it the result of a business"
                              " closing or relocating, or the result of improved processes in a particular sector resulting in less waste?"]
         "Inverclyde"        [:div.content
                              [:span.has-text-success "Inverclyde's waste data show some interesting and positive features."]
                              " As one of Scotland's smallest unitary authorities, we might expect the total waste it produces to be lower than in other regions. However, 
                              it's important to note that the waste generated per person is also relatively small.  Inverclyde's household waste per person has been
                              consistently below the Scottish average and has been decreasing - in 2019, it reported the lowest household waste per person of any 
                              of the reporting areas, at just under 0.36 tonnes per person."
                              " The fraction of household waste sent for recycling is also higher than the Scottish average, with less than 50% going to landfill 
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
                               recycling."] " However it is important to remember that collecting and processing recycling is not so easy for a distributed community without major
                               transport infrastructure. It is also important to realise that shipping materials long distances to recycling facilitaites bears its own
                               environmental costs in carbon emissions and pollution associated with transport."
                              " For household waste, overall voluments of waste per person, waste desitnations and waste composition have all remained broadly similar in 
                              the period hsown in the graphs on this page. "
                              " However, there are some intersting features in the business waste data. There was a large drop in the amount of business waste reported between 
                              2011 and 2012, followed by a sustained increase up to 2019, when levels had almost returned to those of 2011. These changes are almost entirely down 
                              to changesin the category " [:em "Other mineral wastes"] ". Were these changes real, or are they a result of a change in the way a certain type of waste was reported? "
                              " A related curiousity is the large amount of " [:em "Soils"] " that came into the Bennadrove landfill site on the Isle of Lewis in 2019."
                              [:div [:span.has-text-info "Related: "] "the Isle of Lewis in " [:a {:href (rfe/href :dcs.prototype-6.router/waste-sites-map-view)} "Waste sites"]]]
         "Perth and Kinross" [:div.content
                              [:span.has-text-danger "Perth and Kinross has a higher than average amount of household waste generated per person, and altohugh the decreasing"
                               "trend broadly follows the national average trend, there is qiute a way to go if the Government's target is to be reached in 2025."]
                              " This region has consistently recycled between 50% and 55% of its household waste -it is ahead of other regions in this respect,"
                              " but the trend needs to be for even more recycling if the Government's target is going to be met."
                              " Perth and Kinross has some interesting features in its business waste data, with what seems to be an underling decreasing in the amount"
                              " generated interrupted by two big spiks in 2016 and 2018. Are these real increases, or is this a result of e.g. the timing of the reporting of"
                              " some waste, so that 2016 and 2018 are artificailly high and perhaps 2015 and 2017 are artificially low?" ]
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
                              [:span.has-text-grey "Stirling is pretty average when it comes to the amount of household waste generated per person - that means"
                               " it has seen a decrease over recent years, but bigger cuts will be needed if the Scottish Government's target is going to be met in 2025."]
                              " In the period shown in the graphs, Stirling has been doing consistently better than average in terms of the fraction of household waste"
                              " going to recycling - but like most other regions, even more is going to need to be achieved if the Government's target is going to be met by"
                              " 2025. "
                              " There are some fluctuations in Stirling's business waste data, with the " [:em "Vegetal wastes"] " category showing significant"
                              " variation. There's also an interesting change in the " [:em "Soils"] " category, which almost entirely disappears from 2015 onwards."
                              [:div [:span.has-text-info "Related: "] [:a {:href (rfe/href :dcs.prototype-6.router/stirling-bin-collection-view)} "Stirling's bin collection"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/stirling-community-food-view)} "Stirling Community Food"]
                               " | " [:a {:href (rfe/href :dcs.prototype-6.router/fairshare-view)} "The Fair Share"]]]}]
(get points-of-interest region [:div " "])))

(defn root []
  [ele @state/region-cursor])
