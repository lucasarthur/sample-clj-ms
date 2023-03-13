(ns sample.producer.greeter-producer
  (:require
   [clojure.core.async :refer [chan]]
   [manifold.stream :as s]
   [sample.config.kafka :refer [kafka-cfg-map]]
   [ketu.async.sink :refer [sink]]
   [cheshire.core :refer [generate-string]]))

(def channel (chan 16))
(def stream (-> (s/stream 16) (s/connect channel)))

(def producer (sink channel (kafka-cfg-map
                             {:name "greeter-producer"
                              :topic "GreetingDispatched"
                              :value-type :string
                              :shape :value})))

(defn produce-greeting [greeting]
  (s/put! stream (generate-string {:greeting greeting})))
