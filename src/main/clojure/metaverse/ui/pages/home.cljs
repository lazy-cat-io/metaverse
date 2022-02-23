(ns metaverse.ui.pages.home
  (:require
    [metaverse.ui.components.heroicons.outline :as icons]
    [re-frame.core :as rf]))


(defn loading-page
  []
  [:div {:class "p-8"}
   [:span "Loading..."]])


(defn get-icon
  [route-name]
  (case route-name
    :page/home icons/home-icon
    ;; :page/sign-in [:span "sign-in"]
    ;; :page/sign-in.github [:span "sign-in"]
    :page/news icons/rss-icon
    :page/projects icons/view-boards-icon
    :page/projects.trends icons/fire-icon
    :page/jobs icons/briefcase-icon
    :page/docs icons/book-open-icon
    :page/profile icons/user-icon
    :page/profile.settings icons/cog-icon
    :page/initializer icons/cube-icon
    nil))


(defn logotype
  []
  [:div.logotype])


(defn search
  []
  [:div.search
   [:input.search-input {:type "text" :default-value "Search..."}]])


(defn navbar-item
  [route-name {:keys [href]}]
  [:a.nav-item {:class (if (= route-name href) "active" "")
                :href  @(rf/subscribe [:href href])}
   [:> (get-icon href)]])


(def navbar-items
  {:page/home            {:header "Home", :href :page/home, :column :left}
   :page/news            {:header "News", :href :page/news, :column :left}
   :page/projects        {:header "Projects", :href :page/projects, :column :left}
   :page/projects.trends {:header "Trends", :href :page/projects.trends, :column :left}
   :page/jobs            {:header "Jobs", :href :page/jobs, :column :left}
   :page/docs            {:header "Docs", :href :page/docs, :column :right}
   :page/profile         {:header "Profile", :href :page/profile, :column :right}
   :page/initializer     {:header "Initializer", :href :page/initializer, :column :right}})


(defn navbar
  [route-name]
  (let [groups (group-by :column (vals navbar-items))]
    [:div.nav
     [:div.nav-items
      (into [:div.column-left]
            (for [item (:left groups)]
              [navbar-item route-name item]))
      (into [:div.column-right]
            (for [item (:right groups)]
              [navbar-item route-name item]))]]))


(defn example
  [route-name]
  [:div.page {:style {:border "0px solid red"}}
   [logotype]
   [navbar route-name]
   [search]

   [:div..grid-row-1.col-span-full {:style {:border "0px solid yellow"}}
    [:div.grid.grid-cols-2.gap-2 {:style {:border "0px solid magenta"}}
     [:div.flex.justify-start.gap-2]
     [:div.flex.justify-end.gap-2
      ;; filters
      [:a.nav.item {:href @(rf/subscribe [:href :page/initializer])}
       [:> icons/adjustments-icon]]]]]

   [:div.grid-row-1.col-span-full.my-4 {:style {:border "0px solid lime"}}
    [:span (get-in navbar-items [route-name :header])]]

   [:button
    {:on-click #(rf/dispatch [:app/toggle-theme])}
    "Toggle theme"]

   [:span @(rf/subscribe [:app/theme])]])


(defn page
  "Root page."
  []
  (if-not @(rf/subscribe [:app/initialized?])
    [loading-page]
    (let [route-name @(rf/subscribe [:navigation/route-name])]
      (js/console.log :route-name route-name)
      (case route-name
        :page/home [example route-name]
        :page/sign-in [example route-name]
        :page/sign-in.github [example route-name]
        :page/news [example route-name]
        :page/projects [example route-name]
        :page/projects.trends [example route-name]
        :page/jobs [example route-name]
        :page/docs [example route-name]
        :page/profile [example route-name]
        :page/profile.settings [example route-name]
        :page/initializer [example route-name]
        [example :not-found]))))
