(ns sample.middleware.metrics
  (:require
   [iapetos.core :refer [collector-registry register histogram]]
   [iapetos.collector.jvm :as jvm]
   [iapetos.collector.ring :as ring]
   [compojure.core :refer [routes context GET]]
   [cheshire.core :refer [generate-string]]
   [sample.config.metrics :refer [metrics-cfg-map health-path get-health-status health-statuses]]))

(defn- health-handler [status _]
  {:status (:status status)
   :headers {"content-type" "application/json"}
   :body (generate-string {:message (:message status)})})

(defn- wrap-readiness-probe [ready? _]
  (->> (if (ready?) (get-health-status) (:down health-statuses))
       (partial health-handler)))

(defonce registry
  (-> (collector-registry)
      (register (histogram :app/duration-seconds))
      (jvm/initialize)
      (ring/initialize)))

(defn health-checks [ready-check]
  (routes
   (context health-path []
     (GET "/" [] (partial health-handler (get-health-status)))
     (GET "/liveness" [] (partial health-handler (get-health-status)))
     (GET "/readiness" [] (partial wrap-readiness-probe (or ready-check (constantly true)))))))

(defn wrap-iapetos [handler]
  (ring/wrap-metrics handler registry metrics-cfg-map))

(defn wrap-health-checks [handler ready-check]
  (routes (health-checks ready-check) handler))

(defn wrap-metrics
  ([handler] (wrap-metrics handler nil))
  ([handler ready-check] (-> handler wrap-iapetos (wrap-health-checks ready-check))))
