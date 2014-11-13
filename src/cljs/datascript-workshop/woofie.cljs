(ns datascript-workshop.woofie
  (:require [reagent.core :as reagent :refer [atom]]))

(def app-state (atom {:number-users 0}))

(defn new-user! []
  (swap! app-state #(update-in %1 [:number-users] inc)))

(defn woofie []
   [:div
    [:header [:h1 "Welcome to Woofie"] [:p "The dog social network"]]

    [:div {:class "site-info"}
     [:p (str "Number of dogs on site: " (:number-users @app-state))]]

    [:div {:class "user-tools"}
     [:button {:on-click (new-user!)} "Register"]]])

(reagent/render-component [woofie] (.-body js/document))
