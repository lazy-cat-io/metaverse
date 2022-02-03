(ns metaverse.runner.window
  (:require
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]))


(defn get-instance
  []
  @config/*window)


(defn set-instance!
  [window]
  (reset! config/*window window))


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
  (load-url window (str "file://" config/root-dir "/index.html")))


(defn open-devtools
  [^js/electron.BrowserWindow window]
  (.. window -webContents (-openDevTools)))


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


(defn browser-window
  [opts]
  (electron/BrowserWindow. (clj->js opts)))


(defn build-browser-window-options
  []
  {:alwaysOnTop     config/window-always-on-top
   :center          config/window-center
   :closable        config/window-closable
   :devTools        true
   :frame           config/window-frame
   :height          config/window-height
   :minimizable     config/window-minimizable
   :movable         config/window-movable
   :resizable       config/window-resizable
   :show            config/window-show
   :title           config/window-title
   :titleBarOverlay config/window-title-bar-overlay
   :titleBarStyle   config/window-title-bar-style
   :useContentSize  config/window-use-content-size
   :width           config/window-width
   :webPreferences  {:nodeIntegration config/window-node-integration
                     :webSecurity     config/window-web-security
                     :sandbox         config/window-sandbox}})


(defn create-window
  []
  (-> (build-browser-window-options)
      (browser-window)))
