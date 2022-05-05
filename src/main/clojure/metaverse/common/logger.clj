(ns metaverse.common.logger
  (:require
    [lambdaisland.glogi :as log]))


(defmacro error
  [& keyvals]
  (#'log/log-expr &form :error keyvals))


(defmacro warn
  [& keyvals]
  (#'log/log-expr &form :warn keyvals))


(defmacro info
  [& keyvals]
  (#'log/log-expr &form :info keyvals))


(defmacro debug
  [& keyvals]
  (#'log/log-expr &form :debug keyvals))


(defmacro trace
  [& keyvals]
  (#'log/log-expr &form :trace keyvals))
