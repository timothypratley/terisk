(ns logo
  (:require [clojure.math :as math]
            [terisk.macros :refer [**]]))

(def TAU (* math/PI 2))

(defn nlygon [n r]
  (for [i (range n)
        :let [phi (* (/ i n) TAU)]]
    [(math/round (* r (math/sin phi)))
     (math/round (* r (math/cos phi)))]))
(** (nlygon 5 100)
    #_=> [[0 100]
          [95 31]
          [59 -81]
          [-59 -81]
          [-95 31]])
