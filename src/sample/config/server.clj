(ns sample.config.server
  (:require [environ.core :refer [env]]))

(def server-cfg-map {:port (-> env :port Integer/parseInt)})
