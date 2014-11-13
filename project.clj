(defproject datascript-workshop "0.1.0-SNAPSHOT"
  :description "Datascript workshop for Non-Dysfunctional Programmers"
  :url "https://github.com/bricef/datascript-workshop"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; CLJ AND CLJS source code path
  :source-paths ["src/clj" "src/cljs"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [datascript "0.5.1"]
                 [reagent "0.4.3"]
                 [cljs-uuid "0.0.4"]]

  ;; lein-cljsbuild plugin to build a CLJS project
  :plugins [[lein-cljsbuild "1.0.3"]]

  ;; cljsbuild options configuration
  :cljsbuild {:builds
              [{;; CLJS source code path
                :source-paths ["src/cljs"]

                ;; Google Closure (CLS) options configuration
                :compiler {;; CLS generated JS script filename
                           :output-to "resources/public/js/workshop.js"

                           ;; minimal JS/ optimization directive
                           :optimizations :whitespace

                           ;; generated JS code prettyfication
                           :pretty-print true

                           ;; We need react.js to be included in order to use reagent
                           :preamble ["reagent/react.js"]}}]})
