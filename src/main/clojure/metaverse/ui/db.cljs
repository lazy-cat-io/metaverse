(ns metaverse.ui.db
  (:require
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [re-frame.core :as rf]))


;;
;; Initial state
;;

;; FIXME: [2022-02-15, ilshat@sultanov.team] Add events to read user profile and tokens from the secret store
(rf/reg-event-fx
  ::init
  (fn-traced [_ _]
    (let [db     {:app {:initialized? true}}
          events []]
      {:db         db
       :dispatch-n events})))


(rf/reg-sub
  :app/initialized?
  (fn [db]
    (-> db
        (get-in [:app :initialized?])
        (boolean))))
