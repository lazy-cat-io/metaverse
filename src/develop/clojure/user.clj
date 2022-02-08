(ns user
  "Development helper functions."
  (:require
    [shadow.cljs.devtools.api :as shadow]))


(defn cljs-repl
  ([]
   (cljs-repl :app))
  ([build-id]
   (shadow/repl build-id)))
