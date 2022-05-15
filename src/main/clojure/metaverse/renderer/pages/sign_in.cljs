(ns metaverse.renderer.pages.sign-in
  (:require
    [metaverse.renderer.components :as components]
    [re-frame.core :as rf]))


(defn page
  []
  (let [readiness @(rf/subscribe [:auth/readiness])]
    ^{:key readiness}
    [:div.flex.justify-center.justify-items-center.content-center.items-center
     [:button.bg-white.dark:bg-gray-700.text-black.py-2.px-3.rounded-lg.shadow-md.flex.items-center.cursor-pointer
      {:on-click #(rf/dispatch [:auth/sign-in.github])
       :disabled (= :loading readiness)}
      [components/loader {:state      readiness
                          :on-loading [components/loading-spinner]
                          :on-ready   [components/ready-spinner]
                          :on-failed  [components/failed-spinner]
                          :on-idle    [:i.fa-brands.fa-github.text-gray-600.dark:text-gray-100]}]
      [:span.mx-2.text-gray-700.dark:text-gray-100 "Sign-in via GitHub"]]]))
