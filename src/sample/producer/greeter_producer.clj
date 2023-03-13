(ns sample.producer.greeter-producer
  (:require
   [clojure.core.async :refer [chan put!]]
   [sample.config.kafka :refer [kafka-cfg-map]]
   [ketu.async.sink :refer [sink]]
   [cheshire.core :refer [generate-string]]))

(def channel (chan 16))

(def producer (sink channel (kafka-cfg-map
                             {:name "greeter-producer"
                              :topic "GreetingDispatched"
                              :value-type :string
                              :shape :value})))

(defn produce-greeting [greeting]
  (put! channel (generate-string greeting)))
