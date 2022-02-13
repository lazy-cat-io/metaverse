(ns metaverse.electron.icon
  (:require
    [metaverse.electron :as electron]))


(defn create-empty
  []
  (.createEmpty electron/NativeImage))


(defn create-from-path
  [path]
  (some->> path (.createFromPath electron/NativeImage)))
