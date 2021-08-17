(ns json-to-csv.core-test
  (:gen-class)
  (:use clojure.test)
  (:require [clj-json.core :as json]
            [json-to-csv.core :as core]))

(def jsn (json/parse-string "[{\"a\":1,\"b\":2}]"))

(deftest test-vectorize
  (is
   (= (core/vectorize ["a" "b"] jsn) [[1,2]])
   (= (core/vectorize ["a"] jsn) [[1]])))
