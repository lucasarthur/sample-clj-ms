(ns sample.routes
  (:require
   [sample.handlers :refer :all]
   [sample.middleware.json :refer [wrap-json-response wrap-json]]
   [sample.middleware.sse :refer [wrap-sse-response]]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :refer [not-found]]))

(defroutes routes
  (GET "/" [] (wrap-json-response root-handler))
  (GET "/echo" [] (wrap-json-response echo-handler))
  (POST "/echo" [] (wrap-json echo-handler))
  (GET "/sse/uuids" [] (wrap-sse-response uuid-handler))
  (GET "/divide-by-zero" [] (wrap-json-response divide-by-zero-handler))
  (GET "/nice-exception" [] (wrap-json-response nice-exception-handler))
  (GET "/produce-greeting/:name" [name greeting] (produce-greeting-handler name greeting))
  (not-found (wrap-json-response not-found-handler)))
