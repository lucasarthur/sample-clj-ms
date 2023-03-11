(ns sample.config.swagger
  (:require [environ.core :refer [env]]))

(def swagger-cfg-map
  {:path (env :swagger-path)
   :swagger-docs (-> env :swagger-path (str "/swagger.json"))})
