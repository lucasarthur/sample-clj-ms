(ns sample.middleware.swagger
  (:require
   [sample.config.swagger :refer [swagger-cfg-map]]
   [ring.swagger.swagger-ui :refer [wrap-swagger-ui]]
   [ring.swagger.swagger2 :as rs]))

(defn wrap-swagger [handler]
  (wrap-swagger-ui handler swagger-cfg-map))
