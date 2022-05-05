(ns metaverse.runner.utils.fs
  (:require
    ["fs" :as fs]))


(def no-op (constantly :no-op))


(defn write-file-sync
  ([file data]
   (write-file-sync file data "utf-8"))
  ([file data options]
   (try
     (.writeFileSync fs file data options)
     (catch :default _e))))


(defn write-file
  ([file data callback]
   (write-file file data "utf-8" callback))
  ([file data options callback]
   (try
     (.writeFile fs file data options callback)
     (catch :default _e))))


(defn mkdir
  ([path]
   (mkdir path nil))
  ([path callback]
   (mkdir path nil callback))
  ([path options callback]
   (let [options (or options #js {:recursive true})
         callback (or callback no-op)]
     (.mkdir fs path options callback))))
