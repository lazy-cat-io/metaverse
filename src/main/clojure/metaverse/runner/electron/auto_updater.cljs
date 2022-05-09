(ns metaverse.runner.electron.auto-updater
  (:require
    [metaverse.runner.electron :as electron]))


(defn check-for-updates-and-notify
  []
  (.checkForUpdatesAndNotify electron/AutoUpdater))



(defn on
  [event  handler]
  (.on ^js/electron.autoUpdater electron/AutoUpdater (name event) handler))
