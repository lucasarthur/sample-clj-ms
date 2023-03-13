(ns sample.core
  (:require
   [aleph.http :refer [start-server]]
   [aleph.netty :refer [port]]
   [com.brunobonacci.mulog :refer [log]]
   [sample.util.app :refer [on-shutdown]]
   [sample.config.log :refer [init-logs]]
   [environ.core :refer [env]]
   [sample.config.metrics :refer [set-health-status!]]
   [sample.producer.greeter-producer :as greeter-producer]
   [sample.consumer.greeter-consumer :as greeter-consumer]
   [sample.routes :refer [routes]]
   [sample.swagger :refer [swagger]]
   [sample.middleware.log :refer [log-http-requests]]
   [sample.middleware.exception :refer [wrap-exceptions]]
   [sample.middleware.metrics :refer [wrap-metrics]]
   [sample.middleware.swagger :refer [wrap-swagger]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]])
  (:gen-class))

(def api (-> routes
             (wrap-exceptions)
             (log-http-requests)
             (wrap-metrics)
             (wrap-defaults api-defaults)
             (wrap-swagger swagger)))

(defn -main []
  (init-logs)
  (let [server (start-server api {:port (-> env :port Integer/parseInt)})]
    (log ::server-started :port (port server))
    (set-health-status! :up)
    (on-shutdown
     (fn []
       (greeter-consumer/stop!)
       (greeter-producer/stop!)
       (.close server)))))
