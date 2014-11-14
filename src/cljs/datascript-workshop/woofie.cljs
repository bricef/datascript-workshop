(ns datascript-workshop.woofie
  (:require [reagent.core :as reagent :refer [atom]]
            [datascript-workshop.utils :refer [bind unbind] ]
            [datascript :as d]))


;; Creates a DataScript "connection"
;; (really an atom with the current DB value)
(def conn (d/create-conn))

;; Add some data
(d/transact! conn
   [{:name "Rex" :age 3 :sex "m"
     :breed "Alsatian" :owner "Marco Polo"}
    {:name "Sally" :age 4 :sex "f"
     :breed "Yorkshire Terrier" :owner "Mrs Pollywell"}
    {:name "Snowy" :age 2 :sex "m"
     :breed "Wire Fox Terrier" :owner "Tintin"}
    {:name "Fido" :age 4 :sex "m"
     :breed "Basset Hound" :owner "Marco Polo"}
    {:name "Sam" :age 6 :sex "f"
     :breed "Alsatian" :owner "Guy Fawkes"}])

; Datascript Queries

(def q-dog-names '[:find ?n :where [?e :name ?n]])

(def q-pairings-purebreed
  '[:find ?m ?f ?e ?a
    :where [?e :name ?m]
           [?e :sex "m"]
           [?e :breed ?b]
           [?a :name ?f]
           [?a :sex "f"]
           [?a :breed ?b]
          ])

(def q-pairings-all
  '[:find ?m ?f ?e ?a
    :where [?e :name ?m]
           [?e :sex "m"]
           [?a :name ?f]
           [?a :sex "f"]
          ])

;; Finding every dogs
(def q-unique-dogs '[:find ?n ?e :where [?e :name ?n]])

(comment
  (d/q q-dog-names @conn)
  (d/q q-pairings-purebreed @conn)
  (d/q q-pairings-all @conn)
  (d/q q-unique-dogs @conn)
  )


(def app-state (atom {:number-users 0}))

(defn new-user! []
  (swap! app-state update-in [:number-users] inc))

(defn add-betty! []
  (d/transact! conn [{:name "Betty"
                      :age 3
                      :sex "f"
                      :breed "Wire Fox Terrier"
                      :owner "George Orwell"}]))

(defn woofie []
  (let [matches (bind conn q-pairings-purebreed)
        members (bind conn q-unique-dogs)]
   [:div
    [:header [:h1 "Welcome to Woofie"] [:p "The dog social network"]]

    [:div {:class "site-info"}
     [:p (str "Number of dogs on site: " (count @members))]]

    [:div {:class "user-tools"}
     [:button {:on-click add-betty!} "Add betty"]
     ]

    ;; Listing all the dogs
    [:div {:class "members"}
     [:h3 "Members"]
     [:ul
      (map
       (fn [n] [:li (str (n 0))])
       @members))]]

    [:div {:class "matches"}
     [:h3 "Matches"]
     [:ul
      (map
       (fn [p] [:li (str (p 0) " and " (p 1))])
       @matches)]]

    ]))

(reagent/render-component [woofie] (.-body js/document))
