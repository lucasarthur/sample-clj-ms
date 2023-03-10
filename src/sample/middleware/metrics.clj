(ns sample.middleware.metrics
  (:require
   [iapetos.core :refer [collector-registry register histogram]]
   [iapetos.collector.jvm :as jvm]
   [iapetos.collector.ring :as ring]
   [compojure.core :refer [routes]]
   [sample.config.metrics :refer [metrics-cfg-map health-checks]]))

(defonce registry
  (-> (collector-registry)
      (register (histogram :app/duration-seconds))
      (jvm/initialize)
      (ring/initialize)))

(defn wrap-iapetos [handler]
  (ring/wrap-metrics handler registry metrics-cfg-map))

(defn wrap-health-checks [handler]
  (routes health-checks handler))

(defn wrap-metrics [handler]
  (-> handler wrap-iapetos wrap-health-checks))
