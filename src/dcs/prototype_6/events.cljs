(ns dcs.prototype-6.events
  (:require [dcs.prototype-6.state :refer [app-state]]))

(defn increment
      [event]
      (.preventDefault event)
      (swap! app-state update-in [:count] inc))

(defn decrement
      [event]
      (.preventDefault event)
      (swap! app-state update-in [:count] dec))

