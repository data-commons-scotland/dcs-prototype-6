(ns dcs.prototype-6.view.ace-furniture.co2e
  (:require
    [reitit.frontend.easy :as rfe]
    [oz.core :as oz]
    [dcs.prototype-6.util :as util]
    [dcs.prototype-6.state :as state]))


(defn chart-spec-category-level
      [data]
       {:schema     "https://vega.github.io/schema/vega/v5.json"
        :width      500
        :height     400
        :background "#f2dfce"                                ;  "#980f3d"  "#fff1e5"
        :data       {:values data}
        :transform  [{:calculate "datum.flight==1 ? '✈️' : ''" :as "emoji"}
                     {:window  [{:op "sum" :field "flight" :as "flights"}]
                      :groupby ["category"]}]
        :mark       {:type  "text"
                     :align "left"}
        :encoding   {:y       {:field "category" :type "nominal"
                               :sort {:field "co2e" :order "descending"}}
                     :x       {:field "flights"
                               :type  "quantitative"
                               :axis  {:title "equivalent number of flights"}}
                     :text    {:field "emoji" :type "nominal"}
                     :size    {:value 15}
                     :tooltip [{:field "category" :type "nominal"}
                               {:title "kg of CO2e" :field "co2e" :type "quantitative"}
                               {:title "equivalent number of flights" :field "flights-total" :type "quantitative"}]}
        :config     {:axisX {:grid false}}})


(defn chart-spec-item-level
      [data]
      {:schema     "https://vega.github.io/schema/vega/v5.json"
       :width      500
       :height     1500
       :background "#f2dfce"                                ;  "#980f3d"  "#fff1e5"
       :data       {:values data}
       :transform  [{:calculate "datum.flight==1 ? '✈️' : ''" :as "emoji"}
                    {:window  [{:op "sum" :field "flight" :as "flights"}]
                     :groupby ["category" "item"]}]
       :mark       {:type  "text"
                    :align "left"}
       :encoding   {:y       {:field "item" :type "nominal"
                              :sort {:field "co2e" :order "descending"}}
                    :x       {:field "flights"
                              :type  "quantitative"
                              :axis  {:title "equivalent number of flights"}}
                    :text    {:field "emoji" :type "nominal"}
                    :size    {:value 15}
                    :tooltip [{:field "category" :type "nominal"}
                              {:field "item" :type "nominal"}
                              {:title "kg of CO2e" :field "co2e" :type "quantitative"}
                              {:title "equivalent number of flights" :field "flights-total" :type "quantitative"}]}
       :config     {:axisX {:grid false}}})

(def hint [:div.content.is-small.has-text-info-dark
           [:p "Click on a bar in the graph to see the item category, the exact CO2e amount and flights count."]])

(defn tabbed-area 
  [flights-per-category flights-per-item]
  [:div
   [:div.tabs
    [:ul
     [:li.tab-co2e-since-inception.is-active {:on-click (fn [e] (util/open-tab e "co2e-since-inception" "category-level"))} [:a "Less detail"]]
     [:li.tab-co2e-since-inception {:on-click (fn [e] (util/open-tab e "co2e-since-inception" "item-level"))} [:a "More detail"]]]]

   [:div#category-level-co2e-since-inception.content.tab-content-co2e-since-inception
    [:p "The " [:b "total CO" [:sub "2"] "e"] " (in terms of flights) of the items of furniture sold (Mar 2017-Aug 2019) in each " [:b "category"] "."]
    [:p "Each item reused through the ACE initative, is an item that has avoided becoming waste/pollution."
      " Pollution is often estimated in terms of CO" [:sub "2"] "e,"
      " and we can estimate this for ACE by multipling the total weight of the items"
      " in each category (as shown in the section above) by an appropriate " [:em "multiplier"] "."
      " These multipliers can be found in " 
      [:a {:href (rfe/href :dcs.prototype-6.router/easier-open-data-view nil {:target "co2e-multiplier"})} "The Scottish Carbon Metric multiplier table"] "."
      " Also, we have to " [:em "map"] " ACE's item categories to records in that table,"
      " and this is done via " [:a {:href "ace-furniture-to-waste-streams.csv" :target "_blank"} "this mapping table"] "."]
     [:p " But by themselves, CO" [:sub "2"] "e values can be difficult to relate to so"
      " we convert them into in a more relatable measure: " [:em "flights worth"] " of CO" [:sub "2"] "e..."
      " The average cost of taking a one-way flight from Glasgow to Berlin is 202.5 kgs of CO" [:sub "2"] "e."
      " (This was derived from " 
      [:a {:href "https://www.ovoenergy.com/ovo-newsroom/press-releases/2019/november/think-before-you-thank-if-every-brit-sent-one-less-thank-you-email-a-day-we-would-save-16433-tonnes-of-carbon-a-year-the-same-as-81152-flights-to-madrid.html"
           :target "_blank"} "these OVO Energy estimations"]
      " which incorporate a plane's fuel use but not its build material.)"
      " We divide ACE's CO" [:sub "2"] "e values by 202.5 to yield our " [:em "flights worth"] " of CO" [:sub "2"] "e values"
      " then use these to plot this graph."]
    hint
    [oz/vega-lite (chart-spec-category-level flights-per-category) util/vega-embed-opts]
    ]

   [:div#item-level-co2e-since-inception.content.tab-content-co2e-since-inception {:style {:display "none"}}
    [:p "The " [:b "total CO" [:sub "2"] "e"] " (in terms of flights) of the items of furniture sold (Mar 2017-Aug 2019) in each " [:b "sub-category"] "."]
    hint
    [oz/vega-lite (chart-spec-item-level flights-per-item) util/vega-embed-opts]
    ]

   ]
  )

(defn root []
      [tabbed-area
       @state/ace-furniture-weights-derivation-flights-per-category-cursor
       @state/ace-furniture-weights-derivation-flights-per-item-cursor])


