(ns metaverse.electron.window
  (:require
    [metaverse.electron :as electron]))


(defn get-instance
  [^Atom *ref]
  @*ref)


(defn set-instance!
  [^Atom *ref window]
  (reset! *ref window))


(defn reset-instance!
  [^Atom *ref]
  (set-instance! *ref nil))


(defn destroy!
  [^Atom *ref]
  (reset-instance! *ref))


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


(defn get-bounds
  [^js/electron.BrowserWindow window]
  (.getBounds window))


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
