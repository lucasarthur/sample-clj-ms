(ns sample.swagger
  (:require [schema.core :as s :refer [defschema]]))

(defschema kitty
  {:id s/Int,
   :name s/Str,
   :kind (s/enum :wild :tame)})

(def swagger
  {:info {:version "1.0.0"
          :title "Clojure sample"
          :description "a microservice sample in clojure"
          :contact {:name "John Doe"
                    :email "doe@company.com"
                    :url "https://github.com/lucasarthur/sample-clj-ms"}
          :license {:name "GNU General Public License v3.0"
                    :url "https://www.gnu.org/licenses/gpl-3.0.pt-br.html"}}
   :produces ["application/json" "text/event-stream" "text/plain"]
   :consumes ["application/json"]
   :tags [{:name "sample"}
          {:name "metrics"}]
   :paths {"/" {:get {:description "a classic 'hello, world!' because: why not?"
                      :tags ["sample"]
                      :responses {200 {}}}}
           "/echo" {:get {:description "like a mirror, huh?"
                          :tags ["sample"]
                          :responses {200 {}}}}
           "/sse/uuids" {:get {:description "an infinite stream of random UUIDs"
                               :tags ["sample"]
                               :responses {200 {}}}}
           "/divide-by-zero" {:get {:description "i guess you shouldn't but you're so stubborn"
                                    :tags ["sample"]
                                    :responses {500 {}}}}
           "/nice-exception" {:get {:description "this one you can, it's custom handled! :)"
                                    :tags ["sample"]
                                    :responses {422 {}}}}
           "/metrics" {:get {:description "prometheus metrics"
                             :tags ["metrics"]
                             :responses {200 {}}}}
           "/metrics/health" {:get {:description "health check"
                                    :tags ["metrics"]
                                    :responses {200 {}
                                                503 {}}}}
           "/metrics/health/liveness" {:get {:description "liveness probe"
                                             :tags ["metrics"]
                                             :responses {200 {}
                                                         503 {}}}}
           "/metrics/health/readiness" {:get {:description "readiness probe"
                                              :tags ["metrics"]
                                              :responses {200 {}
                                                          503 {}}}}}})
