(ns metaverse.runner.positioner
  (:require
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]))


(defn set-position!
  [^js/electron.BrowserWindow window ^js/electron.Tray tray]
  (when (and window tray)
    (as-> tray $
          (.getBounds $)
          (.position electron/Positioner window $ (clj->js config/tray-alignment)))))
