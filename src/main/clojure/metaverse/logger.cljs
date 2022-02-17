(ns metaverse.logger
  (:require
    [lambdaisland.glogi :as log]
    [lambdaisland.glogi.console :as console]
    [metaverse.env :as env]))


(defn init!
  "Logger initializer."
  ([]
   (init! env/logger-level))

  ([logger-level]
   (let [level (or logger-level :off)]
     (console/install!)
     (log/set-levels
       {:glogi/root level
        'metaverse  level})
     (log/info :msg "Logger successfully initialized" :level level))))
