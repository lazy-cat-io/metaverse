(ns metaverse.clojuredocs.api
  (:require
    [metaverse.env :as env]
    [metaverse.fetch :as fetch]
    [metaverse.utils.fs :as fs]
    [metaverse.utils.json :as json]
    [metaverse.utils.os :as os]
    [metaverse.utils.path :as path]))


(def clojuredocs-export-path
  (path/join (os/home-dir) ".metaverse" "data" "clojuredocs.edn"))



(defn fetch-data
  [{:keys [on-success on-failure]}]
  (fetch/get
    {:url        env/clojuredocs-export-url
     :on-success (comp on-success json/parse-json :body)
     :on-failure (comp on-failure json/parse-json :error)}
    {:accept :json}))



(defn download
  []
  (fs/mkdir (path/dir-name clojuredocs-export-path)
            (fn [error]
              (if error
                (js/console.log "mkdir error" error)
                (js/console.log "mkdir success" error))))
  (fetch-data
    {:on-success (fn [data]
                   (fs/write-file clojuredocs-export-path (pr-str data)
                                  (fn [error]
                                    (if error
                                      (do
                                        (js/console.log "fetch-success error" error)
                                        (js/console.log "write-file error" error))
                                      (do
                                        (js/console.log "fetch success" error)
                                        (js/console.log "write-file success" error))))))
     :on-failure (fn [error]
                   (js/console.log "fetch error" error))}))



(comment

  (download)
  )


