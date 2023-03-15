;; just for demo purposes.
;; this should be added to .gitignore on a production app.

{:dev {:env {:port "8080"
             :log-level "info"
             :metrics-path "/metrics"
             :swagger-path "/api-docs"
             :kafka-brokers "localhost:9092"
             :kafka-logging-topic "SampleAuditLogs"
             :cors-allowed-origins "*://localhost:[*];*://0.0.0.0:[*];*://abc*.com.br:[80,443];*://abc*.io:[8080-9000]"
             :cors-allowed-methods "GET,POST,PUT,DELETE,PATCH"
             :cors-allowed-headers "Authorization,Content-Type,Origin,Accept,Host"
             :cors-exposed-headers "Connection,Transfer-Encoding,User-Id"
             :cors-allow-credentials "true"
             :cors-max-age "3600"}}}
