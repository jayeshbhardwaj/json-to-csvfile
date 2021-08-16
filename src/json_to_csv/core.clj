(ns json-to-csv.core
  (:gen-class)
  (:require [clj-json.core :as json]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))


(defn write-csv [path row-data]
  (let [columns ["a" "b"]
        headers (map name columns)
        rows (mapv #(mapv % columns) row-data)]
    (with-open [file (io/writer path)]
      (csv/write-csv file (cons headers rows)))))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (write-csv "/tmp/results.csv" (json/parse-string (slurp "/tmp/inp.json"))))
