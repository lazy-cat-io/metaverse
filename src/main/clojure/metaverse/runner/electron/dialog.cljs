(ns metaverse.runner.electron.dialog
  (:require
    [metaverse.runner.electron :as electron]))


(defn show-error-box
  [title message]
  (.showErrorBox ^js/electron.dialog electron/Dialog title message))
