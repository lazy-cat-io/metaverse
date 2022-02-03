(ns metaverse.runner.icon
  (:require
    ["path" :as path]
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]))


(defn create-empty
  []
  (.createEmpty electron/NativeImage))


(defn create
  [filename]
  (some->> filename
           (.join path config/root-dir "assets" "icons" "png")
           (.createFromPath electron/NativeImage)))
