(ns sample.core
  (:require
   [aleph.http :refer [start-server]]
   [aleph.netty :refer [port]]
   [com.brunobonacci.mulog :refer [log]]
   [sample.config.log :refer [init-logs]]
   [sample.config.server :refer [server-cfg-map]]
   [sample.routes :refer [routes]]
   [sample.middleware.log :refer [log-http-requests]]
   [sample.middleware.exception :refer [wrap-exceptions]]
   [sample.middleware.metrics :refer [wrap-metrics]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]])
  (:gen-class))

(def api (-> routes log-http-requests wrap-metrics wrap-exceptions (wrap-defaults api-defaults)))

(defn -main []
  (init-logs)
  (let [server (start-server api server-cfg-map)]
    (log ::server-started :on-port (port server))))
