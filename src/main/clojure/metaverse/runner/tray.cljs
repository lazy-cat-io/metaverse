(ns metaverse.runner.tray
  (:require
    [metaverse.electron.tray :as tray]
    [metaverse.runner.path :as path]
    [metaverse.utils.path :as utils.path]))


(defn create-tray
  [^js/electron.BrowserWindow _window]
  (->> (utils.path/join path/icons-dir "png" "24x24.png")
       (tray/tray)
       (tray/set-instance!)))
