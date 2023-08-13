# Terisk **

<img src="terisk.svg.png" alt="an asterisk" align="right">

Annotate functions with examples that are tested and appended to docstrings.

> ** Terisk is short for asterisk; a common way to annotate.

## Usage

[![Clojars Project](https://img.shields.io/clojars/v/org.scicloj/terisk.svg)](https://clojars.org/org.scicloj/terisk)

```clojure
(ns my.code (:require [terisk.macros :refer [**]]))
(defn foo
  "this is a docstr"
  [x]
  (inc x))
(** (foo 2) 1)
```

Don't

```clojure
(** (foo 2) 1)
(** (foo 3) 2)
```

Calls to `**` mutate var metadata, so the above will only create 1 example, the last one.

Do

```clojure
(** (foo 2) 1
    (foo 3) 2)
```

Multiple examples can be created by including more example/expected pairs.


If you'd like to verify that the docstring is altered, and test was created:

```clojure
  (require ['clojure.repl :refer :all])
  (doc foo)
  (require ['clojure.test :refer :all])
  (run-tests)
```

Or just leave it to your normal test suite and docgen processes.


## Rationale

### Problem

I want testable examples as part of my documentation.
Rich comment blocks don't get tested or included in documentation.

### Goals

* Examples appear in the documentation
* Examples are tested

### Solution

Follow the approach of spec and typed clojure by annotating.
Make use of var mutability to update docstring and test.

Vars can be annotated with the `**` macro which adds a documented test case.

If you'd like to separate your example/expected clauses,
consider using a reader-ignored symbol: `#_=>`

```clojure
(** (foo 1)
    #_=> 2
    
    (foo 2)
    #_=> 3)
```

### Limitations

`(** example expected)` is synonymous with `(is (= example expected))`, and therefore does not express conditions such as "throws an exception".


### Alternatives

#### Replacement `defn` macro with new options

Doesn't work well with existing tools as it introduces new syntax.

#### Inline test

```clojure
(defn ^{:test (fn []
                (is (= 1 (f 0))))}
  foo [x]
  (inc x))
```

Do not get included in documentation.
Worth solving that separately.
The test syntax is distracting.

#### clojure.test and deftest 

```clojure
(deftest foo-test (is (= 1 (f 0))))
```

Tests are great, but often users benefit from having small examples closer to hand.

#### Rich comment tests

* [Hyperfiddle Rich Comment Forms](https://github.com/hyperfiddle/rcf)
* [Rich Comment Tests](https://github.com/matthewdowney/rich-comment-tests)

Do not get added to documentation.
These projects prioritize testing over examples.
Perhaps it would be desirable to use `examples` and `tests` macros to distinguish between them?
