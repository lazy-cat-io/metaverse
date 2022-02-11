(ns metaverse.runner.main
  (:require
    [metaverse.logger :as logger]
    [metaverse.runner.app :as app]
    [metaverse.runner.config :as config]
    [metaverse.runner.menu :as menu]
    [metaverse.runner.positioner :as positioner]
    [metaverse.runner.reporter :as reporter]
    [metaverse.runner.tray :as tray]
    [metaverse.runner.window :as window]))


(declare mount)


;;
;; Event handlers
;;

(defn activate-handler
  []
  (when (window/recreate-window?)
    (mount)))


(defn closed-handler
  {:dev/before-load true}
  []
  (window/destroy!)
  (tray/destroy!))


(defn ready-to-show-handler
  [window tray]
  (fn []
    (positioner/set-position! window tray)))


(defn window-all-closed-handler
  []
  (when-not config/mac-os?
    (app/quit)))


(defn setup-tools!
  []
  (logger/init!)
  (reporter/init!))


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
  (let [window (window/create-window)
        menu   (menu/create-menu window)
        tray   (tray/create-tray window)]
    (setup-global-shortcuts! window)
    (menu/set-application-menu menu)
    (window/set-instance! window)
    (window/load-app window)
    (window/on :closed window closed-handler)
    (window/on :ready-to-show window (ready-to-show-handler window tray))))



;;
;; Entry point
;;

(defn init!
  []
  (setup-tools!)
  (app/on :ready mount)
  (app/on :window-all-closed window-all-closed-handler)
  (app/on :activate activate-handler))
