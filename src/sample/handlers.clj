(ns sample.handlers
  (:require
   [manifold.stream :as s]
   [clojure.core.async :refer [put!]]
   [cheshire.core :refer [generate-string]]
   [web.commons.util.sse :refer [->sse]]
   [sample.producer.greeter-producer :refer [producer]]
   [sample.consumer.greeter-consumer :refer [consumer]]))

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
  (->> (str name " says " (or greeting "hi") "!")
       (hash-map :greeting)
       (generate-string)
       (put! (:channel producer)))
  {:status 202})

(defn consume-greetings-handler [_]
  {:status 200
   :body (s/map ->sse (:stream consumer))})

(defn not-found-handler [_]
  {:status 404
   :body {:message "not found"}})
