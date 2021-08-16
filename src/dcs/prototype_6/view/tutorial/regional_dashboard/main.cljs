(ns dcs.prototype-6.view.tutorial.regional-dashboard.main)

(defn root
  []
  [:div.section

   [:div.content.has-text-centered
    [:h1.title.is-5 "Regional dashboard tutorial"]
    [:h2.subtitle.is-6.has-text-info "How to interpret the "[:em "regional dashboard"] " page."]]

   [:div.container
    [:div.content


     "Add text here."

     "And here."

     "Even split over 
      several
      lines."

     "Etc. etc."
 
     [:p "Put it inside paragraph blocks."]

     [:p "Text a, b, c,"
      " d and e."]

     [:ul 
      [:li "Have list items."]
      [:li "Item 2"]]
     
     "Break explicitly over 2 lines... on line 1" [:br] "and this will be on the line underneath."
     
     
     ]]])