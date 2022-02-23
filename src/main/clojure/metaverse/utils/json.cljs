(ns metaverse.utils.json)


(defn parse-json
  [x]
  (js->clj x :keywordize-keys true))
