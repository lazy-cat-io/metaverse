(ns metaverse.renderer
  (:require
    [goog.dom :as gdom]
    [metaverse.common.logger :as log]
    [metaverse.common.utils.transit :as t]
    [metaverse.renderer.db :as db]
    [metaverse.renderer.pages.home :as home]
    [metaverse.renderer.router.core :as router]
    [metaverse.renderer.storage]
    [re-frame.core :as rf]
    [reagent.core :as r]
    [reagent.dom :as dom]))


(defn setup-tools
  "Setup tools."
  []
  (log/init!))


;; metaverse://app#access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNjUxNzA2NDU2LCJzdWIiOiI2ODQ1NGNiNS04YWY2LTQ0MDctYmMzMC1mM2Y2YmQwZTUyZDAiLCJlbWFpbCI6Imlsc2hhdEBzdWx0YW5vdi50ZWFtIiwicGhvbmUiOiIiLCJhcHBfbWV0YWRhdGEiOnsicHJvdmlkZXIiOiJnaXRodWIiLCJwcm92aWRlcnMiOlsiZ2l0aHViIl19LCJ1c2VyX21ldGFkYXRhIjp7ImF2YXRhcl91cmwiOiJodHRwczovL2F2YXRhcnMuZ2l0aHVidXNlcmNvbnRlbnQuY29tL3UvNjUwMTMyOT92PTQiLCJlbWFpbCI6Imlsc2hhdEBzdWx0YW5vdi50ZWFtIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZ1bGxfbmFtZSI6Iklsc2hhdCBTdWx0YW5vdiIsImlzcyI6Imh0dHBzOi8vYXBpLmdpdGh1Yi5jb20iLCJuYW1lIjoiSWxzaGF0IFN1bHRhbm92IiwicHJlZmVycmVkX3VzZXJuYW1lIjoianVzdC1zdWx0YW5vdiIsInByb3ZpZGVyX2lkIjoiNjUwMTMyOSIsInN1YiI6IjY1MDEzMjkiLCJ1c2VyX25hbWUiOiJqdXN0LXN1bHRhbm92In0sInJvbGUiOiJhdXRoZW50aWNhdGVkIn0.OjB6BnL4b0J9CuUAxa-8fTOuF7wLsEfMf6noEIPS0XI&expires_in=3600&provider_token=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&refresh_token=c19pEgrEeBzi5saIJ2AiNQ&token_type=bearer

(defn root
  []
  (let [did-mount (fn [_]
                    (.. js/window -bridge
                        (dispatch (fn [_ipc-event event]
                                    (let [event (t/read event)]
                                      (rf/dispatch @event))))))]
    (r/create-class
      {:display-name        "root"
       :component-did-mount did-mount
       :reagent-render      (fn []
                              [home/page])})))


(defn mount-root
  "Mount root component."
  {:dev/after-load true}
  []
  (when-some [root-elem (gdom/getElement "root")]
    (rf/clear-subscription-cache!)
    (router/init!)
    (dom/render [root] root-elem)))


(defn -main
  "Renderer entry point."
  {:export true}
  [& _args]
  (setup-tools)
  (rf/dispatch-sync [::db/init])
  (mount-root))
