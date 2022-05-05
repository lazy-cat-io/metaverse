(ns metaverse.electron.app
  (:require
    [metaverse.electron :as electron]))


(defn on
  [event handler]
  (.on electron/App event handler))


(defn dock-hide
  []
  (.. electron/App -dock (hide)))


(defn quit
  []
  (.quit electron/App))


(defn request-single-instance-lock
  []
  (.requestSingleInstanceLock electron/App))


(defn set-as-default-protocol-client
  [protocol]
  (.setAsDefaultProtocolClient electron/App protocol))


;; TODO: [2022-05-05, ilshat@sultanov.team] Implement the code below

;; if (process.defaultApp) {
;;   if (process.argv.length >= 2) {
;;     app.setAsDefaultProtocolClient('electron-fiddle', process.execPath, [path.resolve(process.argv[1])])
;;   }
;; } else {
;;   app.setAsDefaultProtocolClient('electron-fiddle')
;; }

;; if (!gotTheLock) {
;;  app.quit()
;; } else {
;;   app.on('second-instance', (event, commandLine, workingDirectory) => {
;;    // Someone tried to run a second instance, we should focus our window.
;;    if (mainWindow) {
;;    if (mainWindow.isMinimized())
;;      mainWindow.restore()
;;      mainWindow.focus()
;;     }
;; })
