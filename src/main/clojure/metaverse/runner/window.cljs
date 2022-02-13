(ns metaverse.runner.window
  (:require
    [metaverse.electron.window :as window]
    [metaverse.runner.config :as config]))


(defn load-app
  [window]
  (window/load-url window config/index-url))


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
   :webPreferences  {:nodeIntegration    config/node-integration?
                     :webSecurity        config/web-security?
                     :sandbox            config/sandbox?
                     :preload            config/preload-file}})


(defn create-window
  ([]
   (window/browser-window (build-browser-window-options)))
  ([opts]
   (window/browser-window opts)))
