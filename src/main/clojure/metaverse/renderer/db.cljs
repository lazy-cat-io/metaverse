(ns metaverse.renderer.db
  (:require
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [metaverse.renderer.news.core]
    [metaverse.renderer.profile.core]
    [re-frame.core :as rf]))


;;
;; Defaults
;;

(def system-theme
  (if (.. js/window (matchMedia "(prefers-color-scheme: dark)") -matches)
    "dark"
    "light"))



;;
;; Initial state
;;

;; FIXME: [2022-02-15, ilshat@sultanov.team]
;; - Add events to read user profile and tokens from the secret store

(rf/reg-event-fx
  ::init
  [(rf/inject-cofx :local-storage/get-items [:metaverse/theme :metaverse/user])]
  (fn-traced [{{:metaverse/keys [theme user]} :local-storage} _]
    (let [theme (or theme system-theme)]
      {:db {:app  {:initialized? false}
            :user user}
       :fx [[:dispatch-later {:ms 1000 :dispatch [:app/initialized]}]
            [:dispatch [:app/set-theme theme]]]})))



;; Initialization

(rf/reg-event-db
  :app/initialized
  (fn [db _]
    (assoc-in db [:app :initialized?] true)))


(rf/reg-sub
  :app/initialized?
  (fn [db]
    (get-in db [:app :initialized?] false)))


;; Readiness

(rf/reg-sub
  :app/readiness
  (fn [db]
    (get-in db [:app :readiness])))


(rf/reg-event-fx
  :set-readiness
  (fn [{db :db} [_ key state]]
    (case state
      :ready {:db             (assoc-in db [:app :readiness key] state)
              :dispatch-later [{:ms 1500, :dispatch [:set-readiness key nil]}]}
      nil {:db (update-in db [:app :readiness] dissoc key)}
      {:db (assoc-in db [:app :readiness key] state)})))


;; Theme

(defn toggle-theme
  [theme]
  (case theme
    "light" "dark"
    "dark" "light"
    system-theme))


(rf/reg-fx
  :app/set-theme
  (fn-traced [next-theme]
    (let [previous-theme (toggle-theme next-theme)]
      (when previous-theme
        (.remove (.. js/document -documentElement -classList) (name previous-theme)))
      (when next-theme
        (.add (.. js/document -documentElement -classList) (name next-theme))))))


(rf/reg-event-fx
  :app/set-theme
  (fn-traced [{db :db} [_ theme]]
    {:db                     (assoc-in db [:app :theme] theme)
     :app/set-theme          theme
     :local-storage/set-item [:metaverse/theme theme]}))


(rf/reg-event-fx
  :app/toggle-theme
  (fn-traced [{db :db} _]
    (let [current-theme (get-in db [:app :theme])
          next-theme    (toggle-theme current-theme)]
      {:dispatch [:app/set-theme next-theme]})))


(rf/reg-sub
  :app/theme
  (fn [db]
    (get-in db [:app :theme] system-theme)))

