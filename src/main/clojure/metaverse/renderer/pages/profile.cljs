(ns metaverse.renderer.pages.profile
  (:require
    [heroicons.outline :as icons.outline]
    [re-frame.core :as rf]))


(def links
  {:page/profile               {:label "Profile"
                                :href  :page/profile
                                :icon  icons.outline/user-icon}
   :page/profile.subscriptions {:label "Subscriptions"
                                :href  :page/profile.subscriptions
                                :icon  icons.outline/rss-icon}})


(defn navbar
  [route-name]
  (into [:div.flex.flex-col.w-40.px-2.gap-2]
        (for [{:keys [label href icon]} (vals links)]
          (let [active? (= route-name href)]
            [:a.flex.gap-2.items-center.px-4.py-2.text-sm.rounded-md.shadow-md
             {:class (if active?
                       "text-gray-100 hover:text-white bg-violet-600 dark:text-gray-100 dark:hover:text-white dark:bg-violet-600"
                       "text-gray-600 hover:text-gray-900 bg-white dark:text-gray-800 dark:hover:text-gray-900 dark:bg-gray-300")
              :href  @(rf/subscribe [:href href])}
             [icon {:class "w-6 h-6"}]
             [:span label]]))))


(defn profile
  []
  (when-some [{:as _user :keys [user_metadata]} @(rf/subscribe [:user])]
    [:div
     [:div.flex.flex-row.gap-5
      [:div {:class "py-5"}
       [:label {:htmlFor "full-name" :class "block text-sm font-medium text-gray-500"}
        "Full name"]
       [:div {:class "mt-1 relative rounded-md shadow-sm"}
        [:input#full-name {:type     "text"
                           :name     "full-name"
                           :class    "block w-full h-12 pl-7 pr-12 text-gray-700 border border-gray-300 rounded-md"
                           :value    (:full_name user_metadata)
                           :disabled true}]]]

      [:div {:class "py-5"}
       [:label {:htmlFor "email" :class "block text-sm font-medium text-gray-500"}
        "Email"]
       [:div {:class "mt-1 relative rounded-md shadow-sm"}
        [:input#email {:type     "text"
                       :name     "full-name"
                       :class    "block w-full h-12 pl-7 pr-12 text-gray-700 border border-gray-300 rounded-md"
                       :value    (:email user_metadata)
                       :disabled true}]]]]
     #_[:div
        [:pre.my-5 (with-out-str (cljs.pprint/pprint _user))]]]))


(defn subscriptions
  [])


(defn content
  [route-name]
  [:div.flex.flex-1.flex-col.bg-white.rounded-md.shadow-md.p-5
   [:div {:class "flex items-center px-2 pb-4 border-b border-b-1 border-b-gray-200"}
    [:h2 {:class "text-xl font-bold leading-7 text-gray-900"} (get-in links [route-name :label])]]
   [:div.flex.flex-1.mt-5
    (condp re-matches (str route-name)
      #":page/profile.subscriptions.*" [subscriptions]
      [profile])]])


(defn page
  [route-name]
  [:div.flex.justify-between.gap-2
   [navbar route-name]
   [content route-name]])
