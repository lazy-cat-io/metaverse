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
  (let [p (.join path config/root-dir "assets" "icons" "png" filename)]
    (js/console.log p)
    (.createFromPath electron/NativeImage p)))
