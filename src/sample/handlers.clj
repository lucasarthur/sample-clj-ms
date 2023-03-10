(ns sample.handlers
  (:require
   [manifold.stream :as s]
   [cheshire.core :refer [generate-string]]
   [sample.util.sse :refer [->sse]]))

(defn divide-by-zero-handler [_]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (->> (/ 1 0) (hash-map :result) generate-string)})

(defn nice-exception-handler [_]
  (throw (ex-info "now that's what i call an exception!" {:status 422})))

(defn root-handler [_]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (generate-string {:message "hello, world!"})})

(defn echo-handler [req]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (generate-string req)})

(defn uuid-handler [_]
  (->> (s/periodically 2500 random-uuid)
       (s/map #(-> {:uuid %} generate-string ->sse))))

(defn not-found-handler [_]
  {:status 404
   :headers {"content-type" "application/json"}
   :body (generate-string {:status 404 :message "not found"})})
