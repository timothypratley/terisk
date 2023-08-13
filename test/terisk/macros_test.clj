(ns terisk.macros-test
  (:require [clojure.test :refer [deftest testing is]]
            [terisk.macros :refer [**]]))

(deftest **-test
  (testing "basic usage"
    (defn foo "docstring" [x] (inc x))
    (** (foo 1) 2)
    (is (= "docstring
----
(foo 1)
=> 2"
           (-> #'foo meta :doc)))))
