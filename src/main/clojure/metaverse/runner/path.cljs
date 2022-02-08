(ns metaverse.runner.path
  (:require
    ["path" :as path]
    [metaverse.runner.config :as config]))


(defn join
  [& paths]
  (reduce
    (fn [acc v]
      (.join path acc v))
    paths))


(def assets-dir
  (join config/root-dir "assets"))


(def icons-dir
  (join assets-dir "icons"))
