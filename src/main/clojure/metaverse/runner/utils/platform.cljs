(ns metaverse.runner.utils.platform)


(def platform js/process.platform)
(def mac-os? (= "darwin" platform))
