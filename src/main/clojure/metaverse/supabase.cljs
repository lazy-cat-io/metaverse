(ns metaverse.supabase
  (:require
    ["@supabase/supabase-js" :as supabase]
    [applied-science.js-interop :as j]
    [cljs-bean.core :as bean]
    [metaverse.electron.refs :as refs]
    [metaverse.env :as env]
    [metaverse.logger :as log :include-macros true]))


(defn create-client
  "Creates a supabase client."
  ([url key]
   (.createClient supabase url key))
  ([url key opts]
   (.createClient supabase url key (bean/->js opts))))


(defn init!
  "Supabase initializer."
  []
  (try
    (let [supabase (create-client env/supabase-url env/supabase-public-key)]
      (log/info :msg "Supabase successfully initialized")
      (reset! refs/*supabase supabase))
    (catch :default e
      (log/error :msg "Failed to initialize the Supabase" :error e))))



;;
;; Auth
;;

(defn auth
  "Auth API"
  [method & args]
  (let [auth   (.-auth ^js @refs/*supabase)
        args   (into-array (map bean/->js args))
        method (j/get auth method)]
    (.apply method auth args)))


(defn auth:api
  "Auth API (server only)"
  [method & args]
  (let [api    (.-api (.-auth ^js @refs/*supabase))
        args   (into-array (map bean/->js args))
        auth-fn (j/get api method)]
    (.apply auth-fn api args)))


(def auth:sign-up (partial auth :signUp))
(def auth:sign-in (partial auth :signIn))
(def auth:sign-out (partial auth :signOut))
(def auth:session (partial auth :session))
(def auth:user (partial auth :user))
(def auth:update (partial auth :update))
(def auth:set-auth (partial auth :setAuth))
(def auth:on-auth-state-change (partial auth :onAuthStateChange))
(def auth:get-user (partial auth :getUser))
(def auth:reset-password-for-email (partial auth :resetPasswordForEmail))

(def auth:api:create-user (partial auth:api :createUser))
(def auth:api:delete-user (partial auth:api :deleteUser))
(def auth:api:generate-link (partial auth:api :generateLink))
(def auth:api:invite-user-by-email (partial auth:api :inviteUserByEmail))
(def auth:api:send-mobile-otp (partial auth:api :sendMobileOTP))
(def auth:api:update-user-by-id (partial auth:api :updateUserById))
