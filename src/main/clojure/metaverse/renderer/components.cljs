(ns metaverse.renderer.components)


(defn icon-spinner
  []
  [:i.w-4.h-4.rounded-full.animate-spin.border-x-2.border-solid.border-gray-600.dark:border-gray-100.border-t-transparent])


(defn spinner
  ([]
   [:div.flex.justify-center.justify-items-center.content-center.items-center
    [icon-spinner]])
  ([text]
   [:div.flex.justify-center.justify-items-center.content-center.items-center.gap-2
    [icon-spinner]
    [:span.text-gray-600.dark:text-gray-100 text]]))


(defn loader
  [{:keys [state idle-icon loading-icon ready-icon failed-icon]}]
  (case state
    :loading (or loading-icon icon-spinner)
    :ready (or ready-icon [:i.fa-solid.fa-check.text-gray-600.dark:text-gray-100.animate-pulse])
    :failed (or failed-icon [:i.fa-solid.fa-xmark.text-gray-600.dark:text-gray-100.animate-pulse])
    :idle idle-icon))
