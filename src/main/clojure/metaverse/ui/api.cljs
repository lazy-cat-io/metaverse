(ns metaverse.ui.api
  (:require
    [re-frame.core :as rf]))


(rf/reg-fx
  ::dispatch
  (fn [{:keys [event on-success on-failure]}]
    (js/console.log :event event)
    (-> (.. js/window -bridge (dispatch (clj->js event)))
        (.then (fn [response]
                 (js/console.log :response response)
                 (rf/dispatch (conj on-success response))))
        (.catch (fn [error]
                  (js/console.error :error error)
                  (rf/dispatch (conj on-failure error)))))))
