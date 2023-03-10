(ns sample.middleware.log
  (:require [com.brunobonacci.mulog :refer [log]]))

(defn log-http-requests [handler]
  (fn [req]
    (log :http-request
         :method (:request-method req)
         :path (:uri req)
         :remote-addr (:remote-addr req))
    (handler req)))
