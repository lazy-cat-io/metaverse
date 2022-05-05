(ns metaverse.electron.ipc-main
  (:require
    [metaverse.electron :as electron]))


(defn remove-handler!
  [channel]
  (.removeHandler electron/IpcMain channel))


(defn remove-listener!
  [channel listener]
  (.removeListener electron/IpcMain channel listener))


(defn remove-all-listeners!
  ([]
   (.removeAllListeners electron/IpcMain))
  ([channel]
   (.removeAllListeners electron/IpcMain channel)))


(defn on
  [channel listener]
  (.on electron/IpcMain channel listener))


(defn handle
  [channel listener]
  (.handle electron/IpcMain channel listener))
