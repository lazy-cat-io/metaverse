(ns metaverse.runner.main
  (:require
    [metaverse.runner.app :as app]
    [metaverse.runner.config :as config]
    [metaverse.runner.menu :as menu]
    [metaverse.runner.positioner :as positioner]
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



;;
;; Mount root
;;

(defn mount
  []
  (let [window (window/create-window)
        menu   (menu/create-menu window)
        tray   (tray/create-tray window)]
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
  #_(reporter/init-sentry!)
  (app/on :ready mount)
  (app/on :window-all-closed window-all-closed-handler)
  (app/on :activate activate-handler))
