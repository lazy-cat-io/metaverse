(ns metaverse.electron
  (:require
    ["electron" :refer [app screen nativeImage crashReporter
                        globalShortcut safeStorage ipcMain ipcRenderer contextBridge]
     :rename {BrowserWindow browserWindow
              ipcRenderer   ipcRenderer
              Menu          menu
              Tray          tray}]
    ["electron-store" :as store]))


(def ^{:tag js/electron.app} App app)
(def ^{:tag js/electron.BrowserWindow} BrowserWindow browserWindow)
(def ^{:tag js/electron.contextBridge} ContextBridge contextBridge)
(def ^{:tag js/electron.crashReporter} CrashReporter crashReporter)
(def ^{:tag js/electron.globalShortcut} GlobalShortcut globalShortcut)
(def ^{:tag js/electron.ipcMain} IpcMain ipcMain)
(def ^{:tag js/electron.app} IpcRenderer ipcRenderer)
(def ^{:tag js/electron.Menu} Menu menu)
(def ^{:tag js/electron.nativeImage} NativeImage nativeImage)
(def ^{:tag js/electron.safeStorage} SafeStorage safeStorage)
(def ^{:tag js/electron.screen} Screen screen)
(def ^{:tag js/ElectronStore} Store store)
(def ^{:tag js/electron.Tray} Tray tray)
