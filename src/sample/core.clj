(ns sample.core
  (:require
   [web.commons.server :refer [start-server! port]]
   [web.commons.log :refer [log-on-kafka]]
   [web.commons.middleware.log :refer [log-http-requests]]
   [web.commons.middleware.exception :refer [wrap-exceptions]]
   [web.commons.middleware.metrics :refer [wrap-metrics]]
   [web.commons.middleware.swagger :refer [wrap-swagger]]
   [sample.routes :refer [routes]]
   [sample.swagger :refer [swagger]]
   [environ.core :refer [env]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]])
  (:gen-class))

(def api
  (-> routes
      (wrap-exceptions)
      (log-http-requests {:on-kafka? true})
      (wrap-metrics)
      (wrap-defaults api-defaults)
      (wrap-swagger swagger)))

(defn -main []
  (start-server! api {:port (-> env :port Integer/parseInt)
                      :on-start #(log-on-kafka ::server-started :port (port))
                      :on-shutdown #(log-on-kafka ::server-shutdown)}))
