(ns sample.config.metrics
  (:require [environ.core :refer [env]]))

(def metrics-cfg-map {:path (env :metrics-path)})
