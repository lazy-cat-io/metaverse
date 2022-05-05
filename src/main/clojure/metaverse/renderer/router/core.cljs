(ns metaverse.renderer.router.core
  (:require
    [metaverse.common.logger :as log :include-macros true]
    [metaverse.renderer.router.events]
    [metaverse.renderer.router.subs]
    [re-frame.core :as rf]
    [reitit.coercion.malli :as rcm]
    [reitit.frontend :as rfr]
    [reitit.frontend.easy :as rfe]))


(def routes
  [""
   ["/" {:name :page/home, :private false}]
   ["/sign-in"
    ["/" {:name :page/sign-in, :private false}]
    ["/github" {:name :page/sign-in.github, :private false}]]
   ["/news" {:name :page/news, :private false}]
   ["/projects"
    ["/" {:name :page/projects, :private false}]
    ["/trends" {:name :page/projects.trends, :private false}]]
   ["/jobs" {:name :page/jobs, :private false}]
   ["/docs" {:name :page/docs, :private false}]
   ["/initializer"
    ["" {:name :page/initializer, :private false}]]
   ["/profile"
    ["/" {:name :page/profile, :private false}]
    ["/settings" {:name :page/profile.settings, :private false}]]])


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
