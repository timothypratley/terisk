(ns scratch
  (:require [clojure.test :refer [deftest is]]))

(defn ^{:test (fn []
                (is (= 1 (foo 0))))}
  foo
  "docstring: f takes you age as an integer
  and returns your height as an integer.
  (f 0) => 1"
  [x]
  1)

(deftest foo-test
  (is (= 1 (f 0))))
