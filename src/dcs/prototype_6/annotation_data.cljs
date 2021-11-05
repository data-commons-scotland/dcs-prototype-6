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

   {:applications [{:datapoint {:record-type :household-waste-derivation-management
                                :region      "Angus"
                                :year        "2018"
                                :management  "Other Diversion"}}]
    :text         "In 2018, Angus processed more waste by ('Other Diversion') incenerating, converting to bio gas, etc. than by landfilling."}

   {:applications [{:datapoint {:record-type :household-waste-derivation-generation-positions
                                :region      "Inverclyde"}}
                   {:datapoint {:record-type :household-waste-derivation-generation
                                :region      "Inverclyde"
                                :year "2019"}}]
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
    :text "In 2018 the category Common sludges (which refers to sludges produced from waste water treatment and from food preparation and processing) has reduced significantly. 
           Is this because of improved processes or because of changes to what businesses are operating in the region - for example, has a food processing business closed down or moved away?"}

   {:applications [{:datapoint {:record-type :household-co2e-derivation-generation-positions
                                :region      "Inverclyde"}}
                   {:datapoint {:record-type :household-co2e-derivation-generation
                                :region      "Inverclyde"
                                :year        "2019"}}]
    :text "In 2019 it reported the best household CO2e per person of any of the reporting areas, at just under 0.75 tonnes per person."}
   
   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Fife"
                                :year        "2016"
                                :material    "Combustion wastes"}}]
    :text "There is a big change in the amount of Combustion wastes between 2015 and 2017, 
           with this category going from the biggest contributor to business waste to one of the smallest. 
           Is this the result of the closure of Longannet power station in 2016?"}
   
   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Stirling"
                                :year        "2015"
                                :material    "Soils"}}]
    :text "There are some fluctuations in Stirling's business waste, including an interesting change in the Soils category which almost entirely disappears from 2015 onwards."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Stirling"
                                :year        "2019"}}]
    :text "Stirling has been doing consistently better than average in terms of the fraction of household waste going to recycling 
           - but like most other regions, even more is going to need to be achieved if the Government's target is going to be met by 2025."}
   
   {:applications [{:datapoint {:record-type :household-waste-derivation-percent-recycled
                                :region      "Outer Hebrides"
                                :year        "2019"}}]
    :text "Like other island and rural regions, the Outer Hebrides sends a lower-than-average fraction of its household waste for recycling. 
           However it is important to realise that collecting and processing recycling is not so easy for a distributed community without major transport infrastructure. 
           Also, shipping materials long distances to recycling facilitaites bears its own environmental costs in carbon emissions and pollution associated with transport."}
   
   {:applications [{:datapoint {:record-type :business-waste-by-region-derivation-composition
                                :region      "Outer Hebrides"
                                :year        "2012"
                                :material    "Other mineral wastes"}}]
    :text "A large drop in the amount of business waste reported between 2011 and 2012, followed by a sustained increase up to 2019, when levels had almost returned to those of 2011. 
           These changes are almost entirely down to changes in the category Other mineral wastes. 
           Were these changes real, or are they a result of a change in the way a certain type of waste was reported?
           A related curiousity is the large amount of Soils that came into the Bennadrove landfill site on the Isle of Lewis in 2019."}

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
                   Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."}
   
   
   ])
