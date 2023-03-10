(ns sample.routes
  (:require
   [sample.handlers :refer :all]
   [sample.middleware.sse :refer [wrap-raw-sse-response]]
   [compojure.core :refer [defroutes GET]]
   [compojure.route :refer [not-found]]))

(defroutes routes
  (GET "/" [] root-handler)
  (GET "/echo" [] echo-handler)
  (GET "/sse/uuids" [] (wrap-raw-sse-response uuid-handler))
  (GET "/divide-by-zero" [] divide-by-zero-handler)
  (GET "/nice-exception" [] nice-exception-handler)
  (not-found not-found-handler))
