(ns metaverse.runner.api
  (:require
    [metaverse.logger :as log :include-macros true]))


(defmulti dispatch
  (fn [_ipc-event event]
    (or (first event) :default)))


(defmethod dispatch :default
  [_ipc-event event]
  (log/error :msg "Unknown command" :event (first event)))
