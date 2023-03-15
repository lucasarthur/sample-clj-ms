(ns sample.consumer.greeter-consumer
  (:require
   [web.commons.kafka :refer [create-consumer]]
   [web.commons.log :refer [log]]
   [manifold.stream :refer [consume-async]]))

(def consumer (create-consumer
               {:name "greeter-consumer"
                :topic "GreetingDispatched"
                :group-id "sample-greeter-group"
                :value-type :string
                :shape :value}))

(def greetings
  (->> (:stream consumer)
       (consume-async #(log ::greeting-received :saving % :on :database))))
