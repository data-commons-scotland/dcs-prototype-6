(ns dcs.prototype-6.loader
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [dcs.prototype-6.util :as util]
            [dcs.prototype-6.state :as state]
            [dcs.prototype-6.data-shaping :as data-shaping])
  (:require-macros [cljs.core.async.macros :refer [go]]))


(defn fetch
      [url body-handler]
      (js/console.log (str "Fetching " url))
      (go (let [start-time (util/now)
                response (<! (http/get url {:with-credentials? false}))]
               (do
                 (js/console.log (str "Response from " url ": status=" (:status response) " success=" (:success response) " secs-taken=" (util/secs-to-now start-time)))
                 (let [body (:body response)
                       clj-body (if (string? body)
                                  (js->clj (.parse js/JSON body) :keywordize-keys true) ;; probably a text/plain response that we'll have to explicitly convert to Clojure data
                                  body)] ;; probably an application/json response causing cljs-http to have aleady auto converted the JSON to Clojure data
                      (body-handler clj-body))))))

(defn load-data
      []
      (js/console.log "Loading data files")

      (fetch "stirling-community-food-tonnes.json"
             (fn [stirling-community-food-tonnes] (->> stirling-community-food-tonnes
                                                       (reset! state/stirling-community-food-tonnes-holder))))

      (fetch "stirling-community-food-footfall.json"
             (fn [stirling-community-food-footfall] (->> stirling-community-food-footfall
                                                         (reset! state/stirling-community-food-footfall-holder))))

      (fetch "fairshare-donations.json"
             (fn [fairshare-donations] (->> fairshare-donations
                                            (reset! state/fairshare-donations-holder))))

      (fetch "fairshare-co2e.json"
             (fn [fairshare-co2e] (->> fairshare-co2e
                                            (reset! state/fairshare-co2e-holder))))

      (fetch "household-waste-analysis.json"
             (fn [household-waste-analysis] (->> household-waste-analysis
                                                 (reset! state/household-waste-analysis-holder))))

      (fetch "geojson.json"
             (fn [geojson] (->> geojson
                                clj->js
                                (reset! state/geojson-cursor))))

      (fetch (str util/easier-repo-data "population.json")
             (fn [population] (->> population
                                   data-shaping/rollup-population-regions
                                   (concat population)
                                   (reset! state/population-holder))))

      (fetch (str util/easier-repo-data "household-waste.json")
             (fn [household-waste] (->> household-waste
                                        data-shaping/rollup-household-waste-regions
                                        (concat household-waste)
                                        (reset! state/household-waste-holder))))

      (fetch (str util/easier-repo-data "household-co2e.json")
             (fn [household-co2e] (->> household-co2e
                                       data-shaping/rollup-household-co2e-regions
                                       (concat household-co2e)
                                       (reset! state/household-co2e-holder))))

      (fetch (str util/easier-repo-data "business-waste-by-region.json")
             (fn [business-waste-by-region] (->> business-waste-by-region
                                                 data-shaping/rollup-business-waste-by-region-regions
                                                 (concat business-waste-by-region)
                                                 (reset! state/business-waste-by-region-holder))))

      (fetch (str util/easier-repo-data "waste-site.json")
             (fn [waste-site] (->> waste-site
                                   (reset! state/waste-site-holder))))

      (fetch (str util/easier-repo-data "stirling-bin-collection.json")
             (fn [stirling-bin-collection] (->> stirling-bin-collection
                                                (reset! state/stirling-bin-collection-holder)))))
