(ns json-to-csv.core
  (:gen-class)
  (:require [clj-json.core :as json]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))


(defn write-csv
  [iow csvw path row-data]
    (with-open [file (iow path)]
      (csvw file row-data)))


(defn vectorize [cols map]
  (mapv #(mapv % cols) map))


(defn json-to-csv [json out writer]
  (let [rows (try (json/parse-string json)
                  (catch Exception e (throw e)))
        cols (-> rows first keys)]
    (writer out (cons cols (vectorize cols rows)))))


(defn -main
  "Given an input json file and an output path return the csv file in that path"
  [& args]
  (if (< (count args) 2) (println "Usage: lein run <json_file> <output_csv_path>")
      (let [[inp out] args
            writer (partial write-csv io/writer csv/write-csv)]
        (json-to-csv (slurp inp) out writer)
        (println (str "CSV file written at " out)))))
