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

(defn wrap-health-checks [handler ready-check]
  (routes (health-checks ready-check) handler))

(defn wrap-metrics
  ([handler] (wrap-metrics handler nil))
  ([handler ready-check] (-> handler wrap-iapetos (wrap-health-checks ready-check))))
