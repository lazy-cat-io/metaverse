(ns metaverse.renderer.pages.root
  (:require
    [headless.ui :as ui]
    [heroicons.outline :as icons.outline]
    [metaverse.renderer.components :as components]
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


(defn logotype
  []
  (let [theme @(rf/subscribe [:app/theme])
        src   (case theme
                "light" "/assets/images/logotype.black.svg"
                "/assets/images/logotype.svg")]
    [:div.flex.flex-1.justify-start.items-center.h-12.p-2.space-x-8
     [:a {:href @(rf/subscribe [:href :page/home])}
      [:span.sr-only "metaverse"]
      [:img.h-12.w-36.min-w-36 {:src src :alt "logotype"}]]]))


(def navbar-items
  [{:label "Home" :href :page/home :icon icons.outline/home-icon}
   {:label "News" :href :page/news :icon icons.outline/rss-icon}
   {:label "Projects" :href :page/projects :icon icons.outline/fire-icon}
   {:label "Jobs" :href :page/jobs :icon icons.outline/briefcase-icon}
   {:label "Docs" :href :page/docs :icon icons.outline/book-open-icon}
   {:label "Initializer" :href :page/initializer :icon icons.outline/cube-icon}])


(defn navbar-links
  []
  (let [route-name @(rf/subscribe [:navigation/route-name])]
    (into [:div.flex.flex-1.items-center.h-12.space-x-8.p-2]
          (for [{:keys [href icon label]} navbar-items]
            (let [active? (= route-name href)]
              [:a.flex.items-center.gap-2.text-base.font-medium.hover:text-gray-900.dark:hover:text-gray-100.h-24
               {:href  @(rf/subscribe [:href href])
                :class (if active? "text-gray-700 dark:text-white" "text-gray-500 dark:text-gray-400")}
               [icon {:class "h-6"}]
               [:span label]])))))


(defn notifications
  []
  [:button {:type "button"}
   [:span.sr-only "View notifications"]
   [icons.outline/bell-icon {:class "h-6 w-6 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-white" :aria-hidden "true"}]])


(defn toggle-theme
  []
  [:button {:type "button" :on-click #(rf/dispatch [:app/toggle-theme])}
   [:span.sr-only "Dark mode"]
   [icons.outline/sparkles-icon {:class "h-6 w-6 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-white" :aria-hidden "true"}]])


(def user-menu-items
  [{:label "Profile" :href :page/profile :icon icons.outline/user-circle-icon}
   {:label "Sign out" :href :page/sign-out :icon icons.outline/logout-icon}])


(defn user-menu
  []
  (if-some [{{:keys [avatar_url]} :user_metadata} @(rf/subscribe [:user])]
    [ui/menu {:as "div" :class "ml-3 relative"}
     [ui/menu-button {:class "max-w-xs rounded-full flex items-center text-sm"}
      [:span.sr-only "Open user menu"]
      [:a {:href "#"}
       (if avatar_url
         [:img.w-12.h-12.rounded-full {:src avatar_url}]
         [icons.outline/user-icon {:class "w-6 h-6 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-white"}])]]
     [ui/transition {:enter     "transition ease-out duration-100"
                     :enterFrom "transform opacity-0 scale-95"
                     :enterTo   "transform opacity-100 scale-100"
                     :leave     "transition ease-in duration-75"
                     :leaveFrom "transform opacity-100 scale-100"
                     :leaveTo   "transform opacity-0 scale-95"}
      (into [ui/menu-items {:class "origin-top-right absolute right-0 mt-2 w-48 border-solid border border-1 border-gray-200 dark:border-gray-800 rounded-md shadow-lg py-1 bg-white dark:bg-gray-700"}]
            (for [{:keys [href icon label]} user-menu-items]
              [ui/menu-item {:key :page/profile}
               [:a.flex.gap-2.items-center.px-4.py-2.text-sm.text-gray-500.dark:text-gray-400.hover:text-gray-900.dark:hover:text-gray-100
                {:href @(rf/subscribe [:href href])}
                [icon {:class "w-6 h-6"}]
                [:span label]]]))]]
    [:a.ml-8.whitespace-nowrap.inline-flex.items-center.justify-center.px-4.py-2.border.border-transparent.rounded-md.shadow-sm.text-base.font-medium.text-white.bg-violet-600.hover:bg-violet-700
     {:href @(rf/subscribe [:href :page/sign-in])}
     [:span "Sign in"]]))


(defn navbar-controls
  []
  [:div.flex.flex-1.justify-end.items-center.space-x-5
   [:div.flex.space-x-5
    [notifications]
    [toggle-theme]
    [user-menu]]])


(defn navbar
  []
  [:div.max-w-8xl.mx-auto.px-3.bg-white.dark:bg-gray-800.shadow-md
   [:div.flex.justify-between.items-center.p-6.space-x-10
    [logotype]
    [navbar-links]
    [navbar-controls]]])


(defn main
  []
  (let [route-name @(rf/subscribe [:navigation/route-name])]
    [:main.max-w-7xl.mx-auto.p-10
     (case route-name
       :page/home [home/page]
       :page/news [news/page]
       :page/projects [projects/page]
       :page/jobs [jobs/page]
       :page/docs [docs/page]
       :page/profile [profile/page]
       :page/initializer [initializer/page]
       (:page/sign-in :page/oauth.provider.callback) [sign-in/page]
       [not-found/page])]))


(defn page
  "Root page."
  []
  (if-not @(rf/subscribe [:app/initialized?])
    [:div.w-screen.h-screen.flex.justify-center.justify-items-center.content-center.items-center.gap-2
     [components/spinner "Loading..."]]
    [:div.relative.h-screen
     [navbar]
     [main]]))
