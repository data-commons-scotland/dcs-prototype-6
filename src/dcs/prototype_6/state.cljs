(ns dcs.prototype-6.state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {:count 0}))
