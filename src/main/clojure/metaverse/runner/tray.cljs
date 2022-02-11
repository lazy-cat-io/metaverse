(ns metaverse.runner.tray
  (:require
    [metaverse.runner.electron :as electron]
    [metaverse.runner.icon :as icon]
    [metaverse.runner.menu :as menu]
    [metaverse.runner.path :as path]
    [metaverse.runner.refs :as refs]
    [metaverse.runner.window :as window]))


(defn ^js/electron.Tray get-instance
  []
  @refs/*tray)


(defn set-instance!
  [tray]
  (reset! refs/*tray tray))


(defn reset-instance!
  []
  (set-instance! nil))


(defn create-context-menu
  [^js/electron.BrowserWindow window]
  (menu/build-from-template
    [{:label "Explore" :click #(window/show window)}]))


(defn set-context-menu
  [^js/electron.Tray tray context-menu]
  (.setContextMenu tray context-menu))


(defn set-tooltip
  [^js/electron.Tray tray tooltip]
  (.setToolTip tray tooltip))


(defn tray
  ([] (electron/Tray. (icon/create-empty)))
  ([icon-path] (electron/Tray. (icon/create-from-path icon-path))))


(defn create-tray
  [^js/electron.BrowserWindow window]
  (let [tray         (tray (path/join path/icons-dir "png" "24x24.png"))
        context-menu (create-context-menu window)]
    (set-context-menu tray context-menu)
    (set-instance! tray)))


(defn destroy!
  []
  (when-some [tray (get-instance)]
    (.destroy tray)
    (reset-instance!)))
