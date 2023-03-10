(ns sample.config.metrics
  (:require
   [environ.core :refer [env]]
   [compojure.core :refer [defroutes context GET]]
   [cheshire.core :refer [generate-string]]))

(def ^:private health-path
  (str (env :metrics-path) "/health"))

(def ^:private health-statuses
  {:up       {:status 200 :message "up"}
   :down     {:status 503 :message "down"}
   :starting {:status 503 :message "starting"}})

(def metrics-cfg-map {:path (env :metrics-path)})

(defonce health (atom (:starting health-statuses)))

(defn health-status [] @health)

(defn set-health-status! [status]
  (swap! health (constantly (status health-statuses))))

(defn- health-handler [_]
  (let [h (health-status)]
    {:status (:status h)
     :headers {"content-type" "application/json"}
     :body (generate-string {:message (:message h)})}))

(defroutes health-checks
  (context health-path []
    (GET "/" [] health-handler)
    (GET "/liveness" [] health-handler)
    (GET "/readiness" [] health-handler)))
