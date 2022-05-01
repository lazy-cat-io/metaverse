(ns metaverse.rss
  (:require
    ["feed-reader" :rename {read get-feed}]
    [cljs-bean.core :as bean]
    [metaverse.logger :as log :include-macros true]
    [promesa.core :as p]
    [tenet.response :as r]))


(defn fetch
  ([url]
   (fetch url {}))
  ([url {:keys [on-success on-failure]
         :or   {on-success identity
                on-failure identity}}]
   (log/info :msg "fetch rss" :url url)
   (-> (get-feed url)
       (p/then (fn [response]
                 (let [res (-> response (bean/->clj) (r/as-success))]
                   (log/info :msg "fetch rss successful" :url url)
                   (on-success res))))
       (p/catch (fn [error]
                  (let [res (-> error (bean/->clj) (r/as-error))]
                    (log/error :msg "fetch rss failed" :url url :error res)
                    (on-failure res)))))))
