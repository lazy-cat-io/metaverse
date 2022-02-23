(ns metaverse.fetch
  (:refer-clojure :exclude [get])
  (:require
    ["node-fetch" :as node-fetch]
    [lambdaisland.fetch :as fetch]
    [lambdaisland.fetch.edn]))


(set! js/fetch node-fetch)


(defn get
  [{:keys [url on-success on-failure]} opts]
  (-> (fetch/get url opts)
      (.then on-success)
      (.catch on-failure)))
