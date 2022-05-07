(ns metaverse.runner.api
  (:require
    [metaverse.common.logger :as log :include-macros true]
    [metaverse.runner.shell :as shell]
    [tenet.response :as r]))


(defmulti invoke
  (fn [_ipc-event event]
    (or (first event) :incorrect)))


(defmethod invoke :incorrect
  [_ipc-event [event-id & _]]
  (let [message "Unknown event id"]
    (log/error :msg message  :event event-id)
    (js/Promise
      (fn [resolve _reject]
        (resolve (r/as-incorrect {:message message, :event-id event-id}))))))


(defmethod invoke :open-url
  [_ipc-event [_ url]]
  (some->> url (shell/open-external)))
