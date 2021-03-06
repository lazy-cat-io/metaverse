(ns metaverse.common.utils.bean
  (:require
    [cljs-bean.core :as b]))


(defn ->js
  [x]
  (b/->js x))


(defn ->clj
  [x]
  (b/->clj x))


(defn bean
  [^js x]
  (when (some? x)
    (b/bean x :recursive true)))


(defn then-bean
  [^js promise]
  (.then promise bean))
