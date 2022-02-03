(ns metaverse.ui.main
  (:require
    [goog.dom :as gdom]
    [re-frame.core :as rf]
    [reagent.core :refer [atom]]
    [reagent.dom :as dom]))


(enable-console-print!)

(defonce state (atom 0))


(defn app
  []
  [:div {:class "flex "}
   [:div {:class "flex-1 bg-slate-100"}
    "hot reload test"]
   [:div {:class "flex-1 bg-green-500"}
    [:img {:src "assets/images/logotype.black.svg"
           :class "border-2 border-rose-500"}]]])


(defn setup-tools
  "Setup tools."
  [])


(defn root
  []
  [app])


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (dom/render [root] root-elem)))


(defn init!
  "UI initializer."
  {:export true}
  []
  (setup-tools)
  (mount-root))
