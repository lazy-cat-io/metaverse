(ns metaverse.utils.transit
  (:require
    [cognitect.transit :as t]
    [tenet.response :as r]))


(deftype ResponseHandler
  []
  Object
  (tag [_ _x] "tenet")
  (rep [_ x] #js [(.-type x) (.-data x) (meta x)])
  (stringRep [_ _] nil))


(def writer
  (t/writer :json-verbose
            {:handlers
             {r/Response (ResponseHandler.)}}))


(def reader
  (t/reader :json
            {:handlers
             {"tenet" (fn [[type data meta]]
                        (-> data
                            (r/as-response type)
                            (with-meta meta)))}}))


(def write (partial t/write writer))
(def read  (partial t/read reader))


(defn then-write
  [^js promise]
  (.then promise #(write %)))


(defn then-read
  [^js promise]
  (.then promise #(read %)))
