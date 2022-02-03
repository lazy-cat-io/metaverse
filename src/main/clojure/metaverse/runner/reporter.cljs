(ns metaverse.runner.reporter
  (:require
    ["@sentry/electron" :as sentry]
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]))


(defn init-sentry!
  []
  (->> {:dsn config/crash-reporter-submit-url}
       (clj->js)
       (.init sentry)))


(defn start!
  []
  (->> {:autoSubmit               config/crash-reporter-auto-submit
        :companyName              config/company-name
        :ignoreSystemCrashHandler true
        :productName              config/product-name
        :submitURL                config/crash-reporter-submit-url
        :uploadToServer           config/crash-reporter-upload-to-server}
       (clj->js)
       (.start electron/CrashReporter)))
