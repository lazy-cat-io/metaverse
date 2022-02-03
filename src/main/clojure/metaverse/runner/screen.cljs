(ns metaverse.runner.screen
  (:require
    [metaverse.runner.electron :as electron]))


(defn get-primary-display
  []
  (.getPrimaryDisplay ^js/electron.screen electron/Screen))


(defn get-width
  ([]
   (get-width (get-primary-display)))
  ([^js/electron.screen display]
   (.. display -bounds -width)))


(defn get-height
  ([]
   (get-height (get-primary-display)))
  ([^js/electron.screen display]
   (.. display -bounds -height)))
