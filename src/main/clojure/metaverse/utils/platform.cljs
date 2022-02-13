(ns metaverse.utils.platform)


(def ^:const platform js/process.platform)
(def ^:const mac-os? (= "darwin" platform))
