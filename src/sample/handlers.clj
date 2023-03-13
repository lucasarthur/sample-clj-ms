(ns sample.handlers
  (:require
   [manifold.stream :as s]
   [cheshire.core :refer [generate-string]]
   [sample.util.sse :refer [->sse]]))

(defn divide-by-zero-handler [_]
  {:status 200
   :body {:result (/ 1 0)}})

(defn nice-exception-handler [_]
  {:status 200
   :body (throw (ex-info "now that's what i call an exception!" {:status 422}))})

(defn root-handler [_]
  {:status 200
   :body {:message "hello, world!"}})

(defn echo-handler [req]
  {:status 200
   :body req})

(defn uuid-handler [_]
  {:status 200
   :body (->> (s/periodically 2500 random-uuid)
              (s/map #(-> {:uuid %} generate-string ->sse)))})

(defn produce-greeting-handler [_])

(defn not-found-handler [_]
  {:status 404
   :body {:message "not found"}})
