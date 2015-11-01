(ns color-picker.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [goog.dom :as gdom]
            [devcards.core :as dc])
  (:require-macros [devcards.core :refer [defcard]]))

(enable-console-print!)
(devcards.core/start-devcard-ui!)

(defonce app-state (atom {:count 0}))

(defui Counter
  Object
  (render [this]
    (let [{:keys [count]} (om/props this)]
      (dom/div nil
        (dom/span nil (str "Count: " count))
        (dom/button
          #js {:onClick
               (fn [e]
                 (swap! app-state update-in [:count] inc))}
          "Click me!!")))))

(def reconciler
  (om/reconciler {:state app-state}))

(def node (gdom/getElement "app"))

(om/add-root! reconciler
              Counter
              node)

(defcard hi-world
  "Hi world!")
