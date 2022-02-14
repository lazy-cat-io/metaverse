(ns metaverse.runner.api
  (:require
    [metaverse.logger :as log :include-macros true]))


(defmulti dispatch
  (fn [_ipc-event event]
    (log/info "dispatch" event)
    (or (some-> event first keyword)
        :default)))


(defmethod dispatch :default
  [_ event]
  (log/error "Unknown event" event))


(defmethod dispatch :increment
  [_ [_ n]]
  (js/Promise.
    (fn [resolve _]
      (resolve (inc n)))))


(defmethod dispatch :decrement
  [_ [_ n]]
  (js/Promise.
    (fn [resolve _]
      (resolve (dec n)))))
