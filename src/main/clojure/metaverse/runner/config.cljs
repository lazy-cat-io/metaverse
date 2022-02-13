(ns metaverse.runner.config
  (:require
    [metaverse.env :as env]
    [metaverse.utils.os :as os]))


;;
;; System info
;;

(def username (os/get-username))



;;
;; URLs
;;

(def ^:const root-dir js/__dirname)


(def ^:const index-url
  (if env/develop?
    "http://localhost:3000/index.html"
    (str "file://" root-dir "/index.html")))



;;
;; Window options
;;

(def ^:const height 600)
(def ^:const width 500)

(def ^:const always-on-top? false)
(def ^:const center? false)
(def ^:const closable? true)
(def ^:const devtools? env/develop?)
(def ^:const frame? false)
(def ^:const minimizable? true)
(def ^:const movable? true)
(def ^:const resizable? true)
(def ^:const show? false)
(def ^:const title env/product-name)
(def ^:const title-bar-overlay? true)
(def ^:const title-bar-style :hidden)
(def ^:const use-content-size? true)

(def ^:const node-integration? false)
(def ^:const sandbox? false)
(def ^:const web-security? true)
(def ^:const preload-file (str root-dir "/preload.js"))



;;
;; Secret storage options
;;

(def encryption-key username)
