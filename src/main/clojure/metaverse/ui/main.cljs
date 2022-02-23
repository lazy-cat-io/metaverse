(ns metaverse.ui.main
  (:require
    [goog.dom :as gdom]
    [metaverse.logger :as logger]
    [metaverse.ui.db :as db]
    [metaverse.ui.pages.home :as home]
    [metaverse.ui.router.core :as router]
    [re-frame.core :as rf]
    [reagent.dom :as dom]))


(defn setup-tools
  "Setup tools."
  []
  (logger/init!))


(defn root
  []
  [home/page])


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (router/init!)
    (dom/render [root] root-elem)))


(defn init!
  "UI initializer."
  {:export true}
  []
  (setup-tools)
  (rf/dispatch-sync [::db/init])
  (mount-root))
