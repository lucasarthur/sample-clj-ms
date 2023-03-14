(ns sample.core
  (:require
   [aleph.http :refer [start-server]]
   [aleph.netty :refer [port]]
   [environ.core :refer [env]]
   [sample.util.app :refer [on-shutdown]]
   [sample.config.log :refer [log-on-kafka stop-loggers! init-loggers!]]
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
  (init-loggers!)
  (let [server (start-server api {:port (-> env :port Integer/parseInt)})]
    (log-on-kafka ::server-started :port (port server))
    (set-health-status! :up)
    (on-shutdown (fn []
                   (log-on-kafka ::stopping-server)
                   (set-health-status! :down)
                   (greeter-consumer/stop!)
                   (greeter-producer/stop!)
                   (.close server)
                   (stop-loggers!)))))
