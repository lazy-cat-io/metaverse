(ns metaverse.renderer.news.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  :news/readiness
  :<- [:app/readiness]
  (fn [readiness [_ key]]
    (get readiness key :idle)))


(rf/reg-sub
  :news
  (fn [db]
    (:news db)))


(rf/reg-sub
  :news/rss-channels
  :<- [:news]
  (fn [news]
    (or (:rss-channels news) [])))
