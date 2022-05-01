(ns metaverse.rss
  (:require
    ["feed-reader" :rename {read get-feed}]
    [metaverse.logger :as log :include-macros true]
    [promesa.core :as p]
    [tenet.response :as r]))


(defn fetch
  ([url]
   (fetch url {}))
  ([url {:keys [on-success on-failure]
         :or   {on-success identity
                on-failure identity}}]
   (log/info :msg "fetch rss" :url url)
   (-> (get-feed url)
       (p/then (fn [response]
                 (log/info :msg "fetch rss successful" :url url)
                 (on-success (r/as-success response))))
       (p/catch (fn [error]
                  (log/error :msg "fetch rss failed" :url url :error error)
                  (on-failure (r/as-error error)))))))
