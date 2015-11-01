(ns color-picker.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [goog.dom :as gdom]
            [devcards.core :as dc]
            [cljs.pprint :as pp]
            [cljs.test :as t :include-macros true])
  (:require-macros [devcards.core :refer [defcard defcard-doc deftest mkdn-pprint-source]]))

(enable-console-print!)
(devcards.core/start-devcard-ui!)

(defonce app-state (atom {:count 0}))

(defonce saved-color (atom {:color {:r 0 :g 0 :b 0}}))
(def init-color {:color {:id 666 :r 1 :g 2 :b 3}})

(defui Color
  static om/Ident
  (ident [this {:keys [id]}]
         [:the-color id])
  static om/IQuery
  (query [this]
    '[:id :r :g :b]))

(defui App
  static om/IQuery
  (query [this]
    (let [subquery (om/get-query Color)]
      `[{:color ~subquery}])))

(defmulti read om/dispatch)

(defn get-color [state key]
  (let [st @state]
    (get-in st [:color key])))

(defmethod read :color
  [{:keys [state] :as env} key params]
  {:value (get-color state key)})

(def norm-data (om/tree->db App init-color true))
;(pp/pprint norm-data)

(defcard
  "normie data"
  (str norm-data))

(deftest get-color-test
  "Get color test"
  (t/is (= (get-color (atom {:color {:r 1 :g 2 :b 3}}) :g)
           2)))

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

(defcard saved-color
  "###  Here's the current color being saved."
  @saved-color
  {:inspect-data true :history true})

(defcard color-slider-identity
  "First, let's see the definition of an identity."
  (mkdn-pprint-source om/ident))
