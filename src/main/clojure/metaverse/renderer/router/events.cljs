(ns metaverse.renderer.router.events
  (:require
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [metaverse.common.logger :as log :include-macros true]
    [re-frame.core :as rf]
    [reitit.frontend :as rrf]
    [reitit.frontend.controllers :as rfc]
    [reitit.frontend.easy :as rfe]))


(rf/reg-event-db
  :navigation/set-router
  (fn-traced [db [_ router]]
    (assoc-in db [:navigation :router] router)))


;; FIXME: [2022-02-15, ilshat@sultanov.team] Change redirects after implementing business logic

(rf/reg-event-fx
  :navigation/set-route
  (fn-traced [{db :db} [_ route]]
    (let [initialized?   (get-in db [:app :initialized?])
          authenticated? (some? (:user db))
          private?       (get-in route [:data :private] true)]
      (cond
        ;; if app is not initialized
        (not initialized?)
        {:dispatch-later [{:ms 100, :dispatch [:navigation/set-route route]}]}

        ;; if route is not found
        (nil? route)
        (if-not authenticated?
          {:navigation/redirect {:route-name :page/sign-in}}
          {:navigation/redirect {:route-name :page/home}})

        ;; if route is private and user is not authenticated
        (and private? (not authenticated?))
        {:navigation/redirect {:route-name :page/sign-in}
         ;; FIXME: [2022-02-15, ilshat@sultanov.team] Add a translator and uncomment the code below
         ;; :notification        {:level           :error
         ;;                       :i18n/translator (get-in db [:i18n :translator])
         ;;                       :i18n/key        :auth/unauthorized}
         }

        ;; if route is not private or user is authenticated
        (or (not private?) authenticated?)
        (let [old-route        (get-in db [:navigation :route])
              old-controllers  (:controllers old-route)
              ;; if it is necessary to check user roles - add roles for the corresponding routes and add a check the user role before navigation
              next-controllers (rfc/apply-controllers old-controllers route)
              next-route       (assoc route :controllers next-controllers)]
          {:db (assoc-in db [:navigation :route] next-route)})

        ;; otherwise, redirect to home page
        :else {:navigation/redirect {:route-name :page/home}}))))


(defn resolve-route
  [router to]
  (let [match (rrf/match-by-path router to)]
    {:route-name   (get-in match [:data :name])
     :path-params  (:path-params match)
     :query-params (:query-params match)}))


(defn set-state!
  [f {:keys [router to] :as opts}]
  (if (and router to)
    (let [{:keys [route-name path-params query-params]} (resolve-route router to)]
      (log/trace :msg "Navigate" :route-name route-name)
      (f route-name path-params query-params))
    (let [{:keys [route-name path-params query-params]} opts]
      (log/trace :msg "Navigate" :route-name route-name)
      (f route-name path-params query-params))))


(rf/reg-fx
  :navigation/redirect
  (fn [opts]
    (set-state! rfe/push-state opts)))


(rf/reg-event-fx
  :navigation/redirect
  (fn-traced [{db :db} [_ {:keys [router to route-name path-params query-params]}]]
    (let [router (or router (get-in db [:navigation :router]))]
      {:navigation/redirect {:router router, :to to, :route-name route-name, :path-params path-params, :query-params query-params}})))


(rf/reg-fx
  :navigation/replace
  (fn [opts]
    (set-state! rfe/replace-state opts)))


(rf/reg-event-fx
  :navigation/replace
  (fn-traced [{db :db} [_ {:keys [router to route-name path-params query-params]}]]
    (let [router (or router (get-in db [:navigation :router]))]
      {:navigation/replace {:router router, :to to, :route-name route-name, :path-params path-params, :query-params query-params}})))
