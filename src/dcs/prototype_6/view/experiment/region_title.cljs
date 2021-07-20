(ns dcs.prototype-6.view.experiment.region-title
  (:require [dcs.prototype-6.state :as state]))


(defn toggle-is-activate [id]
  (let [myClass (.-classList (.getElementById js/document (name id)))]
    (-> myClass (.toggle "is-active"))))

(defn dropdown [prompt values]
  [:div#region-dropdown.dropdown
   [:div.dropdown-trigger
    [:button.button {:aria-haspopup true :aria-controls :dropdown-menu
                     :on-click (fn [event]
                                 (.preventDefault event)
                                 (toggle-is-activate :region-dropdown))}
     [:span prompt]
     [:span.icon.is-small
      [:i.fas.fa-angle-down {:aria-hidden true}]]]]
   [:div.dropdown-menu {:id :dropdown-menu :role :menu}
    [:div.dropdown-content
     (sort-by (fn [item]
                (:name (second item)))
              (map (fn [[m-code m-name]]
                     [:a.dropdown-item {:name m-name
                                        :key m-code
                                        :on-click (fn [event]
                                                    (.preventDefault event)
                                                    (toggle-is-activate :region-dropdown)
                                                    (js/console.log (str "Do something with: " m-code ", " m-name))
                                                    (js/console.log (str "before: " @state/region-cursor))
                                                    (reset! state/region-cursor m-name)
                                                    (js/console.log (str "after: " @state/region-cursor)))}
                      m-name])
                   values))]]])


(defn root []
  (let [region @state/region-cursor]
    [:div.has-text-centered
     [:h1.title region]
     [dropdown region [["c1" "Stirling"]
                       ["c2" "Moray"]
                       ["c3" "Highland"]]]]))