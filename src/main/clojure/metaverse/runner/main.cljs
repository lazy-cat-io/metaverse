(ns metaverse.runner.main
  (:require
    [clojure.string :as str]
    [metaverse.electron.app :as app]
    [metaverse.electron.ipc-main :as ipc-main]
    [metaverse.electron.menu :as menu]
    [metaverse.electron.tray :as tray]
    [metaverse.electron.window :as window]
    [metaverse.logger :as log :include-macros true]
    [metaverse.runner.api :as api]
    [metaverse.runner.api.auth]
    [metaverse.runner.config :as config]
    [metaverse.runner.main.menu :as main.menu]
    [metaverse.runner.main.tray :as main.tray]
    [metaverse.runner.main.window :as main.window]
    [metaverse.runner.reporter :as reporter]
    [metaverse.supabase :as supabase]
    [metaverse.utils.platform :as platform]
    [metaverse.utils.transit :as t]
    [tenet.response :as r]))


(declare mount)


;;
;; Event handlers
;;

(defn activate-handler
  [_event has-visible-windows?]
  (when-not has-visible-windows?
    (mount)))


(defn open-url-handler
  [_event url]
  (when (str/starts-with? url "metaverse://app")
    (let [window       ^js/electron.BrowserWindow (main.window/get-instance)
          web-contents ^js/electron.BrowserWindow.webContents (.. window -webContents)]
      (.send web-contents "dispatch" (t/write (r/as-success [:open-url url]))))))


(defn closed-handler
  {:dev/before-load true}
  []
  (main.window/destroy!)
  (main.tray/destroy!))


(defn ready-to-show-handler
  [_window _tray]
  (fn []))


(defn window-all-closed-handler
  []
  (when-not platform/mac-os?
    (app/quit)))


(defn tray-click-handler
  [window]
  (fn [_event bounds]
    (let [bounds (main.window/calculate-window-position window bounds)]
      (window/set-bounds window bounds)
      (window/toggle-window window))))


(defn invoke-handler
  [ipc-event event]
  (let [event (t/read event)]
    (-> (api/invoke ipc-event event)
        (t/then-write))))


(defn send-handler
  [ipc-event event]
  (let [event (t/read event)]
    (api/invoke ipc-event event)))


;;
;; Initializers
;;

(defn setup-tools!
  []
  (log/init!)
  (reporter/init!))


(defn setup-deps!
  []
  (supabase/init!))


(defn setup!
  []
  (setup-tools!)
  (setup-deps!))


;; TODO: [2022-02-11, ilshat@sultanov.team] Add a global shortcut to search for a library or docs / to create a new project?
;; example: (keyboard/register-global-shortcut! "Alt+CommandOrControl+I" #(window/toggle-devtools window))

(defn setup-global-shortcuts!
  [_window])



;;
;; Mount root
;;

(defn mount
  {:dev/after-load true}
  []
  (let [window (main.window/create-window)
        menu   (main.menu/create-menu window)
        tray   (main.tray/create-tray window)]
    (app/dock-hide)
    (app/set-as-default-protocol-client "metaverse")
    (setup-global-shortcuts! window)
    (tray/set-tooltip tray config/title)
    (menu/set-application-menu menu)
    (main.window/set-instance! window)
    (main.window/load-app window)
    (window/on "closed" window closed-handler)
    (window/on "ready-to-show" window (ready-to-show-handler window tray))
    (tray/on "click" tray (tray-click-handler window))))



;;
;; Entry point
;;

(defn init!
  []
  (setup!)
  (app/on "ready" mount)
  (app/on "activate" activate-handler)
  (app/on "open-url" open-url-handler)
  (app/on "window-all-closed" window-all-closed-handler)
  (ipc-main/remove-all-listeners!)
  (ipc-main/handle "invoke" invoke-handler)
  (ipc-main/on "send" send-handler))

