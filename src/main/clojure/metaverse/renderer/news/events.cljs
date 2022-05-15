(ns metaverse.renderer.news.events
  (:require
    [metaverse.common.logger :as log :include-macros true]
    [re-frame.core :as rf]))


(rf/reg-event-fx
  :news/rss-channels->success
  (fn [{db :db} [_ rss-channels]]
    (log/info :news/rss-channels->success rss-channels)
    {:db       (assoc-in db [:news :rss-channels] @rss-channels)
     :dispatch [:set-readiness :news/rss-channels :ready]}))


(rf/reg-event-fx
  :news/rss-channels->failure
  (fn [{db :db} [_ error]]
    ;; TODO: [2022-05-15, ilshat@sultanov.team] Show notification
    (log/info :news/rss-channels->failure error)
    {:db       (update db :news dissoc :rss-channels)
     :dispatch [:set-readiness :news/rss-channels :failed]}))


(rf/reg-event-fx
  :news/rss-channels
  (fn [{db :db} _]
    {:db         (update db :news dissoc :rss-channels)
     :dispatch   [:set-readiness :news/rss-channels :loading]
     :api/invoke {:event      [:news/rss-channels]
                  :on-success [:news/rss-channels->success]
                  :on-failure [:news/rss-channels->failure]}}))
