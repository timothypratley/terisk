(ns logo
  (:require [clojure.math :as math]
            [clojure.string :as str]
            [terisk.macros :refer [**]]))

(def TAU (* math/PI 2))

(defn nlygon [n r]
  (for [i (range n)
        :let [phi (* (/ i n) TAU)]]
    [(math/round (- (* r (math/sin phi))))
     (math/round (- (* r (math/cos phi))))]))
(** (nlygon 5 100)
    #_=> [[0 100]
          [95 31]
          [59 -81]
          [-59 -81]
          [-95 31]])

(defn pair [[x y]]
  (str x "," y))

(defn asterisk
  "Creates a path representing an asterisk"
  []
  (str "M 0,0 L"
       (str/join " "
                 (->> (nlygon 5 90)
                      (interpose [0 0])
                      (map pair)))))
(** (asterisk)
    #_=> "M 0,0 L0,-90 0,0 -86,-28 0,0 -53,73 0,0 53,73 0,0 86,-28")
