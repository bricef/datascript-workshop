(ns datascript-workshop.utils
  (:require
     [reagent.core :as reagent :refer [atom]]
     [datascript :as d]
     [datascript.core :as dc]
     [cljs-uuid.core :as uuid]))


(defn bind
  ([conn q]
   (bind conn q (atom nil)))
  ([conn q state]
   (let [k (uuid/make-random)]
     (reset! state (d/q q @conn))
     (d/listen! conn k (fn [tx-report]
                         (let [novelty (d/q q (:tx-data tx-report))]
                           (when (not-empty novelty) ;; Only update if query results actually changed
                             (reset! state (d/q q (:db-after tx-report)))))))
     (set! (.-__key state) k)
     state)))

(defn unbind
  [conn state]
  (d/unlisten! conn (.-__key state)))
