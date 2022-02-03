(ns metaverse.ui.main
  (:require
    [goog.dom :as gdom]
    [re-frame.core :as rf]
    [reagent.dom :as dom]))


(defn app
  []
  [:div {:class "flex"}
   [:div {:class "flex-1"}
    ""]
   [:div {:class "flex-1"}
    [:img {:src "assets/images/logotype.black.svg"}]]])


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
