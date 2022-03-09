(ns metaverse.runner.api
  (:require
    [metaverse.logger :as log :include-macros true]))


(defmulti dispatch
  (fn [_ipc-event command _event]
    (or command :default)))


(defmethod dispatch :default
  [_ command event]
  (log/error "Unknown command" command "event" event))
