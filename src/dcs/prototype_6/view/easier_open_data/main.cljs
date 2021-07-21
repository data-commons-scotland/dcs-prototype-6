(ns dcs.prototype-6.view.easier-open-data.main
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [dcs.prototype-6.util :as util]))

(defn dataset-row
      [dataset-ele-id dataset description csv json number creator supplier supplier-url licence-url licence]
      [:tr {:id dataset-ele-id}
       [:td dataset]
       [:td description]
       [:td [:a {:href (str util/easier-repo-data csv)
                 :target "_blank"} "CSV"] "  " (if (nil? json)
                                                             ""
                                                             [:a {:href (str util/easier-repo-data json) :target "_blank"} "JSON"])]
       [:td number]
       [:td creator]
       [:td supplier [:sup [:a {:href supplier-url :target "_blank"} (gstring/unescapeEntities "&nbsp;") "URL"]]]
       [:td [:a {:href licence-url :target "_blank"} licence]]])

(defn scroll-fn
      [id]
      (fn []
          (let [ele (.getElementById js/document id)]
            (.scrollIntoView ele true)
            (.add (.-classList ele) "is-selected")
            ;; allow for the navbar area when scrolling-into-view a specific element
            (when-let [scrolledY (.-scrollY js/window)]
                      (.scroll js/window 0 (- scrolledY 50))))))

(defn dimension-row
      ([dataset-ele-id dataset example count min max]
       [:tr
        [:td [:a {:href "javascript:;" :on-click (scroll-fn dataset-ele-id)} dataset]]
        [:td example]
        [:td count]
        [:td min]
        [:td max]])
      ([dimension-row-span dimension description-row-span description dataset-ele-id dataset example count min max]
       (let [basic-row (dimension-row dataset-ele-id dataset example count min max)]
            (into [] (concat [(first basic-row)
                              [:td {:row-span dimension-row-span} dimension]
                              [:td {:row-span description-row-span} description]]
                             (rest basic-row))))))

