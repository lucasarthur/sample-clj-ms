(ns sample.middleware.sse)

(def ^:private sse-headers {"content-type" "text/event-stream"
                            "cache-control" "no-cache"})

(defn wrap-sse-response [handler]
  (fn [req]
    (-> (handler req) (update-in [:headers] #(merge sse-headers %)))))

(defn wrap-raw-sse-response [handler]
  (wrap-sse-response (fn [req] {:status 200 :body (handler req)})))
