(ns metaverse.runner.config)


;;
;; Application options
;;

(def ^:const company-name "@team.sultanov")
(def ^:const product-name "metaverse")


;;
;; Refs
;;

(defonce *window (atom nil))
(defonce *tray (atom nil))



;;
;; URLs
;;

(def ^:const root-dir js/__dirname)
(def ^:const index-url (str "file://" root-dir "/index.html"))



;;
;; Platform options
;;

(def ^:const platform js/process.platform)
(def ^:const mac-os? (= "darwin" platform))



;;
;; Window options
;;

(def ^:const window-height 600)
(def ^:const window-width 500)

(def ^:const window-always-on-top false)
(def ^:const window-center false)
(def ^:const window-closable true)
(def ^:const window-frame true)
(def ^:const window-minimizable true)
(def ^:const window-movable true)
(def ^:const window-resizable true)
(def ^:const window-show false)
(def ^:const window-title product-name)
(def ^:const window-title-bar-overlay true)
(def ^:const window-title-bar-style :hidden)
(def ^:const window-use-content-size true)

(def ^:const window-node-integration false)
(def ^:const window-sandbox false)
(def ^:const window-web-security true)

(goog-define example "N/A")



;;
;; Tray alignment
;;

(def ^:const tray-alignment {:x "center", :y "up"})



;;
;; Crash reporter options
;;

(def ^:const crash-reporter-submit-url "")
(def ^:const crash-reporter-auto-submit true)
(def ^:const crash-reporter-upload-to-server true)
