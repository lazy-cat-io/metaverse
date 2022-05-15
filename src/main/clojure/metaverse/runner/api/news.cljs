(ns metaverse.runner.api.news
  (:require
    [metaverse.common.logger :as log :include-macros true]
    [metaverse.common.supabase :as supabase]
    [metaverse.runner.api :as api]
    [tenet.response :as r]))


(defn rss-channels:get-list
  []
  (-> (supabase/api:rss-channels:get-list)
      (.then (fn [res]
               (log/info :res res)
               (if (r/anomaly? res)
                 res
                 (update res :data :data))))))


;;
;; Public API
;;

(defmethod api/invoke :news/rss-channels [_ _] (rss-channels:get-list))



(comment
  ;; rss channels
  (-> (rss-channels:get-list)
      (.then (fn [res]
               (log/info :rss-channels:get-list res))))
  )
