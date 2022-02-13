(ns metaverse.electron.tray
  (:require
    [metaverse.electron :as electron]
    [metaverse.electron.icon :as icon]
    [metaverse.electron.refs :as refs]))


(defn ^js/electron.Tray get-instance
  []
  @refs/*tray)


(defn set-instance!
  [tray]
  (reset! refs/*tray tray))


(defn reset-instance!
  []
  (set-instance! nil))


(defn tray
  ([] (electron/Tray. (icon/create-empty)))
  ([icon-path] (electron/Tray. (icon/create-from-path icon-path))))


(defn destroy!
  []
  (when-some [tray (get-instance)]
    (.destroy tray)
    (reset-instance!)))


(defn set-tooltip
  [^js/electron.Tray tray tooltip]
  (.setToolTip tray tooltip))


(defn on
  [event ^js/electron.Tray tray handler]
  (.on tray (name event) handler))
