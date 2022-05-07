(ns metaverse.renderer.components
  (:require
    [reagent.core :as r]))


(defn logotype
  []
  [:div.col-span-full.flex.justify-end.items-center.mt-1.h-3.bg-logotype.bg-no-repeat.bg-right])


(defn icon-spinner
  []
  [:i.w-4.h-4.rounded-full.animate-spin.border-x-2.border-solid.border-gray-600.dark:border-gray-100.border-t-transparent])


(defn spinner
  ([]
   [:div.flex.justify-center.justify-items-center.content-center.items-center
    [icon-spinner]])
  ([text]
   [:div.flex.justify-center.justify-items-center.content-center.items-center
    [icon-spinner]
    [:span.mx-2.text-xl.font-bold.text-gray-600.dark:text-gray-100.brand-font-family text]]))


(defn loader
  [{:keys [state idle-icon loading-icon ready-icon failed-icon]}]
  (case state
    :loading (r/as-element (or loading-icon icon-spinner))
    :ready (r/as-element (or ready-icon [:i.fa-solid.fa-check.text-gray-600.dark:text-gray-100.animate-pulse]))
    :failed (r/as-element (or failed-icon [:i.fa-solid.fa-xmark.text-gray-600.dark:text-gray-100.animate-pulse]))
    :idle (r/as-element idle-icon)))
