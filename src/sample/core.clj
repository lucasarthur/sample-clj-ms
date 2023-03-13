(ns sample.core
  (:require
   [aleph.http :refer [start-server]]
   [aleph.netty :refer [port]]
   [com.brunobonacci.mulog :refer [log]]
   [sample.config.log :refer [init-logs]]
   [sample.config.server :refer [server-cfg-map]]
   [sample.config.metrics :refer [set-health-status!]]
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
  (let [server (start-server api server-cfg-map)]
    (log ::server-started :port (port server))
    (set-health-status! :up)))
