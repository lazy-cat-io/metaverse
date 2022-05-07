(ns metaverse.common.reporter
  (:require
    [metaverse.common.env :as env]
    [metaverse.common.logger :as log :include-macros true]
    [metaverse.common.utils.string :as str]
    [tenet.response :as r]))


;; TODO: [2022-05-02, ilshat@sultanov.team] validate opts

(defn init!
  ([^js sentry]
   (init! sentry {}))
  ([^js sentry {:keys [dsn] :as opts}]
   (let [dsn (or dsn env/sentry-dsn)]
     (if (= "N/A" dsn)
       (do
         (log/error :msg "Sentry reporter is not initialized" :opts opts)
         (r/as-incorrect env/sentry-dsn))
       (let [version  (:version env/build-info)
             build    (-> env/build-info
                          (:metadata)
                          (assoc :version version))
             opts'    (-> {:dsn              env/sentry-dsn,
                           :environment      env/mode,
                           :tracesSampleRate 1.0,
                           :debug            env/develop?
                           :release          (str/format "%s@%s:%s" env/company-name env/product-name version)
                           :initialScope     {:build build}}
                          (merge opts))
             reporter (.init sentry (clj->js opts'))]
         (log/info :msg "Sentry reporter successfully initialized" :opts opts')
         (r/as-success reporter))))))
