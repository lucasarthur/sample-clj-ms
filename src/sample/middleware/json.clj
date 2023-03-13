(ns sample.middleware.json
  (:require
   [clojure.java.io :refer [reader]]
   [cheshire.core :refer [parse-stream generate-string]]))

(def json-content-type-header {"content-type" "application/json"})

(defn byte-stream->json [stream]
  (-> stream reader (parse-stream true)))

(defn wrap-json-request [handler]
  (fn [req]
    (handler (->> (-> req :body byte-stream->json) (assoc req :body)))))

(defn wrap-json-response [handler]
  (fn [req]
    (-> (handler req)
        (update :body #(generate-string %))
        (update-in [:headers] #(merge % json-content-type-header)))))

(defn wrap-json [handler]
  (-> handler wrap-json-request wrap-json-response))
