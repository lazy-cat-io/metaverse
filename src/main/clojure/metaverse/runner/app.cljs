(ns metaverse.runner.app
  (:require
    [metaverse.runner.electron :as electron]))


(defn on
  [event handler]
  (.on ^js/electron.app electron/App (name event) handler))


(defn quit
  []
  (.quit ^js/electron.app electron/App))
