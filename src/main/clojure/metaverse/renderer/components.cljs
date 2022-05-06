(ns metaverse.renderer.components)


(defn logotype
  []
  [:div.col-span-full.flex.justify-end.items-center.mt-1.h-3.bg-logotype.bg-no-repeat.bg-right])


(defn spinner
  ([]
   [:div.flex.justify-center.justify-items-center.content-center.items-center
    [:div.w-8.h-8.rounded-full.animate-spin.border-x-2.border-solid.border-gray-600.dark:border-gray-100.border-t-transparent]])
  ([text]
   [:div.flex.justify-center.justify-items-center.content-center.items-center
    [:div.w-8.h-8.rounded-full.animate-spin.border-x-2.border-solid.border-gray-600.dark:border-gray-100.border-t-transparent]
    [:span.mx-2.text-xl.font-bold.text-gray-600.dark:text-gray-100.brand-font-family text]]))
