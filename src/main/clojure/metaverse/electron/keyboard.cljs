(ns metaverse.electron.keyboard
  (:require
    [metaverse.electron :as electron]))


(defn register-global-shortcut!
  [shortcut handler]
  (.register electron/GlobalShortcut shortcut handler))
