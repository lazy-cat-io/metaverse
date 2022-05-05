(ns metaverse.ui.api
  (:require
    [metaverse.utils.transit :as t]
    [re-frame.core :as rf]
    [tenet.response :as r]))


(rf/reg-fx
  :api/invoke
  (fn [{:keys [event on-success on-failure]}]
    (-> (.. js/window -bridge (invoke (t/write event)))
        (t/then-read)
        (.then (fn [res]
                 (if (r/anomaly? res)
                   (rf/dispatch (conj on-failure res))
                   (rf/dispatch (conj on-success res)))))
        (.catch (fn [error]
                  (rf/dispatch (conj on-failure error)))))))


(rf/reg-fx
  :api/send
  (fn [event]
    (.. js/window -bridge (send (t/write event)))))
