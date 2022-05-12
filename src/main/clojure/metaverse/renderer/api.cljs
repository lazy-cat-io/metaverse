(ns metaverse.renderer.api
  (:require
    [clojure.string :as str]
    [metaverse.common.utils.transit :as t]
    [re-frame.core :as rf]
    [tenet.response :as r]))


(defn init!
  []
  (.. js/window -bridge
      (dispatch (fn [_ipc-event event]
                  (let [event (t/read event)]
                    (rf/dispatch @event))))))


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


(rf/reg-event-fx
  :api/send
  (fn [_ [_ event]]
    {:api/send event}))


;; TODO: [2022-05-07, ilshat@sultanov.team] Check the callback URL of other OAuth providers
;; github: metaverse://app/oauth/github/callback#access_token=123...

(defn prepare-url
  [url]
  (some-> url
          (str/replace "metaverse://app" "")
          (str/replace "#" "?")))


(rf/reg-event-fx
  :open-url
  (fn [{db :db} [_ url]]
    (if-not (and (string? url)
                 (str/starts-with? url "metaverse://app"))
      {}
      {:navigation/redirect {:to     (prepare-url url)
                             :router (get-in db [:navigation :router])}})))
