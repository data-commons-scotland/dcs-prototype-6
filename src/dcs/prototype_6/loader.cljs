(ns dcs.prototype-6.loader
  (:require [clojure.string :as str]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [testdouble.cljs.csv :as csv]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.data-shaping :as data-shaping])
  (:require-macros [cljs.core.async.macros :refer [go]]))


(defn fetch
      [url body-handler]
      (js/console.log (str "Fetching " url))
      (go (let [start-time (util/now)
                response (<! (http/get url {:with-credentials? false}))]
                 (js/console.log (str "Response from " url ": status=" (:status response) " success=" (:success response) " secs-taken=" (util/secs-to-now start-time)))
                 (let [body (:body response)
                       clj-body (cond
                                  (str/ends-with? url ".csv") (csv/read-csv (str/replace body "\r" "")) ;; parse as CSV (and remove \r chars since they're troublesome with this CSV lib in this context)
                                  (string? body) (js->clj (.parse js/JSON body) :keywordize-keys true) ;; probably a text/plain response that we'll have to explicitly convert to Clojure data
                                  :else body ;; probably an application/json response causing cljs-http to have aleady auto converted the JSON to Clojure data
                                  )] 
                      (body-handler clj-body)))))

(defn load-data
  []
  (js/console.log "Loading data files")
  
  (fetch "ace-furniture-count.json"
         (fn [ace-furniture-count] (->> ace-furniture-count
                                        (reset! state/ace-furniture-count-holder))))

  (fetch "ace-furniture-avg-weight.json"
         (fn [ace-furniture-avg-weight] (->> ace-furniture-avg-weight
                                             (reset! state/ace-furniture-avg-weight-holder))))
  
  (fetch "geojson.json"
         (fn [geojson] (->> geojson
                            clj->js
                            (reset! state/geojson-cursor))))
  
  (fetch "regional-dashboard-annotations.csv"
         (fn [regional-dashboard-annotations] (->> regional-dashboard-annotations
                                                   (reset! state/regional-dashboard-annotations-holder))))
  
  (fetch "household-waste-analysis-annotations.csv"
         (fn [household-waste-analysis-annotations] (->> household-waste-analysis-annotations
                                                         (reset! state/household-waste-analysis-annotations-holder))))
  
  (fetch "fairshare-annotations.csv"
         (fn [fairshare-annotations] (->> fairshare-annotations
                                          (reset! state/fairshare-annotations-holder))))

  (fetch (str util/easier-repo-data "meta.json")
         (fn [meta] (->> meta
                         (reset! state/meta-holder))))


  (fetch (str util/easier-repo-data "co2e-multiplier.json") 
         (fn [co2e-multiplier] (->> co2e-multiplier
                                    (reset! state/co2e-multiplier-holder))))
  
  (fetch (str util/easier-repo-data "population.json")
         (fn [population] (let [population-scotland      (data-shaping/rollup-population-regions population)
                                ;; fabricate records that will be used to calculate "Scottish government target" values
                                ;; (fabricated records will be the same as the records for "Scotland", just with a different :region value)
                                population-scotGovTarget (map #(assoc % :region "Scot gov target") population-scotland)]
                            (reset! state/population-holder
                                    (concat population
                                            population-scotland
                                            population-scotGovTarget)))))

  (fetch (str util/easier-repo-data "household-waste.json")
         (fn [household-waste] (let [household-waste-scotland      (data-shaping/rollup-household-waste-regions household-waste)
                                     household-waste-scotGovTarget (data-shaping/calc-scotGovTarget-for-household-waste household-waste-scotland)]
                                 (reset! state/household-waste-holder
                                         (concat household-waste 
                                                 household-waste-scotland
                                                 household-waste-scotGovTarget)))))

  (fetch (str util/easier-repo-data "household-co2e.json")
         (fn [household-co2e] (->> household-co2e
                                   data-shaping/rollup-household-co2e-regions
                                   (concat household-co2e)
                                   (reset! state/household-co2e-holder))))

  (fetch (str util/easier-repo-data "business-waste-by-region.json")
         (fn [business-waste-by-region] (let [business-waste-by-region-scotland      (data-shaping/rollup-business-waste-by-region-regions business-waste-by-region)
                                              business-waste-by-region-scotGovTarget (data-shaping/calc-scotGovTarget-for-business-waste-by-region business-waste-by-region-scotland)]
                                          (reset! state/business-waste-by-region-holder
                                                  (concat business-waste-by-region
                                                          business-waste-by-region-scotland
                                                          business-waste-by-region-scotGovTarget)))))

  (fetch (str util/easier-repo-data "waste-site-io.json")
         (fn [waste-site] (->> waste-site
                               (reset! state/waste-site-holder))))

  (fetch (str util/easier-repo-data "bin-collection.json")
         (fn [stirling-bin-collection] (->> stirling-bin-collection
                                            (reset! state/stirling-bin-collection-holder))))
  
  (fetch (str util/easier-repo-data "fairshare.json")
         (fn [fairshare] (->> fairshare
                              (reset! state/fairshare-holder))))
  
  (fetch (str util/easier-repo-data "stirling-community-food-tonnes.json")
         (fn [stirling-community-food-tonnes] (->> stirling-community-food-tonnes
                                                   (reset! state/stirling-community-food-tonnes-holder))))

  (fetch (str util/easier-repo-data "stirling-community-food-footfall.json")
         (fn [stirling-community-food-footfall] (->> stirling-community-food-footfall
                                                     (reset! state/stirling-community-food-footfall-holder))))

  (fetch (str util/easier-repo-data "ace-furniture-to-scottishCarbonMetric.json")
         (fn [ace-furniture-to-scottishCarbonMetric] (->> ace-furniture-to-scottishCarbonMetric
                                                          (reset! state/ace-furniture-to-scottishCarbonMetric-holder))))

  (fetch (str util/easier-repo-data "household-waste-analysis.json")
         (fn [household-waste-analysis] (->> household-waste-analysis
                                             (reset! state/household-waste-analysis-holder)))))
