(ns metaverse.downloader
  (:require
    [metaverse.common.logger :as log]
    [metaverse.common.supabase :as supabase]
    [metaverse.downloader.sentry :as sentry]))


(defn setup-tools
  []
  (log/init!)
  (sentry/init!)
  (supabase/init!))


(defn -main
  "Downloader entry point."
  {:export true}
  [& _args]
  (setup-tools)
  (.exit js/process 1))
