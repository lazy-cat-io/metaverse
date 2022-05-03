(ns metaverse.runner.main
  (:require
    [cljs-bean.core :as bean]
    [clojure.string :as str]
    [metaverse.electron.app :as app]
    [metaverse.electron.ipc-main :as ipc-main]
    [metaverse.electron.menu :as menu]
    [metaverse.electron.tray :as tray]
    [metaverse.electron.window :as window]
    [metaverse.logger :as log :include-macros true]
    [metaverse.runner.api :as api]
    ;; runner api deps
    [metaverse.runner.api.auth]
    [metaverse.runner.config :as config]
    [metaverse.runner.menu :as runner.menu]
    [metaverse.runner.reporter :as reporter]
    [metaverse.runner.tray :as runner.tray]
    [metaverse.runner.window :as runner.window]
    [metaverse.supabase :as supabase]
    [metaverse.utils.platform :as platform]
    [metaverse.utils.transit :as t]))


(declare mount)


;;
;; Event handlers
;;

(defn activate-handler
  [_event has-visible-windows?]
  (when-not has-visible-windows?
    (mount)))


(defn closed-handler
  {:dev/before-load true}
  []
  (window/destroy!)
  (tray/destroy!))


(defn ready-to-show-handler
  [window tray]
  (fn []))


(defn window-all-closed-handler
  []
  (when-not platform/mac-os?
    (app/quit)))


(defn tray-click-handler
  [window]
  (fn [_event bounds]
    (let [bounds (runner.window/calculate-window-position window bounds)]
      (window/set-bounds window bounds)
      (window/toggle-window window))))


(defn web-contents-created-handler
  [_event ^js/electron.BrowserWindow.webContents contents]
  (.on contents "will-attach-webview"
       (fn [event web-preferences params]
         (js-delete web-preferences "preload")
         (js-delete web-preferences "preloadURL")
         (aset web-preferences "nodeIntegration" false)
         (log/info "web-preferences" web-preferences)
         (log/info "web-preferences" params)
         (when-not (str/starts-with? (.. params -src) "https://github.com")
           (log/info "preventDefault event" event)
           (.preventDefault event))))

  (.on contents "will-navigate"
       (fn [event navigation-url]
         (let [parsed-url (js/URL. navigation-url)]
           (log/info "navigation-url" navigation-url)
           (log/info "parsed-url" parsed-url)
           (when-not (= "https://github.com" (.. parsed-url -origin))
             (log/info "preventDefault event" event)
             (.preventDefault event))))))


(defn dispatch-handler
  [ipc-event event]
  (let [event (t/read event)]
    (-> (api/dispatch ipc-event event)
        (t/then-write))))


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
  (let [window (runner.window/create-window)
        menu   (runner.menu/create-menu window)
        tray   (runner.tray/create-tray window)]
    (app/dock-hide)
    (setup-global-shortcuts! window)
    (tray/set-tooltip tray config/title)
    (menu/set-application-menu menu)
    (window/set-instance! window)
    (runner.window/load-app window)
    (window/on :closed window closed-handler)
    (window/on :ready-to-show window (ready-to-show-handler window tray))
    (tray/on :click tray (tray-click-handler window))
    (ipc-main/remove-handler! :dispatch)
    (ipc-main/handle :dispatch dispatch-handler)))



;;
;; Entry point
;;

(defn init!
  []
  (setup!)
  (app/on :ready mount)
  (app/on :activate activate-handler)
  (app/on :window-all-closed window-all-closed-handler)
  (app/on :web-contents-created web-contents-created-handler))
