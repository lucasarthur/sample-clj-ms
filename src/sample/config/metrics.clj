(ns sample.config.metrics
  (:require
   [environ.core :refer [env]]
   [compojure.core :refer [routes context GET]]
   [cheshire.core :refer [generate-string]]))

(def ^:private health-path
  (str (env :metrics-path) "/health"))

(def ^:private health-statuses
  {:up   {:status 200 :message "up"}
   :down {:status 503 :message "down"}})

(def metrics-cfg-map {:path (env :metrics-path)})

(defonce health-status (atom (:down health-statuses)))

(defn get-health-status []
  @health-status)

(defn set-health-status! [status]
  (swap! health-status (constantly (status health-statuses))))

(defn- health-handler [status _]
  {:status (:status status)
   :headers {"content-type" "application/json"}
   :body (generate-string {:message (:message status)})})

(defn- wrap-readiness-probe [ready? _]
  (->> (if (ready?) (get-health-status) (:down health-statuses))
       (partial health-handler)))

(defn health-checks [ready-check]
  (routes
   (context health-path []
     (GET "/" [] (partial health-handler (get-health-status)))
     (GET "/liveness" [] (partial health-handler (get-health-status)))
     (GET "/readiness" [] (partial wrap-readiness-probe (or ready-check (constantly true)))))))
