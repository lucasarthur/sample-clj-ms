(ns sample.config.server
  (:require
   [environ.core :refer [env]]
   [compojure.response :refer [Renderable]]))

(extend-protocol Renderable
  manifold.deferred.IDeferred
  (render [d _] d))

(def server-cfg-map {:port (-> env :port Integer/parseInt)})
