(ns metaverse.common.utils.transit-test
  (:require
    [cljs.test :refer [deftest testing is]]
    [metaverse.common.utils.transit :as sut]
    [tenet.response :as r]))


(deftest transit-test
  (testing "serialize/deserialize unified response"
    (let [res (with-meta (r/as-success 42) {:foo :bar})
          [expected-type expected-data :as expected] res
          [actual-type actual-data :as actual] (sut/read (sut/write res))]
      (is (= res expected actual))
      (is (= :success expected-type actual-type))
      (is (= false (r/anomaly? res) (r/anomaly? expected) (r/anomaly? actual)))
      (is (= 42 @res @actual expected-data actual-data))
      (is (= {:foo :bar} (meta res) (meta expected) (meta actual))))))
