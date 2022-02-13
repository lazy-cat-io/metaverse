(ns metaverse.electron.safe-storage
  (:require
    [clojure.string :as str]
    [metaverse.electron :as electron]))


(def ^:const encoding "utf-8")


(defn buffer->string
  [s]
  (when (and (string? s) (not (str/blank? s)))
    (.from js/Buffer s encoding)))


(defn string->buffer
  [s]
  (when s (.toString s encoding)))


(defn encrypt-string
  [s]
  (when s
    (try
      (.encryptString electron/SafeStorage s)
      (catch :default))))


(defn decrypt-string
  [s]
  (when s
    (try
      (.decryptString electron/SafeStorage s)
      (catch :default))))


(defn encrypt
  [s]
  (some-> s
          (encrypt-string)
          (string->buffer)))


(defn decrypt
  [s]
  (some-> s
          (buffer->string)
          (decrypt-string)))
