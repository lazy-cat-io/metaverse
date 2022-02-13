(ns metaverse.runner.path
  (:require
    [metaverse.runner.config :as config]
    [metaverse.utils.path :as path]))


(def assets-dir
  (path/join config/root-dir "assets"))


(def icons-dir
  (path/join assets-dir "icons"))
