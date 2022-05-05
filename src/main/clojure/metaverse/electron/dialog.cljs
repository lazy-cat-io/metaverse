(ns metaverse.electron.dialog
  (:require
    [metaverse.electron :as electron]))


(defn show-error-box
  [title message]
  (.showErrorBox ^js/electron.dialog electron/Dialog title message))
