(ns metaverse.sentry.nodejs
  (:require
    ["@sentry/node" :as sentry]
    ["@sentry/tracing"]))


(defn init!
  [opts]
  (.init sentry (clj->js opts)))


;; TODO: [2022-05-02, ilshat@sultanov.team] Add close handler, setContext, setUser, etc
