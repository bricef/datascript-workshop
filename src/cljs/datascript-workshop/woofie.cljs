(ns datascript-workshop.woofie
  (:require [reagent.core :as reagent]))

(defn woofie []
   [:div
    [:header [:h1 "Welcome to Woofie"]]
    [:p "The dog social network"]])

(reagent/render-component [woofie] (.-body js/document))
