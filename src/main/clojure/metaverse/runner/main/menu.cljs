(ns metaverse.runner.main.menu
  (:require
    [metaverse.electron.menu :as menu]
    [metaverse.electron.window :as window]
    [metaverse.env :as env]
    [metaverse.runner.config :as config]
    [metaverse.utils.platform :as platform]))


(defn create-menu
  [_window]
  (menu/build-from-template
    (cond-> [{:label   config/title
              :submenu [{:role "about"}
                        {:type "separator"}
                        {:role "hide"}
                        {:role "hideOthers"}
                        {:role "unhide"}
                        {:type "separator"}
                        {:role "quit"}]}]

      env/develop? (conj {:label   "Developer"
                          :submenu [{:role "reload"}
                                    {:label       "Toggle Developer Tools"
                                     :accelerator (if platform/mac-os? "Command+Alt+I" "Control+Alt+I")
                                     :click       (fn [_item focused-window]
                                                    (window/toggle-devtools focused-window #js {:mode "detach"}))}]}))))
