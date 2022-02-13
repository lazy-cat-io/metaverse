(ns metaverse.sentry
  (:require
    ["@sentry/electron" :as sentry]))


(defn init!
  [opts]
  (.init sentry (clj->js opts)))
