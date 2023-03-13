(ns sample.config.kafka
  (:require [environ.core :refer [env]]))

(defn kafka-cfg-map [options]
  (-> {:brokers (-> env :kafka-brokers)} (merge options)))
