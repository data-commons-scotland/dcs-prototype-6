(ns dcs.prototype-6.annotation-data)


(comment
  
  {:text ""
   :applications [{:datapoint {}
                   :dx 0
                   :dy 0}]}
  
  )


(def ANNOTATIONS
  
  [;; household-waste-analysis

   {:id   "3"
    :text "We can see that rural £/££ households dispose of a lot of fine-grained material, i.e. Fines (<10mm). 
                        Also, they dispose of a sizable portion of it inappropriately in recycling bins."
    :pred #(and (= :household-waste-analysis-derivation (:record-type %))
                (= "Fines (<10mm)" (:material-L2 %))
                (= "rural £/££" (:stratum %))
                (= "recycling bin" (:stream %)))}
   {:id   "4"
    :text "Urban £££ households dispose of a lot of Green garden waste inappropriately in comparison to their rural £££ peers. 
                        Is that because urban £££ households have fewer convenient spaces in which to heap their garden waste! 
                        Perhaps, but it is foolish to make such inferences from data (such as this) which covers a relatively small number of observations."
    :pred #(and (= :household-waste-analysis-derivation (:record-type %))
                (= "Green garden waste" (:material-L2 %))
                (= "urban £££" (:stratum %))
                (= "grey bin" (:stream %)))}

   ;; regional-dashboard

   ;; Scot gov target

   {:id "1"
    :text "By 2025, the Scottish Government aims to reduce total waste arising in Scotland by 15% against 2011 levels."
    :pred #(and (= :household-waste-derivation-generation (:record-type %))
                (= "Scot gov target" (:region %))
                (= "2019" (:year %)))}

   {:id "2"
    :text "By 2025, the Scottish Government aims to recycle 70% of remaining waste."
    :pred #(and (= :household-waste-derivation-percent-recycled (:record-type %))
                (= "Scot gov target" (:region %))
                (= "2019" (:year %)))}
   
   ;; Angus

    {:id "15"
     :text "In 2018, Angus processed more waste by ('Other Diversion') incenerating, converting to bio gas, etc. than by landfilling."
     :pred #(and (= :household-waste-derivation-management (:record-type %))
                 (= "Angus" (:region %))
                 (= "2018" (:year %))
                 (= "Other Diversion" (:management %)))}
   
   ;; Inverclyde

   {:id "9"
    :text "Inverclyde's household waste per person has been consistently better than the Scottish average.
           In 2019 it reported the best household waste per person of any of the reporting areas, at just under 0.36 tonnes per person."
    :pred #(or (and (= :household-waste-derivation-generation-positions (:record-type %))
                    (= "Inverclyde" (:region %)))
               (and (= :household-waste-derivation-generation (:record-type %))
                    (= "Inverclyde" (:region %))
                    (= "2019" (:year %))))}
   
   {:id "10"
    :text "The fraction of household waste sent for recycling is better than the Scottish average, with less than 50% going to landfill every year since 2012. 
           It would be interesting to know what is behind these positive figures;
           is it because of Inverclyde's small size, good transport links and therefore accessiblity of recycling facilities compared to more dispersed and geographically remote regions. 
           Interestingly, the significant increase in recycling rates seen between 2011 and 2013 suggests that something changed in the region in this period. 
           Were there changes to waste collections, processing, or perhaps people's awareness of the importance of waste? 
           Or was there some other factor?"
    :pred #(and (= :household-waste-derivation-percent-recycled (:record-type %))
                (= "Inverclyde" (:region %))
                (= "2019" (:year %)))}
   
      {:id "11"
       :text "These business waste figures raises some questions. 
              For example, between 2014 and 2017, Vegetal wastes appeared to almost vanish from Inverclyde's business, only to return in 2018. 
              Was this real, and if so what caused it - or was it because of a temporary change in reporting?"
       :pred #(and (= :business-waste-by-region-derivation-composition (:record-type %))
                   (= "Inverclyde" (:region %))
                   (= "2013" (:year %))
                   (= "Vegetal wastes" (:material %)))}
   
       {:id "12"
        :text "In 2018 the category Common sludges (which refers to sludges produced from waste water treatment and from food preparation and processing) has reduced significantly. 
               Is this because of improved processes or because of changes to what businesses are operating in the region - for example, has a food processing business closed down or moved away?"
        :pred #(and (= :business-waste-by-region-derivation-composition (:record-type %))
                    (= "Inverclyde" (:region %))
                    (= "2018" (:year %))
                    (= "Common sludges" (:material %)))}
      
      {:id "14"
       :text "In 2019 it reported the best household CO2e per person of any of the reporting areas, at just under 0.75 tonnes per person."
       :pred #(or (and (= :household-co2e-derivation-generation-positions (:record-type %))
                       (= "Inverclyde" (:region %)))
                  (and (= :household-co2e-derivation-generation (:record-type %))
                       (= "Inverclyde" (:region %))
                       (= "2019" (:year %))))}

   ;; Fife

   {:id "5"
    :text "There is a big change in the amount of Combustion wastes between 2015 and 2017, 
           with this category going from the biggest contributor to business waste to one of the smallest. 
           Is this the result of the closure of Longannet power station in 2016?"
    :pred #(and (= :business-waste-by-region-derivation-composition (:record-type %))
                (= "Fife" (:region %))
                (= "2016" (:year %))
                (= "Combustion wastes" (:material %)))}

   ;; Stirling

   {:id "6"
    :text "There are some fluctuations in Stirling's business waste, including an interesting change in the Soils category which almost entirely disappears from 2015 onwards."
    :pred #(and (= :business-waste-by-region-derivation-composition (:record-type %))
                (= "Stirling" (:region %))
                (= "2015" (:year %))
                (= "Soils" (:material %)))}
   
      {:id "13"
       :text "Stirling has been doing consistently better than average in terms of the fraction of household waste going to recycling 
              - but like most other regions, even more is going to need to be achieved if the Government's target is going to be met by 2025."
       :pred #(and (= :household-waste-derivation-percent-recycled (:record-type %))
                   (= "Stirling" (:region %))
                   (= "2019" (:year %)))}

   ;; Outer Hebrides

   {:id "7"
    :text "Like other island and rural regions, the Outer Hebrides sends a lower-than-average fraction of its household waste for recycling. 
           However it is important to realise that collecting and processing recycling is not so easy for a distributed community without major transport infrastructure. 
           Also, shipping materials long distances to recycling facilitaites bears its own environmental costs in carbon emissions and pollution associated with transport."
    :pred #(and (= :household-waste-derivation-percent-recycled (:record-type %))
                (= "Outer Hebrides" (:region %))
                (= "2019" (:year %)))}
   
   {:id "8"
    :text "A large drop in the amount of business waste reported between 2011 and 2012, followed by a sustained increase up to 2019, when levels had almost returned to those of 2011. 
           These changes are almost entirely down to changes in the category Other mineral wastes. 
           Were these changes real, or are they a result of a change in the way a certain type of waste was reported?
           A related curiousity is the large amount of Soils that came into the Bennadrove landfill site on the Isle of Lewis in 2019."
    :pred #(and (= :business-waste-by-region-derivation-composition (:record-type %))
                (= "Outer Hebrides" (:region %))
                (= "2012" (:year %))
                (= "Other mineral wastes" (:material %)))}


   ])