(defn root
  [route]
  (let [target (some-> route :parameters :query :target)]
           ;(js/console.log (str "target=" target))
    (when target (r/after-render (scroll-fn target)))

    [:div

     [:section.hero
      [:div.hero-body

       [:div.content.has-text-centered
        [:h1.title.is-5 "About the data on this site"]]

       [:div.container
        [:div.content
         [:h2.subtitle.is-4.has-text-danger [:span [:em "Easier"] " open data about waste in Scotland"]]
         [:p "Several organisations are doing a very good job of curating & publishing "
          [:b "open data"] " about waste in Scotland but,
          the published data is not always \"easy to use\" for non-experts.
          We have see several references to this at open data conference events and on social media platforms:"]
         [:blockquote "Whilst statisticians/coders may think that it is reasonably simple to knead together these
          somewhat diverse datasets into a coherent knowledge, the interested layman doesn't find it so easy."]
         [:p "One of the objectives of the Data Commons Scotland project is to address
          the \"ease of use\" issue over open data.
          The contents of this repository are the result of us " [:em "re-working"] " some of the
          existing " [:em "source"] " open data
          so that it is " [:b [:em "easier"]] " to use, understand, consume, parse, and all in one place.
          It may not be as detailed or have all the nuances as the source data - but aims to be
          better for the purposes of making the information accessible to non-experts."]
         [:p "We have processed the source data just enough to:"]
         [:ul
          [:li "provide value-based cross-referencing between datasets"]
          [:li "add a few fields whose values are generally useful but not easily derivable by a
           simple calculation (such as latitude & longitude)"]
          [:li "make it available as simple CSV and JSON files in a Git repository."]]
         [:p "We have not augmented the data with derived values that can be simply calculated,
          such as per-population amounts, averages, trends, totals, etc."]]]]]

     [:section.hero {:style {:backgroundColor "#fff1e5"}}
      [:div.hero-body
       [:div.container
        [:div.content
         [:h2.subtitle.is-4.has-text-danger [:span "The 12 " [:em "easier"] " datasets"]]
         [:table#easier-table-fff1e5.table.is-hoverable.is-narrow
          [:thead
           [:tr.has-text-left
            [:th {:col-span 4} [:span "dataset" (gstring/unescapeEntities "&nbsp;") [:sup.has-text-info "(generated February 2021)"]]]
            [:th {:col-span 3} [:span "data source" (gstring/unescapeEntities "&nbsp;") [:sup.has-text-info "(sourced January 2021)"]]]]
           [:tr.has-text-left
            [:th.has-text-danger "name"]
            [:th.has-text-danger "description"]
            [:th.has-text-danger "file"]
            [:th.has-text-danger "number of records"]
            [:th.has-text-danger "creator"]
            [:th.has-text-danger "supplier"]
            [:th.has-text-danger "licence"]]]

          [:tbody

           (dataset-row "household-waste" "household-waste" "The categorised quantities of the ('managed') waste generated by households." "household-waste.csv" "household-waste.json" "19008" "SEPA" "statistics.gov.scot" "http://statistics.gov.scot/data/household-waste" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" "OGL v3.0")

           (dataset-row "household-co2e" "household-co2e" "The carbon impact of the waste generated by households." "household-co2e.csv" "household-co2e.json" "288" "SEPA" "SEPA" "https://www.environment.gov.scot/data/data-analysis/household-waste" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "business-waste-by-region" "business-waste-by-region" "The categorised quantities of the waste generated by industry & commerce." "business-waste-by-region.csv" "business-waste-by-region.json" "8976" "SEPA" "SEPA" "https://www.sepa.org.uk/environment/waste/waste-data/waste-data-reporting/business-waste-data" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "business-waste-by-sector" "business-waste-by-sector" "The categorised quantities of the waste generated by industry & commerce." "business-waste-by-sector.csv" "business-waste-by-sector.json" "2640" "SEPA" "SEPA" "https://www.sepa.org.uk/environment/waste/waste-data/waste-data-reporting/business-waste-data" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "waste-site" "waste-site" "The locations, services & capacities of waste sites." "waste-site.csv" "waste-site.json" "1254" "SEPA" "SEPA" "https://www.sepa.org.uk/data-visualisation/waste-sites-and-capacity-tool" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "waste-site-io" "waste-site-io" "The categorised quantities of waste going in and out of waste sites." "waste-site-io.csv" nil "2667914" "SEPA" "SEPA" "https://www.sepa.org.uk/data-visualisation/waste-sites-and-capacity-tool" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "stirling-bin-collection" "stirling-bin-collection" "The categorised quantities of the waste collected from household bins." "stirling-bin-collection.csv" "stirling-bin-collection.json" "136" "Stirling council" "Stirling council" "https://data.stirling.gov.uk/dataset/waste-management" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" "OGL v3.0")
           
           (dataset-row "material-coding" "material-coding" "A mapping between the EWC codes and SEPA's materials classification (as used in these datasets)." "material-coding.csv" "material-coding.json" "557" "SEPA" "SEPA" "https://www.sepa.org.uk/data-visualisation/waste-sites-and-capacity-tool" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/" "OGL v2.0")

           (dataset-row "ewc-coding" "ewc-coding" "EWC (European Waste Classification) codes and descriptions." "ewc-coding.csv" "ewc-coding.json" "973" "European Commission of the EU" "Publications Office of the EU" "https://eur-lex.europa.eu/legal-content/EN/TXT/HTML/?uri=CELEX:02000D0532-20150601&from=EN#tocId7" "https://creativecommons.org/licenses/by/4.0/" "CC BY 4.0")

           (dataset-row "co2e-multiplier" "co2e-multiplier" "Per-material weight-multipliers to calaculate CO2e amounts. This data has been copied from section 6.2 of The Scottish Carbon Metric." "co2e-multiplier.csv" "co2e-multiplier.json" "37" "Zero Waste Scotland" "Zero Waste Scotland" "https://www.zerowastescotland.org.uk/sites/default/files/The%20Scottish%20Carbon%20Metric.pdf" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" "OGL v3.0")

           (dataset-row "households" "households" "Occupied residential dwelling counts. Useful for calculating per-household amounts." "households.csv" "households.json" "288" "NRS" "statistics.gov.scot" "http://statistics.gov.scot/data/household-estimates" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" "OGL v3.0")

           (dataset-row "population" "population" "People counts. Useful for calculating per-citizen amounts." "population.csv" "population.json" "288" "NRS" "statistics.gov.scot" "http://statistics.gov.scot/data/population-estimates-current-geographic-boundaries" "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" "OGL v3.0")

           ]]
         [:p.is-size-7 "(The fuller, " [:a {:href (str util/easier-repo-metadata "datasets.csv")} "CSV version of the table"] " above.)"]
         ]]
       ]
      ]

     [:section.hero {:style {:backgroundColor "#f2dfce"}}
      [:div.hero-body
       [:div.container
        [:div.content
         [:h2.subtitle.is-4.has-text-success [:span "The dimensions of the " [:em "easier"] " datasets"]]
         [:p "One of the things that makes these datasets " [:em "easier"] " to use
           is that they use consistent dimension values/controlled code-lists.
           This makes it easier to join/link datasets."]
         [:p "We have tried to rectify the inconsistencies that occur in the source data;
           in particular, the inconsistent labelling of waste materials and regions.
           This is still \"work-in-progress\"
           because we have yet to tease out and make consistent further useful dimensions."]
         [:table#easier-table-f2dfce.table.is-hoverable.is-narrow
          [:thead
           [:tr.has-text-left
            [:th.has-text-danger "dimension"]
            [:th.has-text-danger "description"]
            [:th.has-text-danger "dataset"]
            [:th.has-text-danger "example value of dimension"]
            [:th.has-text-danger "count of values of dimension"]
            [:th.has-text-danger "min value of dimension"]
            [:th.has-text-danger "max value of dimension"]]]
          [:tbody

           (dimension-row "7" "region" "7" "The name of a council area." "household-waste" "household-waste" "Falkirk" "32" "" "")

           (dimension-row "household-co2e" "household-co2e" "Aberdeen City" "32" "" "")

           (dimension-row "business-waste-by-region" "business-waste-by-region" "Falkirk" "34" "" "")

           (dimension-row "waste-site" "waste-site" "North Lanarkshire" "32" "" "")

           (dimension-row "stirling-bin-collection" "stirling-bin-collection" "Stirling" "1" "" "")

           (dimension-row "households" "households" "West Dunbartonshire" "32" "" "")

           (dimension-row "population" "population" "West Dunbartonshire" "32" "" "")

           (dimension-row "1" "business-sector" "1" "The label representing the business/economic sector." "business-waste-by-sector" "business-waste-by-sector" "Water industry" "10" "" "")

           (dimension-row "9" "year" "9" "The integer representation of a year." "household-waste" "household-waste" "2011" "9" "2011" "2019")

           (dimension-row "household-co2e" "household-co2e" "2013" "9" "2011" "2019")

           (dimension-row "business-waste-by-region" "business-waste-by-region" "2018" "8" "2011" "2018")

           (dimension-row "business-waste-by-sector" "business-waste-by-sector" "2018" "8" "2011" "2018")

           (dimension-row "waste-site" "waste-site" "2019" "1" "2019" "2019")

           (dimension-row "waste-site-io" "waste-site-io" "2013" "14" "2007" "2020")

           (dimension-row "stirling-bin-collection" "stirling-bin-collection" "2020" "4" "2018" "2021")

           (dimension-row "households" "households" "2011" "9" "2011" "2019")

           (dimension-row "population" "population" "2013" "9" "2011" "2019")

           (dimension-row "2" "quarter" "2" "The integer representation of the year's quarter." "waste-site-io" "waste-site-io" "4" "4" "" "")

           (dimension-row "stirling-bin-collection" "stirling-bin-collection" "2" "4" "" "")

           (dimension-row "1" "site-name" "1" "The name of the waste site." "waste-site" "waste-site" "Bellshill H/care Waste Treatment & Transfer" "1246" "" "")

           (dimension-row "2" "permit" "2" "The waste site operator's official permit or licence." "waste-site" "waste-site" "PPC/A/1180708" "1254" "" "")

           (dimension-row "waste-site-io" "waste-site-io" "PPC/A/1000060" "1401" "" "")

           (dimension-row "1" "status" "1" "The label indicating the open/closed status of the waste site in the record's timeframe. " "waste-site" "waste-site" "Not applicable" "4" "" "")

           (dimension-row "1" "latitude" "1" "The signed decimal representing a latitude." "waste-site" "waste-site" "55.824871489601804" "1227" "" "")

           (dimension-row "1" "longitude" "1" "The signed decimal representing a longitude." "waste-site" "waste-site" "-4.035165962797409" "1227" "" "")

           (dimension-row "1" "io-direction" "1" "The label indicating the direction of travel of the waste from the PoV of a waste site." "waste-site-io" "waste-site-io" "in" "2" "" "")

           (dimension-row "5" "material" "5" "The name of a waste material in SEPA's classification." "household-waste" "household-waste" "Animal and mixed food waste" "22" "" "")

           (dimension-row "business-waste-by-region" "business-waste-by-region" "Spent solvents" "33" "" "")

           (dimension-row "business-waste-by-sector" "business-waste-by-sector" "Spent solvents" "33" "" "")

           (dimension-row "stirling-bin-collection" "stirling-bin-collection" "Household and similar wastes" "6" "" "")

           (dimension-row "material-coding" "material-coding" "Acid, alkaline or saline wastes" "34" "" "")

           (dimension-row "1" "management" "1" "The label indicating how the waste was managed/processed (i.e. what its end-state was)." "household-waste" "household-waste" "Other Diversion" "3" "" "")

           (dimension-row "1" "recycling?" "1" "True if the waste was categorised as 'for recycling' when collected." "stirling-bin-collection" "stirling-bin-collection" "false" "2" "" "")

           (dimension-row "1" "missed-bin?" "1" "True if the waste was in a missed bin." "stirling-bin-collection" "stirling-bin-collection" "true" "2" "" "")

           (dimension-row "3" "ewc-code" "3" "The code from the European Waste Classification hierarchy." "waste-site-io" "waste-site-io" "00 00 00" "787" "" "")

           (dimension-row "material-coding" "material-coding" "11 01 06*" "557" "" "")

           (dimension-row "ewc-coding" "ewc-coding" "01" "973" "" "")

           (dimension-row "1" "ewc-description" "1" "The description from the European Waste Classification hierarchy." "ewc-coding" "ewc-coding" "WASTES RESULTING FROM EXPLORATION, MINING, QUARRYING, AND PHYSICAL AND CHEMICAL TREATMENT OF MINERALS" "774" "" "")

           (dimension-row "1" "waste-stream" "1" "The name of a waste stream in ZWS' classification." "co2e-multiplier" "co2e-multiplier" "Textiles" "37" "" "")

           (dimension-row "2" "operator" "2" "The name of the waste site operator." "waste-site" "waste-site" "TRADEBE UK" "753" "" "")

           (dimension-row "waste-site-io" "waste-site-io" "ABERDEENSHIRE COUNCIL" "1003" "" "")

           (dimension-row "1" "activities" "1" "The waste processing activities supported by the waste site." "waste-site" "waste-site" "Other treatment" "50" "" "")

           (dimension-row "1" "accepts" "1" "The kinds of clients/wastes accepted by the waste site." "waste-site" "waste-site" "Other special" "42" "" "")

           (dimension-row "1" "multiplier" "1" "The value to multiply a weight by to calculate the C02e amount." "co2e-multiplier" "co2e-multiplier" "100.00" "36" "0.03" "100.00")

           (dimension-row "1" "population" "1" "The population count as an integer." "population" "population" "89800" "" "21420" "633120")

           (dimension-row "1" "households" "1" "The households count as an integer." "households" "households" "42962" "" "9424" "307161")

           (dimension-row "6" "tonnes" "6" "The waste related quantity as a decimal." "household-waste" "household-waste" "0" "" "0" "183691")

           (dimension-row "household-co2e" "household-co2e" "251386.54" "" "24768.53" "762399.92")

           (dimension-row "business-waste-by-region" "business-waste-by-region" "488" "" "0" "486432")

           (dimension-row "business-waste-by-sector" "business-waste-by-sector" "6" "" "0" "1039179")

           (dimension-row "waste-site-io" "waste-site-io" "0" "" "-8.56" "2325652.83")

           (dimension-row "stirling-bin-collection" "stirling-bin-collection" "60.42" "" "0.3" "5447.70")

           (dimension-row "1" "tonnes-input" "1" "The quantity of incoming waste as a decimal." "waste-site" "waste-site" "154.55" "" "0" "1476044")

           (dimension-row "1" "tonnes-treated-recovered" "1" "The quantity of waste treated or recovered as a decimal." "waste-site" "waste-site" "133.04" "" "0" "1476044")

           (dimension-row "1" "tonnes-output" "1" "The quantity of outgoing waste as a decimal." "waste-site" "waste-site" "152.8" "" "0" "235354.51")
           
           ]]
         [:p.is-size-7 "(The " [:a {:href (str util/easier-repo-metadata "dimensions.csv")} "CSV version of the table"] " above.)"]
         ]
        ]]
      ]

     ]))





