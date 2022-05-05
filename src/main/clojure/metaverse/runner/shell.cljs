(ns metaverse.runner.shell
  (:require
    [metaverse.electron :as electron]))


(defn open-external
  [url]
  (.openExternal ^js/electron.shell electron/Shell url))
