(ns sample.middleware.metrics
  (:require
   [environ.core :refer [env]]
   [iapetos.core :refer [collector-registry register histogram]]
   [iapetos.collector.jvm :as jvm]
   [iapetos.collector.ring :as ring]
   [compojure.core :refer [routes context GET]]
   [sample.config.metrics :refer [health-path get-health-status health-statuses]]
   [sample.middleware.json :refer [wrap-json-response]]))

(defn- health-handler [status _]
  {:status (:status status)
   :body {:message (:message status)}})

(defn- test-readiness [ready?]
  (if (ready?) (get-health-status) (:down health-statuses)))

(defn- json-health-handler [status]
  (->> status (partial health-handler) (wrap-json-response)))

(defonce registry
  (-> (collector-registry)
      (register (histogram :app/duration-seconds))
      (jvm/initialize)
      (ring/initialize)))

(defn health-checks [ready-check]
  (routes
   (context health-path []
     (GET "/" [] (json-health-handler (get-health-status)))
     (GET "/liveness" [] (json-health-handler (get-health-status)))
     (GET "/readiness" [] (->> (or ready-check (constantly true)) (test-readiness) (json-health-handler))))))

(defn wrap-iapetos [handler]
  (ring/wrap-metrics handler registry {:path (env :metrics-path)}))

(defn wrap-health-checks [handler ready-check]
  (routes (health-checks ready-check) handler))

(defn wrap-metrics
  ([handler] (wrap-metrics handler nil))
  ([handler ready-check] (-> handler wrap-iapetos (wrap-health-checks ready-check))))
