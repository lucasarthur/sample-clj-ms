(ns sample.producer.greeter-producer
  (:require [web.commons.kafka :refer [create-producer]]))

(def producer (create-producer
               {:name "greeter-producer"
                :topic "GreetingDispatched"
                :value-type :string
                :shape :value
                :channel-size 16}))
