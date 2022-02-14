(ns metaverse.ui.main
  (:require
    [goog.dom :as gdom]
    [metaverse.logger :as logger]
    [metaverse.ui.api :as api]
    [metaverse.ui.db :as db]
    [metaverse.ui.router.core :as router]
    [re-frame.core :as rf]
    [reagent.dom :as dom]))


(rf/reg-sub
  :counter
  (fn [db]
    (get db :counter 0)))


(rf/reg-event-db
  :increment->success
  (fn [db [_ n]]
    (assoc db :counter n)))


(rf/reg-event-db
  :increment->failure
  (fn [db [_ error]]
    (js/console.log :increment->failure error)
    db))


(rf/reg-event-fx
  :increment
  (fn [_ [_ n]]
    {::api/dispatch {:event      [:increment n]
                     :on-success [:increment->success]
                     :on-failure [:increment->failure]}}))


(rf/reg-event-db
  :decrement->success
  (fn [db [_ n]]
    (assoc db :counter n)))


(rf/reg-event-db
  :decrement->failure
  (fn [db [_ error]]
    (js/console.log :decrement->failure error)
    db))


(rf/reg-event-fx
  :decrement
  (fn [_ [_ n]]
    {::api/dispatch {:event      [:decrement n]
                     :on-success [:decrement->success]
                     :on-failure [:decrement->failure]}}))


(defn app
  [counter]
  [:div {:class "p-4"}
   [:div {:class "flex"}
    [:div {:class "flex-1"}
     ""]
    [:div {:class "flex-1"}
     [:img {:src "assets/images/logotype.black.svg"}]]]
   [:div {:class "flex flex-col"}
    [:div {:class ""}
     [:button {:class    "mx-2 w-24 py-2 my-2 bg-gray-300 font-semibold rounded text-xs text-center dark:text-black hover:bg-gray-500 hover:text-white focus:outline-none"
               :on-click #(rf/dispatch [:decrement counter])}
      "-"]
     [:button {:class    "w-24 py-2 my-2 bg-gray-300 font-semibold rounded text-xs text-center dark:text-black hover:bg-gray-500 hover:text-white focus:outline-none"
               :on-click #(rf/dispatch [:increment counter])}
      "+"]]
    [:span counter]]])


(defn setup-tools
  "Setup tools."
  []
  (logger/init!))


(defn root
  []
  [app @(rf/subscribe [:counter])])


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (router/init!)
    (dom/render [root] root-elem)))


(defn init!
  "UI initializer."
  {:export true}
  []
  (setup-tools)
  (rf/dispatch-sync [::db/init])
  (mount-root))
