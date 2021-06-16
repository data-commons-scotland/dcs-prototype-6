(ns dcs.prototype-6.view.ace-furniture.shared)

(def periods ["Mar 2017 - Feb 2018" "Mar 2018 - Feb 2019" "Mar 2019 - Aug 2019"])

(def transform-spec [{:timeUnit "yearmonth" :field "yyyy-MM-dd"  :as "instant"}
                     {:calculate "timeFormat(datum.instant, '%m %Y')" :as "simpleinstant"}
                     {:calculate (str "if(datum.simpleinstant == '02 2018', '" (get periods 0)
                                      "', if(datum.simpleinstant == '02 2019', '" (get periods 1)
                                      "', '" (get periods 2)
                                      "'))") :as "period"}])

(def color-spec {:field "period"  :type "nominal"
                 :scale {:domain periods
                         :range ["#D99586" "#C0808C" "#9B708D"]}
                 :legend {:title "period"}})
