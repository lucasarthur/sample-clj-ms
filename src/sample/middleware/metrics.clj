(ns sample.middleware.metrics
  (:require
   [iapetos.core :refer [collector-registry register histogram]]
   [iapetos.collector.jvm :as jvm]
   [iapetos.collector.ring :as ring]
   [sample.config.metrics :refer [metrics-cfg-map]]))

(defonce registry
  (-> (collector-registry)
      (register (histogram :app/duration-seconds))
      (jvm/initialize)
      (ring/initialize)))

(defn wrap-metrics [handler]
  (ring/wrap-metrics handler registry metrics-cfg-map))
