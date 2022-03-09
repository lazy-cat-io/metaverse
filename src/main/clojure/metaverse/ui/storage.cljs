(ns metaverse.ui.storage
  (:require
    [cljs-bean.core :as bean]
    [metaverse.utils.string :refer [keyword->string]]
    [re-frame.core :as rf]))


(defn set-item!
  [key item]
  (when-some [key (keyword->string key)]
    (let [item (.stringify js/JSON (bean/->js item))]
      (.setItem js/localStorage key item))))


(defn set-items!
  [pairs]
  (run! set-item! pairs))



(defn get-item
  [key]
  (when-some [key (keyword->string key)]
    (let [item (.getItem js/localStorage key)]
      (bean/->clj (.parse js/JSON item)))))


(defn get-items
  [ks]
  (reduce
    (fn [acc key]
      (assoc acc key (get-item key)))
    {} ks))



(defn remove-item!
  [key]
  (when-some [key (keyword->string key)]
    (.removeItem js/localStorage key)))


(defn remove-items!
  [ks]
  (run! remove-item! ks))



(rf/reg-cofx
  :local-storage/get-item
  (fn [cofx key]
    (->> key
         (get-item)
         (assoc-in cofx [:local-storage key]))))


(rf/reg-cofx
  :local-storage/get-items
  (fn [cofx ks]
    (->> ks
         (get-items)
         (assoc cofx :local-storage))))



(rf/reg-fx
  :local-storage/set-item
  (fn [[key item]]
    (set-item! key item)))


(rf/reg-fx :local-storage/set-items set-items!)


(rf/reg-fx :local-storage/remove-item remove-item!)
(rf/reg-fx :local-storage/remove-items remove-items!)
