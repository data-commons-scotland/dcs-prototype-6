(ns dcs.prototype-6.view.tutorial.regional-dashboard.main
(:require [reagent.core :as r]
          [dcs.prototype-6.util :as util]
          [dcs.prototype-6.state :as state]
          [dcs.prototype-6.view.regional-dashboard.region-position :as region-position]
          [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-generation :as hw-gen]
          [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-composition :as hw-comp]
          [dcs.prototype-6.view.regional-dashboard.household-waste-derivation-management :as hw-mgmt]))

(defn ele
  [household-waste-derivation-generation
   household-waste-derivation-composition
   household-waste-derivation-management
   household-waste-derivation-generation-positions
   household-waste-derivation-percent-recycled-positions
   household-co2e-derivation-generation-positions]
  
  [:div 
   
   [:section.hero.is-small.has-background
    [:img.hero-background.is-transparent {:src "img/home-page-top-hero.jpg"
                                          :alt "About the regional dashboard image"}]
    [:div.hero-body
     [:div.container
      [:div.content.has-text-centered
       [:h1.title.is-5 "Reading the " [:em "Regional Dashboard"] " page"]
       [:h2.subtitle.is-6.has-text-info "What the graphs show and what to look out for"]]]]]

   
   [:div.section

   

   [:div.container
    [:div.content

     [:p "This page provides some information and guidance relating to the Waste Matters Scotland "
      [:em "Regional Dashboard"] " page."]
     [:p "It is intended to help you:"]
     [:ul
      [:li "understand what the data are and where they come from"]
      [:li "interpret the visualisations and ask questions about the data and what they mean"]]
     
     [:h2.title.is-5 "What are the data and where do they come from?"]
     
     [:p "The " [:em "Regional Dashboard"] " page displays a range of data sets relating to household"
      " and business waste for each of Scotland's unitary authorities."]
     [:p "The data are published by Scotland's Environment Protection Agency (SEPA). They are"
      " reported by the authorities, mostly on an annual basis. The latest available data are"
      " from the reports for the year 2019. The Waste Matters Scotland " [:em "Regional Dashboard"] " page"
      " shows data going back to 2011, which means you can look for changes over time."]
     [:p "If you click on one of the authorities (or regions) on the map on the right-hand side of the page,"
      " the data associated with that region will be displayed in a series of graphs and visualisations on the left-hand side."
      " Data relating to household waste are shown at the top. It is important to note that these data relate to household"
      " waste generated " [:em "per person"] " and not to total amounts of waste. If you scroll down, you will also be able to see"
      " data relating to business waste."]
     
     [:h2.title.is-5 "Interpreting the graphs and other data visualisations"]
     
     [:p "The first six data visulaisations displayed on this page relate to " [:em "household waste"] " ."]
     [:p " " [:em "Ranking"] " information that tells you how this region compares to other regions"
      " for a series of metrics that relate to three of the Scottish Government's key aims. These aims are"
      " (1) to reduce the total amount of waste generated in Scotland, (2) to reduce the carbon footprint"
      " of the waste we generate and (3) to increase the fraction of the waste that we generate that goes to"
      " recycling rather than landfill.  The " [:em "Regional Dashboard"] " page shows you rankings for both the"
      " " [:em "trend"] " (how quickly changes are being made) and the " [:em "latest"] " reported figures."]
     
     
     [:div.columns.is-vcentered
      
      [:div.column.is-one-quarter
       [:div {:style {:width "250px"}}
        [region-position/ele 
         "Stirling" 
         household-waste-derivation-generation-positions
         household-waste-derivation-percent-recycled-positions
         household-co2e-derivation-generation-positions]]]
      
      [:div.column
       [:div.content
        [:p "Here is an example for Stirling.  We can see that this region is doing well in terms of the " [:em "latest"] ""
         " figures: these tell us that, in 2019, the amount of waste generated per person was the 14th lowest in"
         " Scotland, the carbon footprint of that waste per person was the 7th lowest in Scotland, and the fraction"
         " of waste being recycled was the 10th highest in Scotland. The rankings for " [:em "trends"] " may not seem"
         " so impressive, but perhaps this is because it is hard to keep improving when you are already doing well."]]]]
     

     [:div.columns.is-vcentered

      [:div.column
       [:div.content
        [:p "To the right of the ranking information, you can see a graph of how the household waste generated per person has"
         " varied in the 2011-2019 period. The data for the region you have selected are indicated by an " [:span {:style {:color "#fdae6b"}} "orange"] " trendline."
         " This graph also includes the overall Scottish average waste generated per person for the same period, shown in"
         " " [:span {:style {:color "#1f77b4"}} "blue"] ". We have also included a " [:span {:style {:color "lightgrey"}} "grey"] " dashed trend line showing what your selected region would need to achieve in order"
         " to meet the Scottish Government's target, assuming that the region reduces its household waste generated per person"
         " by the same amount each year.  There are several things to look out for when reading these graphs. First, look at the"
         " range displayed on the y-axis.  It might look like the data are fluctuating wildly, but if the differences between each"
         " year are small compared to the overall amount, this might not be significant.  For example, if we look at Stirling's"
         " data, at first glance it might be tempting to say there was a surprising increase in 2016. However, the total amount of"
         " waste generated per person changed from 0.433 tonnes in 2014 to 0.472 tonnes in 2016. This is only a 6% increase.  It"
         " may still be worth asking why this increase happened, though!"]]]

      [:div.column.is-one-quarter
       [:div {:style {:width "250px"}}
        [hw-gen/chart
         "Stirling"
         household-waste-derivation-generation]]]]
     
     [:p "Once you have satisfied yourself that any variations between years are significant, it's time to start asking questions" 
      " about why they might have happened. It may be that there was a change in how something was reported, or in a region's waste"
      " collections or waste management contracts.  Or it may be that there was a change in the actual waste being generated. "
      " The next two graphs you see may contain clues to help us understand what is going on. The first of these is the"
      " " [:em "Composition"] " graph."]
     
     [:div.columns.is-vcentered
      
      [:div.column.is-one-quarter
       [:div {:style {:width "250px"}}
        [hw-comp/chart
         "Stirling"
         household-waste-derivation-composition]]]
     
     [:div.column
      [:div.content
       [:p "The " [:em "Composition"] " graph shows us information about the types of waste that make up the household waste stream."
        " The graph takes the form of a stacked bar chart, where the different colours in a single column indicate different categories"
        " of waste.  These are the categroies used by SEPA and by local authorities when they report to SEPA.  If you move your mouse"
        " up and down a column, you will see what the different waste categories are.  Stirloing's figures tell us that the dominant"
        " component of household waste falls into the category " [:em "Household and similar wastes"] ", which is probably not surprising."
        " The second biggest contribution to household waste falls into the category " [:em "Vegetal wastes"] ". It's worth asking just"
        " what this category means and whether it is the same for all regions.  For the period covered by the graph, Stirling operated"
        " separate collections for food and garden wastes.  It may be that its garden waste collections were reported as " [:em "Vegetal"] ""
        " while domestic food waste may have been reported under " [:em "Animal and mixed food wastes"] "."]]]]
     
     [:div.columns.is-vcentered
      
      [:div.column
       [:div.content
        [:p "Next to the " [:em "Composition"] " graph, you can see the " [:em "Management"] " graph, which shows us information about where"
         " our household wastes end up. This is displayed as another stacked bar chart, with just three possible destinations: " [:em "Landfilled"] ","
         " " [:em "Recycled"] " and " [:em "Other diversion"] ". This last category can include more environmentally sustainable destinations such"
         " as biomass energy generation, but it can also include incineration.  The data show that Stirling doesn't send a great deal of its household" 
         " waste to destinations other than landfill or recycling, but we can see that in some years there has been a more singificant amount of"
         " " [:em "Other Diversion"] " than others.  You might want to ask your local authority what this category means for them - and if you live"
         " in Stirling, you might want to ask why there was more of this reported in 2016 than in any other year."]]]
      
      [:div.column.is-one-quarter
       [:div {:style {:width "250px"}}
        [hw-mgmt/chart
         "Stirling"
         household-waste-derivation-management]]]]
     
     ]]]])

(defn root []
  (r/after-render (util/scroll-fn))

  [ele
   @state/household-waste-derivation-generation-cursor
   @state/household-waste-derivation-composition-cursor
   @state/household-waste-derivation-management-cursor
   @state/household-waste-derivation-generation-positions-cursor
   @state/household-waste-derivation-percent-recycled-positions-cursor
   @state/household-co2e-derivation-generation-positions-cursor])
