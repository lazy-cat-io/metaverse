(ns metaverse.runner.menu
  (:require
    [metaverse.env :as env]
    [metaverse.runner.config :as config]
    [metaverse.runner.electron :as electron]
    [metaverse.runner.window :as window]))


(defn set-application-menu
  [menu]
  (.setApplicationMenu ^js/electron.Menu electron/Menu menu))


(defn build-from-template
  [menu]
  (.buildFromTemplate ^js/electron.Menu electron/Menu (clj->js menu)))


(defn create-menu
  [window]
  (build-from-template
    [{:label   config/title
      :submenu (cond-> [{:role "about"}
                        {:type "separator"}
                        {:role "hide"}
                        {:role "hideOthers"}
                        {:role "unhide"}]
                 env/develop? (conj {:type "separator"}
                                    {:role        "Toggle DevTools"
                                     :accelerator "Alt+CommandOrControl+I"
                                     :click       #(window/toggle-devtools window)})
                 :always (conj {:type "separator"}
                               {:role "quit"}))}]))
