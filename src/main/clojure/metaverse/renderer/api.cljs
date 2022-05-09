(ns metaverse.renderer.api
  (:require
    [clojure.string :as str]
    [metaverse.common.logger :as log :include-macros true]
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


(rf/reg-event-fx
  :auto-updater/checking-for-update
  (fn [_ _]
    ;; as-success
    (log/info :auto-updater/checking-for-update nil)
    {}))


(rf/reg-event-fx
  :auto-updater/update-available
  (fn [_ [_ info]]
    ;; as-found
    (log/info :auto-updater/checking-for-update info)
    {}))


(rf/reg-event-fx
  :auto-updater/update-not-available
  (fn [_ [_ info]]
    ;; as-unavailable
    (log/info :auto-updater/update-not-available info)
    {}))


(rf/reg-event-fx
  :auto-updater/error
  (fn [_ [_ error]]
    ;; as-error
    (log/info :auto-updater/error error)
    {}))


(rf/reg-event-fx
  :auto-updater/download-progress
  (fn [_ [_ progress]]
    ;; as-success
    (log/info :auto-updater/download-progress progress)
    {}))


(rf/reg-event-fx
  :auto-updater/update-downloaded
  (fn [_ [_ info]]
    ;; as-success
    (log/info :auto-updater/update-downloaded info)
    {}))
