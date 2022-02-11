(ns metaverse.runner.keyboard
  (:require
    [metaverse.runner.electron :as electron]))


(defn register-global-shortcut!
  [shortcut handler]
  (.register electron/GlobalShortcut shortcut handler))
