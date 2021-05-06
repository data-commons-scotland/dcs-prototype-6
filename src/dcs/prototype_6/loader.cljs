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
                response (<! (http/get url))]
               (do
                 (js/console.log (str "Response from " url ": status=" (:status response) " success=" (:success response) " secs-taken=" (util/secs-to-now start-time)))
                 (body-handler (:body response))))))

(defn load-data
      []
      (js/console.log "Loading data files")

      (fetch "stirling-community-food-tonnes.json"
             (fn [stirling-community-food-tonnes] (->> stirling-community-food-tonnes
                                                       (reset! state/stirling-community-food-tonnes-holder))))

      (fetch "stirling-community-food-footfall.json"
             (fn [stirling-community-food-footfall] (->> stirling-community-food-footfall
                                                         (reset! state/stirling-community-food-footfall-holder))))

      (fetch "geojson.json"
             (fn [geojson] (->> geojson
                                clj->js
                                (reset! state/geojson-cursor))))

      (fetch "population.json"
             (fn [population] (->> population
                                   data-shaping/rollup-population-regions
                                   (concat population)
                                   (reset! state/population-holder))))

      (fetch "household-waste.json"
             (fn [household-waste] (->> household-waste
                                        data-shaping/rollup-household-waste-regions
                                        (concat household-waste)
                                        (reset! state/household-waste-holder))))

      (fetch "household-co2e.json"
             (fn [household-co2e] (->> household-co2e
                                       data-shaping/rollup-household-co2e-regions
                                       (concat household-co2e)
                                       (reset! state/household-co2e-holder))))

      (fetch "business-waste-by-region.json"
             (fn [business-waste-by-region] (->> business-waste-by-region
                                                 data-shaping/rollup-business-waste-by-region-regions
                                                 (concat business-waste-by-region)
                                                 (reset! state/business-waste-by-region-holder))))

      (fetch "waste-site.json"
             (fn [waste-site] (->> waste-site
                                   (reset! state/waste-site-holder))))

      (fetch "stirling-bin-collection.json"
             (fn [stirling-bin-collection] (->> stirling-bin-collection
                                                (reset! state/stirling-bin-collection-holder)))))
