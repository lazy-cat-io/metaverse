(ns metaverse.ui.profile.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  :user
  (fn [db]
    (:user db)))
