(ns metaverse.runner.api
  (:require
    [metaverse.logger :as log :include-macros true]))


(defmulti dispatch
  (fn [_ipc-event command event]
    (log/info "dispatch" command "event" event)
    (or command :default)))


(defmethod dispatch :default
  [_ command event]
  (log/error "Unknown command" command "event" event))


(defmethod dispatch :increment
  [_ _ n]
  (js/Promise.
    (fn [resolve _]
      (resolve (inc n)))))


(defmethod dispatch :decrement
  [_ _ n]
  (js/Promise.
    (fn [resolve _]
      (resolve (dec n)))))
