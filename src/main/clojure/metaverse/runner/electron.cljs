(ns metaverse.runner.electron
  (:require
    ["electron" :refer [app screen nativeImage crashReporter globalShortcut safeStorage]
     :rename {BrowserWindow browserWindow
              Menu          menu
              Tray          tray}]
    ["electron-store" :as store]
    ["electron-traywindow-positioner" :as positioner]))


(def App app)
(def BrowserWindow browserWindow)
(def CrashReporter crashReporter)
(def Menu menu)
(def NativeImage nativeImage)
(def Positioner positioner)
(def Screen screen)
(def Tray tray)
(def GlobalShortcut globalShortcut)
(def Store store)
(def SafeStorage safeStorage)
