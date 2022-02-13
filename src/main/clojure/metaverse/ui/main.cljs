(ns metaverse.ui.main
  (:require
    [goog.dom :as gdom]
    [re-frame.core :as rf]
    [reagent.core :as r]
    [reagent.dom :as dom]))


(defn app
  [counter]
  (let [f (fn [command]
            (fn []
              (let [n @counter]
                (js/console.log :command command)
                (js/console.log :click n)
                (js/console.log :bridge (.. js/window -bridge))
                (-> (.. js/window -bridge (dispatch command n))
                    (.then (fn [response]
                             (js/console.log :response response)
                             (reset! counter response)))
                    (.catch (fn [error]
                              (js/console.error :error error)))))))]
    (js/console.log :counter @counter)
    [:div {:class "p-4"}
     [:div {:class "flex"}
      [:div {:class "flex-1"}
       ""]
      [:div {:class "flex-1"}
       [:img {:src "assets/images/logotype.black.svg"}]]]
     [:div {:class "flex flex-col"}
      [:div {:class ""}
       [:button {:class    "mx-2 w-24 py-2 my-2 bg-gray-300 font-semibold rounded text-xs text-center dark:text-black hover:bg-gray-500 hover:text-white focus:outline-none"
                 :on-click (f "decrement")}
        "-"]
       [:button {:class    "w-24 py-2 my-2 bg-gray-300 font-semibold rounded text-xs text-center dark:text-black hover:bg-gray-500 hover:text-white focus:outline-none"
                 :on-click (f "increment")}
        "+"]]
      [:span @counter]]]))


(defn setup-tools
  "Setup tools."
  [])


(defn root
  []
  [app (r/atom 42)])


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (dom/render [root] root-elem)))


(defn init!
  "UI initializer."
  {:export true}
  []
  (setup-tools)
  (mount-root))
