(ns metaverse.downloader
  (:require
    [metaverse.downloader.reporter :as reporter]
    [metaverse.logger :as log]
    [metaverse.supabase :as supabase]))


(defn setup-tools
  []
  (log/init!)
  (reporter/init!)
  (supabase/init!))


(defn init!
  "Downloader initializer."
  {:export true}
  [& _args]
  (setup-tools)
  (.exit js/process 1))
