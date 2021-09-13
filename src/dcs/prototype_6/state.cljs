(ns dcs.prototype-6.state
  (:require [reagent.core :as r]))


(defonce root (r/atom {:route-match                                 nil
                       :geojson                                     nil
                       :region                                      "Please select a region..."
                       :meta-derivation                             nil
                       :household-waste-derivation                  {:generation                 nil
                                                                     :percent-recycled           nil
                                                                     :management                 nil
                                                                     :composition                nil
                                                                     :generation-positions       nil
                                                                     :percent-recycled-positions nil}
                       :household-co2e-derivation                   {:generation           nil
                                                                     :generation-positions nil}
                       :business-waste-by-region-derivation         {:generation  nil
                                                                     :composition nil}
                       :waste-site-derivation                       nil
                       :stirling-bin-collection-derivation          {:generation       nil
                                                                     :percent-recycled nil
                                                                     :composition      nil
                                                                     :missed-bins      nil}
                       :stirling-community-food-tonnes-derivation   {:tonnes nil
                                                                     :flow   nil}
                       :stirling-community-food-footfall-derivation nil
                       :fairshare-derivation                        {:material nil
                                                                     :co2e     nil}
                       :ace-furniture-counts-derivation             {:orig            nil
                                                                     :category-trends nil
                                                                     :item-trends     nil}
                       :ace-furniture-weights-derivation            {:orig          nil
                                                                     :flights-worth {:per-category nil
                                                                                     :per-item     nil}}}))

(defonce route-match-cursor (r/cursor root [:route-match]))

(defonce geojson-cursor (r/cursor root [:geojson]))

(defonce region-cursor (r/cursor root [:region]))

(defonce meta-derivation-cursor (r/cursor root [:meta-derivation]))

(defonce household-waste-derivation-generation-cursor (r/cursor root [:household-waste-derivation :generation]))
(defonce household-waste-derivation-percent-recycled-cursor (r/cursor root [:household-waste-derivation :percent-recycled]))
(defonce household-waste-derivation-management-cursor (r/cursor root [:household-waste-derivation :management]))
(defonce household-waste-derivation-composition-cursor (r/cursor root [:household-waste-derivation :composition]))
(defonce household-waste-derivation-generation-positions-cursor (r/cursor root [:household-waste-derivation :generation-positions]))
(defonce household-waste-derivation-percent-recycled-positions-cursor (r/cursor root [:household-waste-derivation :percent-recycled-positions]))

(defonce household-co2e-derivation-generation-cursor (r/cursor root [:household-co2e-derivation :generation]))
(defonce household-co2e-derivation-generation-positions-cursor (r/cursor root [:household-co2e-derivation :generation-positions]))

(defonce business-waste-by-region-derivation-generation-cursor (r/cursor root [:business-waste-by-region-derivation :generation]))
(defonce business-waste-by-region-derivation-composition-cursor (r/cursor root [:business-waste-by-region-derivation :composition]))

(defonce waste-site-derivation-cursor (r/cursor root [:waste-site-derivation]))

(defonce stirling-bin-collection-derivation-generation-cursor (r/cursor root [:stirling-bin-collection-derivation :generation]))
(defonce stirling-bin-collection-derivation-percent-recycled-cursor (r/cursor root [:stirling-bin-collection-derivation :percent-recycled]))
(defonce stirling-bin-collection-derivation-composition-cursor (r/cursor root [:stirling-bin-collection-derivation :composition]))
(defonce stirling-bin-collection-derivation-missed-bins-cursor (r/cursor root [:stirling-bin-collection-derivation :missed-bins]))

(defonce stirling-community-food-tonnes-derivation-tonnes-cursor (r/cursor root [:stirling-community-food-tonnes-derivation :tonnes]))
(defonce stirling-community-food-tonnes-derivation-flow-cursor (r/cursor root [:stirling-community-food-tonnes-derivation :flow]))
(defonce stirling-community-food-footfall-derivation-cursor (r/cursor root [:stirling-community-food-footfall-derivation]))

(defonce fairshare-derivation-material-cursor (r/cursor root [:fairshare-derivation :material]))
(defonce fairshare-derivation-co2e-cursor (r/cursor root [:fairshare-derivation :co2e]))

(defonce ace-furniture-counts-derivation-orig-cursor (r/cursor root [:ace-furniture-counts-derivation :orig]))
(defonce ace-furniture-counts-derivation-category-trends-cursor (r/cursor root [:ace-furniture-counts-derivation :category-trends]))
(defonce ace-furniture-counts-derivation-item-trends-cursor (r/cursor root [:ace-furniture-counts-derivation :item-trends]))
(defonce ace-furniture-weights-derivation-orig-cursor (r/cursor root [:ace-furniture-weights-derivation :orig]))
(defonce ace-furniture-weights-derivation-flights-per-category-cursor (r/cursor root [:ace-furniture-weights-derivation :flights-worth :per-category]))
(defonce ace-furniture-weights-derivation-flights-per-item-cursor (r/cursor root [:ace-furniture-weights-derivation :flights-worth :per-item]))

(defonce household-waste-analysis-derivation-cursor (r/cursor root [:household-waste-analysis-derivation]))

;; -----------------

(defonce meta-holder (atom nil))
(defonce population-holder (atom nil))
(defonce co2e-multiplier-holder (atom nil))
(defonce household-waste-holder (atom nil))
(defonce household-co2e-holder (atom nil))
(defonce business-waste-by-region-holder (atom nil))
(defonce waste-site-holder (atom nil))
(defonce stirling-bin-collection-holder (atom nil))
(defonce stirling-community-food-tonnes-holder (atom nil))
(defonce stirling-community-food-footfall-holder (atom nil))
(defonce fairshare-holder (atom nil))
(defonce ace-furniture-counts-holder (atom nil))
(defonce ace-furniture-weights-holder (atom nil))
(defonce ace-furniture-to-waste-streams-holder (atom nil))
(defonce household-waste-analysis-holder (atom nil))
