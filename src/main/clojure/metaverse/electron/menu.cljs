(ns metaverse.electron.menu
  (:require
    [metaverse.electron :as electron]))


(defn set-application-menu
  [menu]
  (.setApplicationMenu electron/Menu menu))


(defn build-from-template
  [menu]
  (.buildFromTemplate electron/Menu (clj->js menu)))
