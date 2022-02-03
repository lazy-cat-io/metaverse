(ns metaverse.runner.electron
  (:require
    ["electron" :refer [app screen nativeImage crashReporter]
     :rename {BrowserWindow browserWindow
              Menu          menu
              Tray          tray}]
    ["electron-traywindow-positioner" :as positioner]))


(def App app)
(def BrowserWindow browserWindow)
(def CrashReporter crashReporter)
(def Menu menu)
(def NativeImage nativeImage)
(def Positioner positioner)
(def Screen screen)
(def Tray tray)
