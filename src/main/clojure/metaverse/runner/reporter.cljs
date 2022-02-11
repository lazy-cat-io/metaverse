(ns metaverse.runner.reporter
  (:require
    ["@sentry/electron" :as sentry]
    [metaverse.env :as env]
    [metaverse.logger :as logger :include-macros true]))


(defn init!
  []
  (when env/production?
    (if (= "N/A" env/sentry-dsn)
      (logger/error :msg "Sentry reporter is not initialized" :sentry-dsn env/sentry-dsn)
      (do
        (->> {:dsn env/sentry-dsn}
             (clj->js)
             (.init sentry))
        (logger/info :msg "Sentry reporter successfully initialized" :sentry-dsn env/sentry-dsn)))))
