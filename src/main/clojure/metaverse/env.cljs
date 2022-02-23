(ns metaverse.env
  (:require
    [clojure.string :as str]))


(goog-define company-name "@team.sultanov")
(goog-define product-name "metaverse")

(goog-define mode "production")
(goog-define version "N/A")
(goog-define build-number "N/A")
(goog-define build-timestamp "N/A")
(goog-define git-url "N/A")
(goog-define git-branch "N/A")
(goog-define git-sha "N/A")
(goog-define logger-level "error")
(goog-define default-language "en")

(goog-define sentry-dsn "N/A")

(goog-define clojuredocs-export-url "N/A")


(def develop?
  (= "develop" (str/lower-case mode)))


(def production?
  (= "production" (str/lower-case mode)))
