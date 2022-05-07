(ns metaverse.renderer.profile.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  :auth/readiness
  :<- [:app/readiness]
  (fn [readiness]
    (or (:auth readiness) :idle)))


(rf/reg-sub
  :user
  (fn [db]
    (:user db)))
