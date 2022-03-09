(ns metaverse.ui.api
  (:require
    [cljs-bean.core :as bean]
    [re-frame.core :as rf]))


(rf/reg-fx
  :api/dispatch
  (fn [{:keys [event on-success on-failure]}]
    (-> (.. js/window -bridge (dispatch (bean/->js event)))
        (.then #(rf/dispatch (conj on-success (bean/bean % :recursive true))))
        (.catch #(rf/dispatch (conj on-failure (bean/bean % :recursive true)))))))
