#!/usr/bin/env boot

(set-env!
  :source-paths #{"src/cljs"}
  :resource-paths #{"resources"}
  :dependencies '[; Boot setup
                  [adzerk/boot-cljs "1.7.48-6"]
                  [adzerk/boot-cljs-repl "0.2.0"]
                  [adzerk/boot-reload "0.4.1"]
                  [pandeiro/boot-http "0.7.0-SNAPSHOT"]

                  ; App dependencies
                  [org.clojure/clojurescript "1.7.145"]
                  [org.omcljs/om "1.0.0-alpha14"]
                  [devcards "0.2.0-8"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl repl-env]]
         '[adzerk.boot-reload :refer [reload]]
         '[pandeiro.boot-http :refer [serve]])

(task-options!
  pom {:project "color-picker-demo"
       :version "0.1.0-SNAPSHOT"}
  cljs {:source-map true})

(deftask run
  []
  (comp
    (watch)
    (speak)
    (reload)
    (cljs-repl)
    (cljs :compiler-options {:devcards true})
    (serve :dir "target")))
