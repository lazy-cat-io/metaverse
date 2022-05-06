(ns metaverse.renderer
  (:require
    [goog.dom :as gdom]
    [metaverse.common.logger :as log]
    [metaverse.renderer.api :as api]
    [metaverse.renderer.db :as db]
    [metaverse.renderer.pages.root :as root]
    [metaverse.renderer.router.core :as router]
    [metaverse.renderer.storage]
    [re-frame.core :as rf]
    [reagent.dom :as dom]))


(defn setup-tools
  "Setup tools."
  []
  (log/init!)
  (api/init!))


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (router/init!)
    (dom/render [root/page] root-elem)))


(defn -main
  "Renderer entry point."
  {:export true}
  [& _args]
  (setup-tools)
  (rf/dispatch-sync [::db/init])
  (mount-root))
