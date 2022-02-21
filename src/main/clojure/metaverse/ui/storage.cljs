(ns metaverse.ui.storage
  (:require
    [cljs.reader :as reader]
    [re-frame.core :as rf]))


;; TODO: [2022-02-22, ilshat@sultanov.team] serialize/deserialize using clj->js and js->clj

(defn set-item!
  [key value]
  (.setItem js/localStorage (name key) value))


(defn get-item
  [key]
  (-> (.getItem js/localStorage (name key))
      (reader/read-string)))


(defn remove-item!
  [key]
  (.removeItem js/localStorage (name key)))



(rf/reg-cofx
  :local-storage
  (fn [cofx key]
    (->> key
         (get-item)
         (assoc-in cofx [:local-storage key]))))
