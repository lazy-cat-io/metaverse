(ns metaverse.runner.secret
  (:require
    [metaverse.runner.config :as config]
    [metaverse.runner.safe-storage :as safe-storage]
    [metaverse.runner.store :as store]))


(defonce store
  (store/create-store
    {:name          "metaverse"
     :encryptionKey config/encryption-key
     :watch         true}))


(defn get-secret
  [key]
  (some->> key
           (store/get store)
           (safe-storage/decrypt)))


(defn set-secret
  [key secret]
  (some->> secret
           (safe-storage/encrypt)
           (store/assoc store key)))


(defn delete-secret
  [key]
  (store/dissoc store key))
