(ns metaverse.electron.window
  (:require
    [metaverse.electron :as electron]
    [metaverse.electron.refs :as refs]))


(defn get-instance
  []
  @refs/*window)


(defn set-instance!
  [window]
  (reset! refs/*window window))


(defn reset-instance!
  []
  (set-instance! nil))


(defn destroy!
  []
  (reset-instance!))


(defn load-url
  [^js/electron.BrowserWindow window url]
  (.loadURL window url))


(defn on
  [event ^js/electron.BrowserWindow window handler]
  (.on window (name event) handler))


(defn show
  [^js/electron.BrowserWindow window]
  (.show window))


(defn hide
  [^js/electron.BrowserWindow window]
  (.hide window))


(defn visible?
  [^js/electron.BrowserWindow window]
  (.isVisible window))


(defn toggle-window
  [^js/electron.BrowserWindow window]
  (if-not (visible? window)
    (show window)
    (hide window)))


(defn set-bounds
  [^js/electron.BrowserWindow window bounds]
  (.setBounds window (clj->js bounds)))


(defn set-title
  [^js/electron.BrowserWindow window title]
  (.setTitle window title))


(defn get-all-windows
  []
  (.getAllWindows electron/BrowserWindow))


(defn recreate-window?
  []
  (zero? (count (get-all-windows))))


(defn toggle-devtools
  [^js/electron.BrowserWindow window opts]
  (.. window -webContents (toggleDevTools opts)))


(defn browser-window
  [opts]
  (electron/BrowserWindow. (clj->js opts)))
