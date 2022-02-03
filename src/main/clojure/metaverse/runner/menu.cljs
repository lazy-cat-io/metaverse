(ns metaverse.runner.menu
  (:require
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]))


(defn set-application-menu
  [menu]
  (.setApplicationMenu ^js/electron.Menu electron/Menu menu))


(defn build-from-template
  [menu]
  (.buildFromTemplate ^js/electron.Menu electron/Menu (clj->js menu)))


(defn create-menu
  [_window]
  (build-from-template
    [{:label   config/window-title
      :submenu [{:role "about"}
                {:type "separator"}
                {:role "hide"}
                {:role "hideOthers"}
                {:role "unhide"}
                {:type "separator"}
                {:role "quit"}]}]))
