(ns metaverse.electron.screen
  (:require
    [metaverse.electron :as electron]))


(defn get-primary-display
  []
  (.getPrimaryDisplay electron/Screen))


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
