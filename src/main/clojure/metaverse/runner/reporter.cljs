(ns metaverse.runner.reporter
  (:require
    [metaverse.env :as env]
    [metaverse.logger :as log :include-macros true]
    [metaverse.sentry :as sentry]))


(defn init!
  []
  (when env/production?
    (if (= "N/A" env/sentry-dsn)
      (log/error :msg "Sentry reporter is not initialized" :sentry-dsn env/sentry-dsn)
      (do
        (sentry/init! {:dsn env/sentry-dsn})
        (log/info :msg "Sentry reporter successfully initialized" :sentry-dsn env/sentry-dsn)))))
