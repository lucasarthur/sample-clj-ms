(ns sample.consumer.greeter-consumer
  (:require
   [clojure.core.async :refer [chan]]
   [environ.core :refer [env]]
   [ketu.async.source :as s :refer [source]]
   [manifold.stream :refer [->source consume-async]]))

(def channel (chan))
(def raw-events (->source channel))

(def consumer
  (source channel {:brokers (-> env :kafka-brokers)
                   :name "greeter-consumer"
                   :topic "GreetingDispatched"
                   :group-id "sample-greeter-group"
                   :value-type :string
                   :shape :value}))

(def greetings (->> raw-events (consume-async #(println "saving " % " on database!"))))

(defn stop! []
  (s/stop! consumer))
