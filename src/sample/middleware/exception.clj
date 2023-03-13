(ns sample.middleware.exception
  (:require
   [com.brunobonacci.mulog :refer [log]]
   [cheshire.core :refer [generate-string]]
   [clojure.string :refer [split]])
  (:import [clojure.lang ExceptionInfo]))

(defn- exception->json [e]
  (-> (.toString e)
      (split #": ")
      (#(hash-map :error (first %) :message (second %)))
      (generate-string)))

(defn- exception-info->json [e]
  (->> (ex-message e) (hash-map :message) (generate-string)))

(defn- extract-status [e]
  (or (-> e ex-data :status) 500))

(defn wrap-exceptions [handler]
  (fn [req]
    (try (handler req)
         (catch ExceptionInfo e
           (log :error :exception e :data (ex-data e))
           {:status (extract-status e)
            :headers {"content-type" "application/json"}
            :body (-> e exception-info->json)})
         (catch Exception e
           (log :error :exception e)
           {:status 500
            :headers {"content-type" "application/json"}
            :body (-> e exception->json)}))))
