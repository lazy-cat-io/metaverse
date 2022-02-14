(ns metaverse.ui.router.core
  (:require
    [metaverse.logger :as log :include-macros true]
    [metaverse.ui.router.events]
    [metaverse.ui.router.subs]
    [re-frame.core :as rf]
    [reitit.coercion.malli :as rcm]
    [reitit.frontend :as rfr]
    [reitit.frontend.easy :as rfe]))


(def routes
  [""
   ["/" {:name :page/root, :private false}]
   ["/sign-in"
    ["/" {:name :page/sign-in, :private false}]
    ["/github" {:name :page/sign-in.github, :private false}]]
   ["/explore"
    ["" {:name :page/explore, :private false}]
    ["/trends" {:name :page/explore.trends, :private false}]
    ["/projects" {:name :page/explore.projects, :private false}]
    ["/docs" {:name :page/explore.docs, :private false}]
    ["/jobs" {:name :page/explore.jobs, :private false}]]
   ["/profile"
    ["/" {:name :page/profile, :private false}]
    ["/settings" {:name :page/profile.settings, :private false}]]
   ["/initializer"
    ["" {:name :page/initializer, :private false}]]])


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
