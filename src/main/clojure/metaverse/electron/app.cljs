(ns metaverse.electron.app
  (:require
    [metaverse.electron :as electron]))


(defn on
  [event handler]
  (.on electron/App (name event) handler))


(defn dock-hide
  []
  (.. electron/App -dock (hide)))


(defn quit
  []
  (.quit electron/App))
