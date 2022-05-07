(ns metaverse.renderer.pages.profile
  (:require
    [cljs.pprint :as pprint]
    [re-frame.core :as rf]))


(defn page
  []
  (when-some [user @(rf/subscribe [:user])]
    [:div
     [:h1 "Profile"]
     [:button.bg-white.text-black.py-2.px-3.rounded-lg.shadow-md {:on-click #(rf/dispatch [:auth/sign-out])}
      [:i.fa-solid.fa-right-from-bracket]
      [:span.mx-2 "Sign-out"]]
     [:pre.my-5 (with-out-str (pprint/pprint user))]]))
