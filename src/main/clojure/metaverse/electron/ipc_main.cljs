(ns metaverse.electron.ipc-main
  (:require
    [metaverse.electron :as electron]))


(defn on
  [event handler]
  (.on electron/IpcMain (name event) handler))


(defn handle
  [channel handler]
  (.handle electron/IpcMain (name channel) handler))


(defn remove-handler!
  [channel]
  (.removeHandler electron/IpcMain (name channel)))
