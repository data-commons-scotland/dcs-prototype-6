(ns dcs.prototype-6.annotation-data)


(def ANNOTATIONS
  
  [{:applications [{:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Scot gov target"
                                :year        "2019"}
                    :dx        0
                    :dy        0}]
    :text         "By 2025, the Scottish Government aims to reduce total waste arising in Scotland by 15% against 2011 levels."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Scot gov target"
                                :year        "2019"}}]
    :text         "By 2025, the Scottish Government aims to recycle 70% of remaining waste."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "Aberdeen City"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Aberdeen City"
                                :year        "2016"}}]
    :text         "Aberdeen City in 2016, increased the percentage of recycled household waste. 
                   What has caused this change? 
                   Is it something other regions can learn from, such as changes to waste collections or public awareness and education campaigns?"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Aberdeen City"
                                :year        "2017"}}]
    :text         "In 2017/18 the fraction of household waste that went to recycling overtook the Scottish average. 
                   However, it still has a way to go before it reaches the Government's goal of 70% recycling, which is set for 2025."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Aberdeen City"
                                :year        "2017"
                                :management  "Other Diversion"}}]
    :text         "In 2017, a significant proportion of Aberdeen City's household waste that was previously going to landfill was instead managed through Other Diversion. 
                   We might ask, what is happening to this waste - is it going to, e.g. energy generation, or is it simply being incinerated?"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Aberdeenshire"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Aberdeenshire"
                                :year        "2019"}}]
    :text         "Aberdeenshire's household waste data show what the region has been achieving in terms of waste generated per person since 2011. 
                   It is now approaching the Scottish average, although the rate of this reduction will need to increase further if it is going to meet the Government's overall target by 2025."}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Aberdeenshire"
                                :year        "2012"
                                :material    "Animal and mixed food waste"}}]
    :text         "Aberdeenshire's business waste has been increasing since around 2012. 
           This increase seems to be largely driven by increases in waste in the Vegetal and Animal and mixed food waste categories."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Aberdeenshire"
                                :year        "2016"
                                :management  "Recycled"}}]
    :text         "Both the total amount of domestic waste going to recycling and the fraction of all waste being managed has remained fairly static for the past few years. 
                   This raises some intersting questions. 
                   Aberdeenshire is a rich farming area, which may account for the significant contributions of Vegetal and Animal and mixed food waste to the business waste figures. 
                   So, could a reduction in business waste be achieved by targeting the agricultural sector? 
                   And what can be done to increase the fraction of domestic waste going to recycling, to help aberdeenshire head towards the Government's targets? 
                   Is it simply a matter of education, or do we need to accept that some regions have contextual factors such as geography, access to recycling facilities, etc., and make the targets more sensitive to these factors?"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Angus"
                                :year        "2018"
                                :management  "Other Diversion"}}]
    :text         "In 2018, Angus processed more waste by ('Other Diversion') incenerating, converting to bio gas, etc. than by landfilling."}

   
   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "City of Edinburgh"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "City of Edinburgh"
                                :year        "2019"}}]
    :text         "The City of Edinburgh has consistently produced less household waste per person than the Scottish average, 
                   and is on track to be below the Government's target in 2025."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "City of Edinburgh"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "City of Edinburgh"
                                :year        "2019"}}]
    :text         "The City of Edinburgh's fraction of waste going to recycling is below both the average and the Government's target. 
                   Recycling in Edinburgh will have to increase substantially if that target is to be met by 2025."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-composition
                                :region      "City of Edinburgh"
                                :year        "2019"
                                :material    "Combustion wastes"}}
                   {:datapoint {:record-type :household-waste-derivation-management
                                :region      "City of Edinburgh"
                                :year        "2019"
                                :management  "Other Diversion"}}]
    :text         "In 2019, Combustable wastes in household waste spiked - as did waste management by Other Diversion (i.e. through incineration, bio gas generation, etc.). 
                   Will that increase be maintained in 2020 and 2021?"}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "City of Edinburgh"
                                :year        "2014"
                                :material    "Soils"}}]
    :text         "The amount of business waste seems to vary quite a lot between different years!"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Clackmannanshire"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Clackmannanshire"
                                :year        "2019"}}]
    :text         "The amount of household waste generated per person in Clackmannanshire is above both the Scottish average and the Government's target. 
                   The trend is in the right direction but there is still some way to go."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "Clackmannanshire"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Clackmannanshire"
                                :year        "2019"}}]
    :text         "Clackmannashire recycles a larger fraction of its household waste than the Scottish average."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-composition
                                :region      "Clackmannanshire"
                                :year        "2016"
                                :material    "Mixed and undifferentiated materials"}}
                   {:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Clackmannanshire"
                                :year        "2016"
                                :material    "Mixed and undifferentiated materials"}}]
    :text         "In 2016 there were spikes in Mixed and undifferentiated materials for both household and business waste.
                   Why?"}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Clackmannanshire"
                                :year        "2015"
                                :management  "Recycled"}}]
    :text         "The fraction of household waste going to recycling dropped in 2015.
                   Why?"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Dundee City"
                                :year        "2012"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Dundee City"
                                :year        "2012"}}]
    :text         "At first sight, Dundee City's household waste trends look concerning 
                   - the amount generated per person saw a big increase in 2012, 
                   and the fraction going to recycling saw a big decrease. 
                   What is behind these changes? It could be that the way waste was measured changed 
                   - or that there was a change in Dundee City's waste collection and/or waste management services."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Dundee City"
                                :year        "2013"
                                :management  "Other Diversion"}}]
    :text         "Dundee City seems unusual in its management of household waste: 
                unlike most other regions, it sends the majority to Other Diversion (i.e. to inceneration, bio gas generation, etc.) 
                and surprisingly low proportions are Landfilled. 
                The lower-than-average percentage going to recycling is partly a result of the use of Other Diversion 
                - but without know what that means, we cannot say whether it's good or bad for the environment."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "East Renfrewshire"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "East Renfrewshire"
                                :year        "2019"}}]
    :text         "Although East Renfrewshire generates higher than average household waste per person,
                a large fraction of that waste is recycled. 
                We might therefore ask, does it matter if the total amount of waste is high, if most of it is going to recycling? 
                The answer is probably yes - it does still matter, as recycling uses up energy and resources even if it is better than landfill."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Fife"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Fife"
                                :year        "2019"}}]
    :text         "Fife's household waste figures have shown a sustained decrease in the amount generated per person, 
        but they are still above the Scotitsh average and Government target."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Fife"
                                :year        "2017"}}]
    :text         "Until 2017, Fife was recycling a higher fraction of its household waste than the Scottish average 
        - but that fraction has seen a decline since then. 
        This seems to be mainly because more of Fife's waste has been going to Other diversion - that is, incineration, biomass generation, or similar."}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Fife"
                                :year        "2016"
                                :material    "Combustion wastes"}}]
    :text         "There is a big change in the amount of Combustion wastes between 2015 and 2017, 
           with this category going from the biggest contributor to business waste to one of the smallest. 
           Is this the result of the closure of Longannet power station in 2016?"}


   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "Glasgow City"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Glasgow City"
                                :year        "2019"}}]
    :text         "Glasgow City's recycling rates for household waste are low, and there's no sign of them getting better."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Glasgow City"
                                :year        "2017"
                                :management  "Other Diversion"}}]
    :text         "In recent years, there has been an increase in the amount of waste sent to Other diversion 
          - which could be energy generation, as Glasgow has invested in a new Recycling and Renewable Energy Centre 
          which become fully operational in 2019. 
          Does it make sense to have a Government target for the fraction of waste that goes to recycling, when there are other desitnations that can be environmentally positive such as waste to energy plants?"}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Glasgow City"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Glasgow City"
                                :year        "2019"}}]
    :text         "Glasgow's amount of waste generated per person is significantly lower than the Scottish average, although recent trends may make it harder for them to reach the Goverment target by 2025."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Highland"}}
                   {:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                :region      "Highland"}}]
    :text         "Highland's household waste data shows that this region will struggle to meet the Scottish Government's targets for waste generated per person 
          and fraction being recycled. 
          However, like other rural and island regions, Highland has to cope with a dispersed geography, 
          a much less dense transport network, and larger distances to recycling facilities than areas in the Central Belt. 
          With such regional variation, does it make sense to have the same targets for all areas?"}
   
   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Highland"
                                :year        "2011"
                                :material    "Chemical wastes"}}]
    :text         "In relation to business waste, Highland saw a big decrease after 2011, which is mostly the result of decreases in the categories Chemical wastes and Other mineral wastes. 
          What caused this sudden drop - is it the result of a business closing or relocating, or the result of improved processes in a particular sector resulting in less waste?"}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Inverclyde"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Inverclyde"
                                :year        "2019"}}]
    :text         "Inverclyde's household waste per person has been consistently better than the Scottish average.
                   In 2019 it reported the best household waste per person of any of the reporting areas, at just under 0.36 tonnes per person."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Inverclyde"
                                :year        "2019"}}]
    :text         "The fraction of household waste sent for recycling is better than the Scottish average, with less than 50% going to landfill every year since 2012. 
                   It would be interesting to know what is behind these positive figures;
                   is it because of Inverclyde's small size, good transport links and therefore accessiblity of recycling facilities compared to more dispersed and geographically remote regions.
                   Interestingly, the significant increase in recycling rates seen between 2011 and 2013 suggests that something changed in the region in this period. 
                   Were there changes to waste collections, processing, or perhaps people's awareness of the importance of waste? 
                   Or was there some other factor?"}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Inverclyde"
                                :year        "2013"
                                :material    "Vegetal wastes"}}]
    :text         "These business waste figures raises some questions.
                   For example, between 2014 and 2017, Vegetal wastes appeared to almost vanish from Inverclyde's business, only to return in 2018. 
                   Was this real, and if so what caused it - or was it because of a temporary change in reporting?"}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Inverclyde"
                                :year        "2018"
                                :material    "Common sludges"}}]
    :text         "In 2018 the category Common sludges (which refers to sludges produced from waste water treatment and from food preparation and processing) has reduced significantly. 
           Is this because of improved processes or because of changes to what businesses are operating in the region - for example, has a food processing business closed down or moved away?"}

   {:applications [{:datapoint {:record-type :household-co2e-derivation-generation-positions
                                :region      "Inverclyde"}}
                   {:datapoint {:record-type :household-co2e-derivation-generation
                                :region      "Inverclyde"
                                :year        "2019"}}]
    :text         "In 2019 it reported the best household CO2e per person of any of the reporting areas, at just under 0.75 tonnes per person."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Outer Hebrides"
                                :year        "2019"}}]
    :text         "Like other island and rural regions, the Outer Hebrides sends a lower-than-average fraction of its household waste for recycling. 
           However it is important to realise that collecting and processing recycling is not so easy for a distributed community without major transport infrastructure. 
           Also, shipping materials long distances to recycling facilitaites bears its own environmental costs in carbon emissions and pollution associated with transport."}

   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Outer Hebrides"
                                :year        "2012"
                                :material    "Other mineral wastes"}}]
    :text         "A large drop in the amount of business waste reported between 2011 and 2012, followed by a sustained increase up to 2019, when levels had almost returned to those of 2011. 
           These changes are almost entirely down to changes in the category Other mineral wastes. 
           Were these changes real, or are they a result of a change in the way a certain type of waste was reported?
           A related curiousity is the large amount of Soils that came into the Bennadrove landfill site on the Isle of Lewis in 2019."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Perth and Kinross"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Perth and Kinross"
                                :year        "2019"}}]
    :text         "Perth and Kinross has a higher than average amount of household waste generated per person 
                   and although the decreasing trend broadly follows the national average trend, 
                   there is qiute a way to go if the Government's target is to be reached in 2025."}

    {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled-positions
                                 :region      "Perth and Kinross"}}
                    {:datapoint {:record-type :household-waste-derivation-percent-recycled
                                 :region      "Perth and Kinross"
                                 :year        "2019"}}]
     :text         "Perth and Kinross This region has consistently recycled between 50% and 55% of its household waste 
                    - it is ahead of other regions in this respect, but the trend needs to be for even more recycling 
                    if the Government's target is going to be met."}

    {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-generation
                                 :region      "Perth and Kinross"
                                 :year        "2016"}}]
     :text         "Perth and Kinross has some interesting features in its business waste data, 
                    with what seems to be an underling decreasing in the amount generated interrupted by two big spikes in 2016 and 2018. 
                    Are these real increases, or is this a result of e.g. the timing of the reporting of some waste, 
                    so that 2016 and 2018 are artificailly high and perhaps 2015 and 2017 are artificially low?"}
   
   ;; ----- HERE ------
   


   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Stirling"
                                :year        "2015"
                                :material    "Soils"}}]
    :text         "There are some fluctuations in Stirling's business waste, including an interesting change in the Soils category which almost entirely disappears from 2015 onwards."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Stirling"
                                :year        "2019"}}]
    :text         "Stirling has been doing consistently better than average in terms of the fraction of household waste going to recycling 
           - but like most other regions, even more is going to need to be achieved if the Government's target is going to be met by 2025."}


   {:applications [{:datapoint {:record-type :household-waste-analysis-derivation
                                :stratum     "rural £/££"
                                :stream      "recycling bin"
                                :material-L2 "Fines (<10mm)"}}]
    :text         "We can see that rural £/££ households dispose of a lot of fine-grained material, i.e. Fines (<10mm).
                   Also, they dispose of a sizable portion of it inappropriately in recycling bins."}

   {:applications [{:datapoint {:record-type :household-waste-analysis-derivation
                                :stratum     "urban £££"
                                :stream      "grey bin"
                                :material-L2 "Green garden waste"}}]
    :text         "Urban £££ households dispose of a lot of Green garden waste inappropriately in comparison to their rural £££ peers.
                   Is that because urban £££ households have fewer convenient spaces in which to heap their garden waste!
                   Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."}])
