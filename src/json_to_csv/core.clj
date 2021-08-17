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


(defn json-to-csv [inp out]
  (let [rows (json/parse-string (slurp inp))
        cols (-> rows first keys)]
    (write-csv out (cons cols (vectorize cols rows)))))


(defn -main
  "Given an input json file and an output path return the csv file in that path"
  [& args]
  (if (< (count args) 2) (println "Usage: lein run <json_file> <output_csv_path>")
      (let [[inp out] args]
        (json-to-csv inp out)
        (println (str "CSV file written at " out)))))
