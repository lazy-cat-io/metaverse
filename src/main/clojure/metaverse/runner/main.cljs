(ns metaverse.runner.main
  (:require
    [metaverse.runner.app :as app]
    [metaverse.runner.config :as config]
    [metaverse.runner.menu :as menu]
    [metaverse.runner.positioner :as positioner]
    [metaverse.runner.reporter :as reporter]
    [metaverse.runner.tray :as tray]
    [metaverse.runner.window :as window]))


(declare mount)


;; FIXME: [2022-02-02, ilshat@sultanov.team]
;; - добавить :opencollective :url в package.json
;; - добавить иконки
;; - закоммитить все и отправить в гит
;; - нарисовать лого? metaverse
;; - настроить auto updater?
;; - настроить bb tasks
;; - подключить шрифт FiraCode
;; - проверить как можно запустить npm run repl и в конце дождавшись билда main.js запустить electron:watch
;; - проверить как можно сделать electron:watch
;; - проверить почему ругается tailwind
;; - проверить почему стили подгружаются только после повторного билда


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


(defn ready-to-show
  [window tray]
  (fn []
    (positioner/set-position! window tray)
    (window/open-devtools window)))


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
    (window/on :ready-to-show window (ready-to-show window tray))))



;;
;; Entry point
;;

(defn init!
  []
  #_(reporter/init-sentry!)
  (app/on :ready mount)
  (app/on :window-all-closed window-all-closed-handler)
  (app/on :activate activate-handler))
