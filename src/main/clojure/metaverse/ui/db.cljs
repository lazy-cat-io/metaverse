(ns metaverse.ui.db
  (:require
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [metaverse.ui.storage :as storage]
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
  [(rf/inject-cofx :local-storage :theme)]
  (fn-traced [{{theme :theme} :local-storage} _]
    {:db {:app {:initialized? false
                :theme        (or theme system-theme)}}
     :fx [[:dispatch-later {:ms 1000 :dispatch [:app/initialized]}]
          (when-not theme [:dispatch [:app/toggle-theme]])]}))



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

(defn next-theme
  [theme]
  (case theme
    "light" "dark"
    "dark" "light"
    "light"))


(rf/reg-fx
  :app/toggle-theme
  (fn-traced [{:theme/keys [current next]}]
    (.add (.. js/document -documentElement -classList) (name next))
    (.remove (.. js/document -documentElement -classList) (name current))
    (storage/set-item! :theme next)))


(rf/reg-event-fx
  :app/toggle-theme
  (fn-traced [{db :db} _]
    (let [current (get-in db [:app :theme] system-theme)
          next    (next-theme current)]
      {:db               (assoc-in db [:app :theme] next)
       :app/toggle-theme {:theme/current current
                          :theme/next    next}})))


(rf/reg-sub
  :app/theme
  (fn [db]
    (get-in db [:app :theme] system-theme)))
