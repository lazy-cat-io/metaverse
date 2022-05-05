(ns metaverse.renderer.pages.home
  (:require
    [metaverse.renderer.api]
    [metaverse.renderer.components.heroicons.outline :as icons]
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


(rf/reg-event-fx
  ::sign-in->success
  (fn [_ [_ res]]
    (js/console.log "sign-in->success" (pr-str res))
    {:local-storage/set-item [:metaverse/user res]
     :api/send               [:open-url (:url @res)]}))


(rf/reg-event-fx
  ::sign-in->failure
  (fn [_ [_ res]]
    (js/console.log "sign-in->failure" (pr-str res))
    {:local-storage/remove-item [:metaverse/user]}))


(rf/reg-event-fx
  ::sign-in
  (fn [_ [_ opts]]
    {:api/invoke {:event      [:auth/sign-in opts]
                  :on-success [::sign-in->success]
                  :on-failure [::sign-in->failure]}}))


(rf/reg-event-fx
  ::sign-out->success
  (fn [_ [_ res]]
    (js/console.log "sign-out->success" (pr-str res))
    {:local-storage/remove-item :metaverse/user}))


(rf/reg-event-fx
  ::sign-out->failure
  (fn [_ [_ res]]
    (js/console.log "sign-out->failure" (pr-str res))
    {:local-storage/remove-item :metaverse/user}))


(rf/reg-event-fx
  ::sign-out
  (fn [_ [_ opts]]
    {:api/invoke {:event      [:auth/sign-out opts]
                  :on-success [::sign-out->success]
                  :on-failure [::sign-out->failure]}}))



(defn example
  [route-name theme]
  [:div.page {:style {:border "0px solid red"}}
   [logotype]
   [navbar route-name]
   [search]

   [:div.grid-row-1.col-span-full {:style {:border "0px solid yellow"}}
    [:div.grid.grid-cols-2.gap-2 {:style {:border "0px solid magenta"}}
     [:div.flex.justify-start.gap-2]
     [:div.flex.justify-end.gap-2
      ;; filters
      [:a.nav.item {:href @(rf/subscribe [:href :page/initializer])}
       [:> icons/adjustments-icon]]]]]

   [:div.grid-row-1.col-span-full.my-4 {:style {:border "0px solid lime"}}
    [:span (get-in navbar-items [route-name :header])]]

   [:div
    [:button
     {:on-click #(rf/dispatch [::sign-in {:email "john@doe.com", :password "p4$$w0rd1"}])}
     "Sign-in via email (bad credentials)"]

    [:button
     {:on-click #(rf/dispatch [::sign-in {:email "john@doe.com", :password "p4$$w0rd"}])}
     "Sign-in via email"]

    [:button
     {:on-click #(rf/dispatch [::sign-in {:provider "github"}])}
     "Sign-in via GitHub"]

    [:button
     {:on-click #(rf/dispatch [::sign-out])}
     "Sign-out"]]

   [:div
    [:button
     {:on-click #(rf/dispatch [:app/set-theme "dark"])}
     "Set dark theme"]

    [:button
     {:on-click #(rf/dispatch [:app/set-theme "light"])}
     "Set light theme"]

    [:span (str "theme: " theme)]]])


(defn page
  "Root page."
  []
  (if-not @(rf/subscribe [:app/initialized?])
    [loading-page]
    (let [route-name @(rf/subscribe [:navigation/route-name])
          theme      @(rf/subscribe [:app/theme])]
      (case route-name
        :page/home [example route-name theme]
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

