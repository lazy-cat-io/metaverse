(ns metaverse.runner.api.auth
  (:require
    [applied-science.js-interop :as j]
    [metaverse.logger :as log :include-macros true]
    [metaverse.runner.api :as api]
    [metaverse.supabase :as supabase]))


(defmulti sign-in
  (fn [{:keys [provider email password]}]
    (cond
      provider :provider
      (and email password) :email
      :else :default)))


;; TODO: [2022-03-10, ilshat@sultanov.team] return error?
(defmethod sign-in :default
  [_ _ opts]
  (log/error "Unknown provider" opts))


(defmethod sign-in :email
  [{:keys [email password]}]
  (js/Promise.
    (fn [resolve reject]
      (-> (supabase/auth:sign-in {:email email, :password password})
          (.then (fn [response]
                   (log/info :sign-in response)
                   (resolve (supabase/auth:user))))
          (.catch (fn [error]
                    (log/error :sign-in error)
                    (reject error)))))))


(defmethod sign-in :provider
  [{:keys [provider]}]
  (js/Promise.
    (fn [resolve reject]
      (-> (supabase/auth:sign-in {:provider provider})
          (.then (fn [response]
                   (if-some [url (j/get response :url)]
                     (log/info :sign-in response)
                     (log/error :sign-in response))))
          (.catch (fn [error]
                    (log/error :sign-in error)
                    (reject error)))))))


(defn sign-out
  []
  (js/Promise.
    (fn [resolve reject]
      (-> (supabase/auth:sign-out)
          (.then (fn [response]
                   (log/info :sign-out response)
                   (resolve response)))
          (.catch (fn [error]
                    (log/error :sign-out error)
                    (reject error)))))))


;;
;; Public API
;;

(defmethod api/dispatch :auth/sign-in [_ _ opts] (sign-in opts))
(defmethod api/dispatch :auth/sign-out [_ _ _] (sign-out))
