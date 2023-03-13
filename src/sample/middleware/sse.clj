(ns sample.middleware.sse)

(def ^:private sse-headers {"content-type" "text/event-stream"
                            "cache-control" "no-cache"})

(defn wrap-sse-response [handler]
  (fn [req] (-> (handler req) (update-in [:headers] #(merge % sse-headers)))))
