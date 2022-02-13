(ns metaverse.utils.os
  (:require
    ["os" :as os]))


(defn get-user-info
  []
  (-> (.. os (userInfo))
      (js->clj  :keywordize-keys true)))


(defn get-username
  []
  (:username (get-user-info)))
