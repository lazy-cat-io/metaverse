(ns metaverse.renderer.pages.news
  (:require
    [cljs.pprint :as pprint]
    [heroicons.outline :as icons.outline]
    [metaverse.renderer.components :as components]
    [re-frame.core :as rf]))


(def links
  {:page/news              {:label "News"
                            :href  :page/news
                            :icon  icons.outline/newspaper-icon}
   :page/news.rss-channels {:label "RSS channels"
                            :href  :page/news.rss-channels
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


(defn rss-channels
  []
  (let [readiness    @(rf/subscribe [:news/readiness :news/rss-channels])
        rss-channels @(rf/subscribe [:news/rss-channels])]
    ^{:key readiness}
    [:div
     [components/loader
      {:state      readiness
       :on-loading [components/loading-spinner "Loading..."]}]
     [:pre (with-out-str (pprint/pprint rss-channels))]]))


(defn news
  []
  [:h1 "News"])


(defn content
  [route-name]
  [:div.flex.flex-1.flex-col.bg-white.dark:bg-gray-500.rounded-md.shadow-md.p-5
   [:div {:class "flex items-center px-2 pb-4 border-b border-b-1 border-b-gray-200 dark:border-b-gray-900"}
    [:h2 {:class "text-xl font-bold leading-7 text-gray-900"} (get-in links [route-name :label])]]
   [:div.flex.flex-1.mt-5
    (condp re-matches (str route-name)
      #":page/news.rss-channels.*" [rss-channels]
      [news])]])


(defn page
  [route-name]
  [:div.flex.justify-between.gap-2
   [navbar route-name]
   [content route-name]])
