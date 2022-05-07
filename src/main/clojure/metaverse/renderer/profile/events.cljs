(ns metaverse.renderer.profile.events
  (:require
    [re-frame.core :as rf]))


(rf/reg-event-fx
  :auth/sign-in.oauth->start
  (fn [_ [_ res]]
    (let [{:keys [url provider]} @res]
      (case provider
        "github" {:api/send [:open-url url]}
        {}))))


(rf/reg-event-fx
  :auth/sign-in.oauth->failure
  (fn [{db :db} _]
    ;; TODO: [2022-05-07, ilshat@sultanov.team] Show notification
    {:db                         (dissoc db :user)
     :dispatch                   [:set-readiness :auth :failed]
     :dispatch-later             [{:ms 1500, :dispatch [:navigation/redirect {:route-name :page/sign-in}]}]
     :local-storage/remove-items [:metaverse/user :metaverse/auth]}))


(rf/reg-event-fx
  :auth/sign-in.github
  (fn [_ _]
    {:dispatch   [:set-readiness :auth :loading]
     :api/invoke {:event      [:auth/sign-in {:provider    "github"
                                              :scopes      "email"
                                              :redirect-to "metaverse://app/oauth/github/callback"}]
                  :on-success [:auth/sign-in.oauth->start]
                  :on-failure [:auth/sign-in.oauth->failure]}}))


(rf/reg-event-fx
  :auth/sign-in.github->success
  (fn [_ [_ res]]
    (let [auth @res]
      ;; TODO: [2022-05-07, ilshat@sultanov.team] set-auth for supabase?
      {:dispatch               [:auth/user (:access-token auth)]
       :local-storage/set-item [:metaverse/auth auth]})))


(rf/reg-event-fx
  :auth/sign-out
  (fn [{db :db} _]
    {:db                         (dissoc db :user)
     :api/send                   [:auth/sign-out]
     :navigation/redirect        {:route-name :page/sign-in}
     :local-storage/remove-items [:metaverse/user :metaverse/auth]}))


(rf/reg-event-fx
  :auth/user->success
  (fn [{db :db} [_ res]]
    (let [user @res]
      ;; TODO: [2022-05-07, ilshat@sultanov.team] Redirect to previous page
      {:db                     (assoc db :user user)
       :dispatch               [:set-readiness :auth :ready]
       :dispatch-later         [{:ms 1500, :dispatch [:navigation/redirect {:route-name :page/home}]}]
       :local-storage/set-item [:metaverse/user @res]})))


(rf/reg-event-fx
  :auth/user->failure
  (fn [{db :db} _]
    ;; TODO: [2022-05-07, ilshat@sultanov.team] Show notification
    {:db                         (dissoc db :user)
     :dispatch                   [:set-readiness :auth :failed]
     :dispatch-later             [{:ms 1500, :dispatch [:navigation/redirect {:route-name :page/sign-in}]}]
     :local-storage/remove-items [:metaverse/user :metaverse/auth]}))


(rf/reg-event-fx
  :auth/user
  (fn [_ [_ access-token]]
    {:dispatch   [:set-readiness :auth :loading]
     :api/invoke {:event      [:auth/user access-token]
                  :on-success [:auth/user->success]
                  :on-failure [:auth/user->failure]}}))
