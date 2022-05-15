(ns metaverse.renderer.components)


(defn spinner
  ([icon]
   [:div.inline-flex.justify-center.justify-items-center.content-center.items-center
    [icon]])
  ([icon text]
   [:div.inline-flex.justify-center.justify-items-center.content-center.items-center.gap-2
    [icon]
    [:span.text-gray-600.dark:text-gray-100 text]]))


(defn loading-icon
  []
  [:i.w-4.h-4.rounded-full.animate-spin.border-x-2.border-solid.border-gray-600.dark:border-gray-100.border-t-transparent])


(defn ready-icon
  []
  [:i.fa-solid.fa-check.text-gray-600.dark:text-gray-100.animate-pulse])


(defn failed-icon
  []
  [:i.fa-solid.fa-circle-exclamation.text-gray-600.dark:text-gray-100.animate-pulse])


(defn idle-icon
  []
  [:i.fa-regular.fa-clock.text-gray-600.dark:text-gray-100.animate-pulse])


(defn loading-spinner
  ([]
   [spinner loading-icon])
  ([text]
   [spinner loading-icon text]))



(defn ready-spinner
  ([]
   [spinner ready-icon])
  ([text]
   [spinner ready-icon text]))



(defn failed-spinner
  ([]
   [spinner failed-icon])
  ([text]
   [spinner failed-icon text]))


(defn idle-spinner
  ([]
   [spinner idle-icon])
  ([text]
   [spinner idle-icon text]))



(defn loader
  [{:keys [state on-idle on-loading on-ready on-failed]}]
  (case state
    :loading on-loading
    :ready on-ready
    :failed on-failed
    :idle on-idle
    nil))
