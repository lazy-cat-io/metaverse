(ns metaverse.renderer.router.core
  (:require
    [clojure.set :as set]
    [metaverse.common.logger :as log :include-macros true]
    [metaverse.renderer.router.events]
    [metaverse.renderer.router.subs]
    [re-frame.core :as rf]
    [reitit.coercion.malli :as rcm]
    [reitit.frontend :as rfr]
    [reitit.frontend.easy :as rfe]
    [tenet.response :as r]))


(defmulti prepare-parameters
  (fn [parameters]
    (get-in parameters [:path :provider])))


(defmethod prepare-parameters "github"
  [{:keys [query]}]
  (-> query
      (set/rename-keys
        {:access_token   :access-token
         :expires_in     :expires-in
         :provider_token :provider-token
         :refresh_token  :refresh-token
         :token_type     :token-type})
      (update :expires_in (fnil #(js/parseInt %) "3600"))))


(def routes
  [""
   ["/" {:name :page/home, :private false}]
   ["/sign-in" {:name :page/sign-in, :private false}]
   ["/sign-out" {:name        :page/sign-out
                 :private     true
                 :controllers [{:start #(rf/dispatch [:auth/sign-out])}]}]
   ["/news" {:name :page/news, :private false}]
   ["/projects" {:name :page/projects, :private false}]
   ["/jobs" {:name :page/jobs, :private false}]
   ["/docs" {:name :page/docs, :private false}]
   ["/initializer" {:name :page/initializer, :private false}]
   ["/profile"
    ["/" {:name :page/profile, :private true}]
    ["/subscriptions" {:name :page/profile.subscriptions, :private true}]]
   ["/oauth/:provider/callback"
    {:name        :page/oauth.provider.callback
     :private     false
     :controllers [{:parameters {:path  [:provider]
                                 :query [:access_token :expires_in :provider_token :refresh_token :token_type]}
                    :start      (fn [{:keys [path] :as parameters}]
                                  (case (:provider path)
                                    ;; TODO: [2022-05-07, ilshat@sultanov.team] Handle error response from GitHub
                                    "github" (rf/dispatch [:auth/sign-in.github->success (r/as-success (prepare-parameters parameters))])
                                    (rf/dispatch [:auth/sign-in.oauth->failure (r/as-error {:message "Unknown OAuth provider"})])))}]}]])


(def router
  (rfr/router
    routes
    {:data {:coercion rcm/coercion, :private true}}))


(defn on-navigate
  "Router `on-navigate` entry point. This function to be called when route changes."
  [matched-route]
  (rf/dispatch [:navigation/set-route matched-route]))


(defn init!
  "Router initializer."
  []
  (rfe/start!
    router
    on-navigate
    {:use-fragment true})
  (rf/dispatch-sync [:navigation/set-router router])
  (log/info :msg "Router successfully initialized"))
