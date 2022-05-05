(ns metaverse.runner.main.tray
  (:require
    [metaverse.runner.electron.tray :as tray]
    [metaverse.runner.path :as path]
    [metaverse.runner.utils.path :as utils.path]))


(defonce *ref (atom nil))


(defn ^js/electron.Tray get-instance
  []
  @*ref)


(defn set-instance!
  [tray]
  (tray/set-instance! *ref tray))


(defn reset-instance!
  []
  (tray/reset-instance! *ref))


(defn destroy!
  []
  (tray/destroy! *ref))


(defn create-tray
  [^js/electron.BrowserWindow _window]
  (->> (utils.path/join path/icons-dir "png" "24x24.png")
       (tray/tray)
       (tray/set-instance! *ref)))
