(ns metaverse.runner.shell
  (:require
    [metaverse.runner.electron :as electron]))


(defn open-external
  [url]
  (.openExternal ^js/electron.shell electron/Shell url))
