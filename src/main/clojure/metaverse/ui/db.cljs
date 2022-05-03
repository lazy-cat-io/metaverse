(ns metaverse.ui.db
  (:require
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [metaverse.ui.profile.core]
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



;; Theme

(defn previous-theme
  [theme]
  (case theme
    "light" "dark"
    "dark" "light"
    system-theme))


(rf/reg-fx
  :app/set-theme
  (fn-traced [theme]
    (let [previous (previous-theme theme)]
      (when previous
        (.remove (.. js/document -documentElement -classList) (name previous)))
      (when theme
        (.add (.. js/document -documentElement -classList) (name theme))))))


(rf/reg-event-fx
  :app/set-theme
  (fn-traced [{db :db} [_ theme]]
    {:db                     (assoc-in db [:app :theme] theme)
     :app/set-theme          theme
     :local-storage/set-item [:metaverse/theme theme]}))


(rf/reg-sub
  :app/theme
  (fn [db]
    (get-in db [:app :theme])))
