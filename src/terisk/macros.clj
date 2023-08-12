(ns terisk.macros
  "Annotate functions with examples that will be appended to docstrings,
  and added as tests.
  See `**` as the main entry point."
  (:require [clojure.string :as str]
            [clojure.test :refer [is]]))

(defn ^:private format-example [[example expected]]
  (str example \newline
       "=> " expected))

(defn ^:private docstring [examples]
  (str/join (str \newline \newline)
            (map format-example (partition 2 examples))))

(defn ^:private test-expr [[example expected]]
  `(is (~'= ~example ~expected)))

(defn ^:private tests [examples]
  `(fn []
     ~@(map test-expr (partition 2 examples))))

(defmacro **
  "Attaches examples to the docstring and test of a var.
  ----
  (** (my-function-var args) expected)"
  [example expected & more]
  (assert (even? (count more)) "an even number of example/expected forms")
  (let [[varsym] example
        examples (list* example expected more)]
    `(alter-meta! (var ~varsym)
                  (fn [m#]
                    (-> m#
                        (update :doc str \newline "----" \newline
                                ~(docstring examples))
                        (assoc :test ~(tests examples)))))))

(comment
  (defn foo
    "this is a docstr"
    [x])
  (** (foo 2) #_=> 1)
  (require ['clojure.repl :refer :all])
  (doc foo)
  (require ['clojure.test :refer :all])
  (run-tests)
  (**))
