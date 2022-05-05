(ns metaverse.supabase
  (:require
    ["@supabase/supabase-js" :as supabase]
    [applied-science.js-interop :as j]
    [metaverse.env :as env]
    [metaverse.logger :as log :include-macros true]
    [metaverse.utils.bean :as b]
    [tenet.response :as r]))


(defonce *supabase (atom nil))


(defn create-client
  "Creates a supabase client."
  ([url key]
   (.createClient supabase url key))
  ([url key opts]
   (.createClient supabase url key (b/->js opts))))


(defn init!
  "Supabase initializer."
  ([]
   (init! {:url env/supabase-url, :key env/supabase-public-key}))
  ([{:keys [url key]}]
   (try
     (let [supabase (create-client url key)]
       (log/info :msg "Supabase successfully initialized" :url url)
       (reset! *supabase supabase)
       (r/as-success supabase))
     (catch :default e
       (log/error :msg "Failed to initialize the Supabase" :url url :error e)
       (r/as-error (b/bean e))))))



;;
;; Helpers
;;

(defn then-response
  [^js promise]
  (.then
    promise
    (fn [res]
      (let [error (j/get res :error)]
        (if error
          (r/as-error (b/bean (j/select-keys error [:message])))
          (r/as-success (b/bean res)))))))


(defn catch-error
  [^js promise]
  (.catch
    promise
    (fn [error]
      ;; TODO: [2022-05-03, ilshat@sultanov.team] Return only message?
      (r/as-error error))))


(defn then+catch
  [^js promise]
  (-> promise
      (then-response)
      (catch-error)))


;; TODO: [2022-05-03, ilshat@sultanov.team] Add DSL like honeysql?

(defn from
  [^js client from]
  (.from client from))


(defn select
  [^js client select]
  (.select client select))



;;
;; Auth
;;

(defn auth
  "Auth API"
  [method & args]
  (let [auth    (.-auth ^js @*supabase)
        args    (into-array (map b/->js args))
        auth-fn (j/get auth method)]
    (.apply auth-fn auth args)))


(defn auth:api
  "Auth API (server only)"
  [method & args]
  (let [api     (.-api (.-auth ^js @*supabase))
        args    (into-array (map b/->js args))
        auth-fn (j/get api method)]
    (.apply auth-fn api args)))


(def auth:get-user (comp then+catch (partial auth :getUser)))
(def auth:on-auth-state-change (comp then+catch (partial auth :onAuthStateChange)))
(def auth:reset-password-for-email (comp then+catch (partial auth :resetPasswordForEmail)))
(def auth:session (comp then+catch (partial auth :session)))
(def auth:set-auth (comp then+catch (partial auth :setAuth)))
(def auth:sign-in (comp then+catch (partial auth :signIn)))
(def auth:sign-out (comp then+catch (partial auth :signOut)))
(def auth:sign-up (comp then+catch (partial auth :signUp)))
(def auth:update (comp then+catch (partial auth :update)))
(def auth:user (partial auth :user))


;; Server only
(def auth:api:create-user (comp then+catch (partial auth:api :createUser)))
(def auth:api:delete-user (comp then+catch (partial auth:api :deleteUser)))
(def auth:api:generate-link (comp then+catch (partial auth:api :generateLink)))
(def auth:api:get-user-by-cookie (comp then+catch (partial auth:api :getUserByCookie))) ; check promise or not?
(def auth:api:invite-user-by-email (comp then+catch (partial auth:api :inviteUserByEmail)))
(def auth:api:send-mobile-otp (comp then+catch (partial auth:api :sendMobileOTP)))
(def auth:api:update-user-by-id (comp then+catch (partial auth:api :updateUserById)))



;;
;; RSS channel
;;

(defn api:rss-channel:get-list
  []
  (-> @*supabase
      (from "rss_channel")
      (select "*, user_profile ( first_name, last_name, middle_name )")
      (then+catch)))


(comment
  (-> (api:rss-channel:get-list)
      (.then (fn [res]
               (log/info :data @res))))
  )
