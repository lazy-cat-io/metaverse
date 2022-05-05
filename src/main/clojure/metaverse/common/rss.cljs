(ns metaverse.common.rss
  (:require
    ["feed-reader" :rename {read get-feed}]
    [metaverse.common.logger :as log :include-macros true]
    [tenet.response :as r]))


(defn fetch
  ([url]
   (fetch url {}))
  ([url {:keys [on-success on-failure]
         :or   {on-success identity
                on-failure identity}}]
   (log/debug :msg "RSS feed downloading has started" :url url)
   (-> (get-feed url)
       (.then (fn [response]
                (log/debug :msg "RSS feed was downloaded successfully" :url url)
                (on-success (r/as-success response))))
       (.catch (fn [error]
                 (log/error :msg "RSS feed was downloaded with an error" :url url :error error)
                 (on-failure (r/as-error error)))))))
