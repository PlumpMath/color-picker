#!/usr/bin/env boot

(set-env!
  :source-paths #{"src"}
  :resource-paths #{"html" "resources"}
  :dependencies '[; Boot setup
                  [adzerk/boot-cljs "1.7.48-6"]
                  [adzerk/boot-cljs-repl "0.2.0"]
                  [adzerk/boot-reload "0.4.1"]
                  [pandeiro/boot-http "0.7.0-SNAPSHOT"]

                  ; App dependencies
                  [org.clojure/clojurescript "1.7.145"]
                  [org.omcljs/om "1.0.0-alpha14"]])

(task-options!
  pom {:project "color-picker-demo"
       :version "0.1.0-SNAPSHOT"})

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[pandeiro.boot-http :refer [serve]])

(deftask run
  []
  (comp
    (serve :dir "target")
    (watch)
    (speak)
    (reload :on-jsload 'color-picker.app/run)
    (cljs-repl)
    (cljs :source-map true
          :optimizations :none)))
