(ns metaverse.runner.window
  (:require
    [metaverse.env :as env]
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]
    [metaverse.runner.refs :as refs]))


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


(defn load-app
  [window]
  (load-url window config/index-url))


(defn on
  [event ^js/electron.BrowserWindow window handler]
  (.on window (name event) handler))


(defn show
  [^js/electron.BrowserWindow window]
  (.show window))


(defn hide
  [^js/electron.BrowserWindow window]
  (.hide window))


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
  [^js/electron.BrowserWindow window]
  (when env/develop?
    (if (.. window -webContents (isDevToolsOpened))
      (.. window -webContents (closeDevTools))
      (.. window -webContents (openDevTools #js {:mode "detach"})))))


(defn browser-window
  [opts]
  (electron/BrowserWindow. (clj->js opts)))


(defn build-browser-window-options
  []
  {:alwaysOnTop     config/always-on-top?
   :center          config/center?
   :closable        config/closable?
   :devTools        config/devtools?
   :frame           config/frame?
   :height          config/height
   :minimizable     config/minimizable?
   :movable         config/movable?
   :resizable       config/resizable?
   :show            config/show?
   :title           config/title
   :titleBarOverlay config/title-bar-overlay?
   :titleBarStyle   config/title-bar-style
   :useContentSize  config/use-content-size?
   :width           config/width
   :webPreferences  {:nodeIntegration config/node-integration?
                     :webSecurity     config/web-security?
                     :sandbox         config/sandbox?}})


(defn create-window
  []
  (-> (build-browser-window-options)
      (browser-window)))
