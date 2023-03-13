(ns sample.producer.greeter-producer
  (:require
   [clojure.core.async :refer [chan put! close!]]
   [environ.core :refer [env]]
   [ketu.async.sink :refer [sink]]
   [cheshire.core :refer [generate-string]]))

(def channel (chan 16))

(def producer
  (sink channel {:brokers (-> env :kafka-brokers)
                 :name "greeter-producer"
                 :topic "GreetingDispatched"
                 :value-type :string
                 :shape :value}))

(defn produce-greeting [greeting]
  (put! channel (generate-string greeting)))

(defn stop! []
  (close! channel))
