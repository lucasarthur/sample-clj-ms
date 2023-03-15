(ns sample.core
  (:require
   [web.commons.server :refer [start-server! port]]
   [web.commons.log :refer [log-on-kafka]]
   [web.commons.middleware.log :refer [log-http-requests]]
   [web.commons.middleware.exception :refer [wrap-exceptions]]
   [web.commons.middleware.metrics :refer [wrap-metrics]]
   [web.commons.middleware.swagger :refer [wrap-swagger]]
   [web.commons.middleware.cors :refer [wrap-cors]]
   [sample.routes :refer [routes]]
   [sample.swagger :refer [swagger]]
   [environ.core :refer [env]])
  (:gen-class))

(def api
  (-> routes
      (wrap-cors)
      (wrap-exceptions)
      (log-http-requests {:on-kafka? true})
      (wrap-metrics)
      (wrap-swagger swagger)))

(defn -main []
  (start-server! api {:port (-> env :port parse-long)
                      :on-start #(log-on-kafka ::server-started :port (port))
                      :on-shutdown #(log-on-kafka ::server-shutdown)}))
