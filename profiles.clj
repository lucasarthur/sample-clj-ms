;; just for demo purposes.
;; this should be added to .gitignore on a production app.

{:dev {:env {:port "8080"
             :log-level "info"
             :metrics-path "/metrics"
             :swagger-path "/api-docs"
             :kafka-brokers "localhost:9092"}}}
