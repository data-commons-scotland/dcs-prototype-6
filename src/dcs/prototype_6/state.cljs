(ns dcs.prototype-6.state
  (:require [reagent.core :as r]))


(defonce app-state (r/atom {:count 0}))

(defonce route-match (r/atom nil))

(defonce region-holder (r/atom "Please select a region..."))

(defonce geojson-holder (r/atom nil))

(defonce household-waste-derivation-generation-holder (r/atom nil))
(defonce household-waste-derivation-percent-recycled-holder (r/atom nil))
(defonce household-waste-derivation-management-holder (r/atom nil))
(defonce household-waste-derivation-composition-holder (r/atom nil))
(defonce household-waste-derivation-generation-positions-holder (r/atom nil))
(defonce household-waste-derivation-percent-recycled-positions-holder (r/atom nil))

(defonce household-co2e-derivation-generation-holder (r/atom nil))
(defonce household-co2e-derivation-generation-positions-holder (r/atom nil))

(defonce business-waste-by-region-derivation-generation-holder (r/atom nil))
(defonce business-waste-by-region-derivation-composition-holder (r/atom nil))

(defonce waste-site-derivation-holder (r/atom nil))

(defonce stirling-bin-collection-derivation-missed-holder (r/atom nil))


;; -----------------

(defonce population-holder (atom nil))
(defonce household-waste-holder (atom nil))
(defonce household-co2e-holder (atom nil))
(defonce business-waste-by-region-holder (atom nil))
(defonce waste-site-holder (atom nil))
(defonce stirling-bin-collection-holder (atom nil))

