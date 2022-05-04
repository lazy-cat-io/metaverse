(ns metaverse.electron.tray
  (:require
    [metaverse.electron :as electron]
    [metaverse.electron.icon :as icon]))


(defn ^js/electron.Tray get-instance
  [^Atom *ref]
  @*ref)


(defn set-instance!
  [^Atom *ref tray]
  (reset! *ref tray))


(defn reset-instance!
  [^Atom *ref]
  (set-instance! *ref nil))


(defn destroy!
  [*ref]
  (when-some [tray (get-instance *ref)]
    (.destroy tray)
    (reset-instance! *ref)))


(defn tray
  ([] (electron/Tray. (icon/create-empty)))
  ([icon-path] (electron/Tray. (icon/create-from-path icon-path))))


(defn set-tooltip
  [^js/electron.Tray tray tooltip]
  (.setToolTip tray tooltip))


(defn on
  [event ^js/electron.Tray tray handler]
  (.on tray (name event) handler))
