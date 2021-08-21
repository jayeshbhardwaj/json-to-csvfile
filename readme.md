# Clojure library for JSON to CSV parsing!

[![Clojure CI](https://github.com/jayeshbhardwaj/json-to-csvfile/actions/workflows/clojure.yml/badge.svg?branch=master)](https://github.com/jayeshbhardwaj/json-to-csvfile/actions/workflows/clojure.yml)

## Usage
You will first need to add the library as dependencies in your `project.clj` file.

``` clojure
[org.clojars.bhardwajjayesh7/json-to-csv "0.1.0-SNAPSHOT"]

```

Then you can define them as required libraries in your code like below and start using it


``` clojure
(require '[json-to-csv.core as csvparse])
(csvparse/from-json-to-csv <input_jsonpath> <output_csvpath>)
```

### Running
We can also invoke it using `lein run <input_jsonpath> <output_csvpath>` if we just want to use this as a tool to convert an json to csv
