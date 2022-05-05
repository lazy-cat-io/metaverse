(ns metaverse.runner.electron.store
  (:refer-clojure :exclude [get get-in assoc dissoc])
  (:require
    [clojure.string :as str]
    [metaverse.runner.electron :as electron]))


(defn create-store
  ([]
   (electron/Store.))
  ([data]
   (electron/Store. (clj->js data))))


(defn get
  [store key]
  (when key
    (.get store key)))


(defn get-in
  [store ks]
  (when (sequential? ks)
    (->> (str/join "." ks)
         (get store)
         (js->clj))))


(defn assoc
  [store key value]
  (when key
    (.set store key (clj->js value))))


(defn dissoc
  [store key]
  (when key
    (.delete store key)))


