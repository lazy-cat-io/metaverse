(ns metaverse.runner.api.auth
  (:require
    [applied-science.js-interop :as j]
    [metaverse.logger :as log :include-macros true]
    [metaverse.runner.api :as api]
    [metaverse.supabase :as supabase]
    [tenet.response :as r]))


(defmulti sign-in
  (fn [{:keys [provider email password]}]
    (cond
      provider :provider
      (and email password) :email
      :else :incorrect)))


(defmethod sign-in :incorrect
  [_]
  (let [message "Unknown provider"]
    (log/error :msg message)
    (js/Promise.
      (fn [resolve _reject]
        (resolve (r/as-incorrect {:message message}))))))



(defmethod sign-in :email
  [{:keys [email password]}]
  (-> {:email email, :password password}
      (supabase/auth:sign-in)
      (.then (fn [res]
               (if (r/anomaly? res)
                 res
                 (update res :data #(j/get % :data)))))))


(defmethod sign-in :provider
  [{:keys [provider]}]
  (-> {:provider provider}
      (supabase/auth:sign-in)
      (.then (fn [res]
               (if (r/anomaly? res)
                 res
                 ;; TODO: [2022-05-03, ilshat@sultanov.team] Open a window for signing in using the provider
                 (update res :data #(j/select-keys % [:url :provider])))))))


(defn sign-out
  []
  (-> (supabase/auth:sign-out)
      (.then (fn [_]
               (r/as-success)))))


;;
;; Public API
;;

(defmethod api/dispatch :auth/sign-in [_ [_ credentials]] (sign-in credentials))
(defmethod api/dispatch :auth/sign-out [_ _] (sign-out))



(comment
  ;; sign-up
  (-> (supabase/auth:sign-up {:email "john@doe.com", :password "p4$$w0rd"})
      (.then (fn [res]
               (log/info :sign-up res))))

  ;; wrong password
  (-> (sign-in {:email "john@doe.com", :password "p4$$w0rd1"})
      (.then (fn [res]
               (log/info :sign-in res))))

  ;; correct password
  (-> (sign-in {:email "john@doe.com", :password "p4$$w0rd"})
      (.then (fn [res]
               (log/info :sign-in res))))

  ;; sign-out
  (-> (sign-out)
      (.then (fn [res]
               (log/info :sign-out res))))

  ;; sign-in via github
  (-> (sign-in {:provider "github"})
      (.then (fn [res]
               (log/info :sign-in res))))
  )
