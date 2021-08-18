(ns json-to-csv.core-test
  (:gen-class)
  (:require [clj-json.core :as json]
            [clojure.test :as t]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [json-to-csv.core :as core]))

(def jsn (json/parse-string "[{\"a\":1,\"b\":2}]"))
(def filename "resources/inp.json")



(t/deftest test-vectorize
  (t/is
   (= (core/vectorize ["a" "b"] jsn) [[1,2]])
   (= (core/vectorize ["a"] jsn) [[1]]))
  (t/is
   (= (core/vectorize [] jsn) [[]]))
  (t/is
   (= (core/vectorize ["a"] []) []))
  (t/is
   (= (core/vectorize ["a"] [{:b 1}]) [[nil]])))

(defn mockWriter
  [_ rows]
  rows)

(t/deftest test-json-to-csv
  (t/is
   (= (core/json-to-csv  "[{\"a\":1,\"b\":2}]"  "" mockWriter) [["a" "b"] [1 2]])
   "Valid json string returns rows")
  (t/is
   (= (first (core/json-to-csv "[{\"a\":1}]"  "" mockWriter)) ["a"])
   "Valid json strings should return columns")
  (t/is
   (thrown? Exception (core/json-to-csv "[" "" mockWriter))
   "Invalid json string throws ex" )
  (t/is
   (= (core/json-to-csv "{}" "" mockWriter) [nil])
   "Empty json object should return nil")
  (t/is
   (= (core/json-to-csv "" "" mockWriter) [nil])
   "Empty string should return nil" ))

(t/deftest test-write-csv
  (with-redefs [csv/write-csv (fn [_ data] data)]
    (t/is (=  [["a" "b"] [1 2]]
            (core/write-csv io/writer csv/write-csv  "/tmp/abc.csv" [["a" "b"] [1 2]])))))


(t/with-test
  (defn writejson []
    (with-open [w (io/writer filename :append true)]
      (.write w jsn)))
  (t/is (= nil (core/-main filename "/tmp/res.csv"))
      (= nil (core/-main ""))))
