(ns metaverse.ui.core-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [metaverse.ui.core :as sut]))


(deftest square-test
  (testing "dummy test"
    (is (= 4 (sut/square 2)))))
