(ns metaverse.runner.electron.icon
  (:require
    [metaverse.runner.electron :as electron]))


(defn create-empty
  []
  (.createEmpty electron/NativeImage))


(defn create-from-path
  [path]
  (some->> path (.createFromPath electron/NativeImage)))
