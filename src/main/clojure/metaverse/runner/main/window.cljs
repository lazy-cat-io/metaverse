(ns metaverse.runner.main.window
  (:require
    [metaverse.runner.config :as config]
    [metaverse.runner.electron.window :as window]
    [metaverse.runner.utils.platform :as platform]))


(defonce *ref (atom nil))


(defn get-instance
  []
  (window/get-instance *ref))


(defn set-instance!
  [window]
  (window/set-instance! *ref window))


(defn reset-instance!
  []
  (window/reset-instance! *ref))


(defn destroy!
  []
  (window/destroy! *ref))


(defn load-app
  [window]
  (window/load-url window config/index-url))


(defn calculate-window-position
  [window bounds]
  (let [x             (.. bounds -x)
        y             (.. bounds -y)
        window-bounds (window/get-bounds window)
        height        (.. window-bounds -height)
        width         (.. window-bounds -width)
        y-position    (if platform/mac-os? y (- y height))]
    {:x      (- x (int (/ width 2)))
     :y      y-position
     :height height
     :width  width}))


(defn hide-traffic-lights!
  [window]
  (when platform/mac-os?
    (window/set-window-button-visibility! window false)))


(defn build-browser-window-options
  []
  {:alwaysOnTop    config/always-on-top?
   :center         config/center?
   :closable       config/closable?
   :devTools       config/devtools?
   :frame          config/frame?
   :fullscreenable config/full-screenable?
   :height         config/height
   :maximizable    config/maximizable?
   :minimizable    config/minimizable?
   :movable        config/movable?
   :resizable      config/resizable?
   :show           config/show?
   :title          config/title
   :titleBarStyle  config/title-bar-style
   :useContentSize config/use-content-size?
   :width          config/width
   :webPreferences {:preload config/preload-file}})


(defn create-window
  ([]
   (window/browser-window (build-browser-window-options)))
  ([opts]
   (window/browser-window opts)))
