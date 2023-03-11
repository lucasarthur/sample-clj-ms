(ns sample.config.metrics
  (:require [environ.core :refer [env]]))

(def metrics-cfg-map {:path (env :metrics-path)})

(def health-statuses {:up   {:status 200 :message "up"}
                      :down {:status 503 :message "down"}})

(def health-path
  (str (env :metrics-path) "/health"))

(defonce health-status (atom (:down health-statuses)))

(defn get-health-status []
  @health-status)

(defn set-health-status! [status]
  (swap! health-status (constantly (status health-statuses))))
