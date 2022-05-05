(ns metaverse.runner.config
  (:require
    [metaverse.common.env :as env]
    [metaverse.runner.utils.os :as os]))


;;
;; System info
;;

(def username (os/get-username))



;;
;; URLs
;;

(def root-dir js/__dirname)


(def index-url
  (if env/develop?
    "http://localhost:3000/index.html"
    (str "file://" root-dir "/index.html")))



;;
;; Window options
;;

(def height 800)
(def width 600)

(def always-on-top? false)
(def center? false)
(def closable? true)
(def devtools? env/develop?)
(def frame? false)
(def full-screenable? true)
(def maximizable? true)
(def minimizable? true)
(def movable? true)
(def resizable? true)
(def show? false)
(def title env/product-name)
(def title-bar-overlay? false)
(def title-bar-style :hidden)
(def use-content-size? true)

(def node-integration? false)
(def sandbox? false)
(def web-security? true)
(def preload-file (str root-dir "/preload.js"))



;;
;; Secret storage options
;;

(def encryption-key username)
