(ns sample.handlers
  (:require
   [manifold.stream :as s]
   [cheshire.core :refer [generate-string]]
   [sample.util.sse :refer [->sse]]
   [sample.producer.greeter-producer :refer [produce-greeting]]
   [sample.consumer.greeter-consumer :refer [raw-events]]))

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

(defn produce-greeting-handler [name greeting]
  (produce-greeting {:greeting (str name " says " (or greeting "hi") "!")})
  {:status 202})

(defn consume-greetings-handler [_]
  {:status 200
   :body (s/map ->sse raw-events)})

(defn not-found-handler [_]
  {:status 404
   :body {:message "not found"}})
