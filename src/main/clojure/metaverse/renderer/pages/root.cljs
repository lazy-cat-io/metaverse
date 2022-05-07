(ns metaverse.renderer.pages.root
  (:require
    [metaverse.renderer.components :as components]
    [metaverse.renderer.components.heroicons.outline :as icons]
    [metaverse.renderer.pages.docs :as docs]
    [metaverse.renderer.pages.home :as home]
    [metaverse.renderer.pages.initializer :as initializer]
    [metaverse.renderer.pages.jobs :as jobs]
    [metaverse.renderer.pages.news :as news]
    [metaverse.renderer.pages.not-found :as not-found]
    [metaverse.renderer.pages.profile :as profile]
    [metaverse.renderer.pages.projects :as projects]
    [metaverse.renderer.pages.sign-in :as sign-in]
    [re-frame.core :as rf]))


(defn get-icon
  [route-name]
  (case route-name
    :page/home icons/home-icon
    :page/news icons/rss-icon
    :page/projects icons/fire-icon
    :page/jobs icons/briefcase-icon
    :page/docs icons/book-open-icon
    :page/profile icons/user-icon
    :page/initializer icons/cube-icon
    nil))


(defn search
  []
  [:div.search
   [:input.search-input {:type "text" :default-value "Search..."}]])


(defn navbar-item
  [route-name {:keys [href]}]
  [:a.h-10.w-10.p-1.rounded-lg.text-gray-600.hover:text-gray-100.hover:bg-gray-600.dark:text-gray-100.dark:hover:text-gray-100.dark:hover:bg-gray-700
   {:class    (if (= route-name href) "text-gray-100 bg-gray-600 dark:text-gray-100 dark:bg-gray-700" "")
    :href     @(rf/subscribe [:href href])
    :on-click #(rf/dispatch [:app/set-theme (rand-nth ["dark" "light"])])}
   [:> (get-icon href)]])


(def navbar-items
  {:page/home        {:header "Home", :href :page/home, :column :left}
   :page/news        {:header "News", :href :page/news, :column :left}
   :page/projects    {:header "Projects", :href :page/projects, :column :left}
   :page/jobs        {:header "Jobs", :href :page/jobs, :column :left}
   :page/docs        {:header "Docs", :href :page/docs, :column :right}
   :page/initializer {:header "Initializer", :href :page/initializer, :column :right}
   :page/profile     {:header "Profile", :href :page/profile, :column :right}})


(defn navbar
  [route-name]
  (let [groups (group-by :column (vals navbar-items))]
    [:div.col-span-full.mt-2
     [:div.grid.grid-cols-2.gap-2
      (into [:div.flex.justify-start.gap-2]
            (for [item (:left groups)]
              [navbar-item route-name item]))
      (into [:div.h-12.flex.justify-end.gap-2]
            (for [item (:right groups)]
              [navbar-item route-name item]))]]))


(defn page
  "Root page."
  []
  (let [route-name @(rf/subscribe [:navigation/route-name])]
    [:div.w-screen.h-screen.p-2
     [components/logotype]
     [navbar route-name]
     (if-not @(rf/subscribe [:app/initialized?])
       [:div.w-full.h-full.mt-10
        [components/spinner "Loading..."]]
       [:div.w-full.h-full.mt-10
        (case route-name
          :page/home [home/page]
          :page/news [news/page]
          :page/projects [projects/page]
          :page/jobs [jobs/page]
          :page/docs [docs/page]
          :page/profile [profile/page]
          :page/initializer [initializer/page]
          (:page/sign-in :page/oauth.provider.callback) [sign-in/page]
          [not-found/page])])]))
