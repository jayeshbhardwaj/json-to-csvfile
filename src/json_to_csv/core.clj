(ns json-to-csv.core
  (:gen-class)
  (:require [clj-json.core :as json]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))


(defn write-csv [path row-data]
    (with-open [file (io/writer path)]
      (csv/write-csv file row-data)))


(defn vectorize [cols map]
  (mapv #(mapv % cols) map))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [[inp out] args
        rows (json/parse-string (slurp inp))
        cols (-> rows first keys)]
    (write-csv out (cons cols (vectorize cols rows)))))
