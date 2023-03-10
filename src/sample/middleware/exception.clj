(ns sample.middleware.exception
  (:require
   [com.brunobonacci.mulog :refer [log]]
   [cheshire.core :refer [generate-string]]
   [clojure.string :refer [split]]))

(defn- exception->map [e]
  (-> (.toString e)
      (split #": ")
      (#(hash-map :error (first %) :message (second %)))))

(defn wrap-exceptions [handler]
  (fn [req] (try (handler req)
                 (catch Exception e
                   (log :error :exception e)
                   {:status 500
                    :headers {"content-type" "application/json"}
                    :body (-> e exception->map generate-string)}))))
