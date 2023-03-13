(ns sample.config.log
  (:require
   [com.brunobonacci.mulog :refer [start-publisher!]]
   [org.slf4j.impl.mulog :refer [set-level!]]
   [environ.core :refer [env]])
  (:import [org.slf4j LoggerFactory]))

(defn slf4j-logger
  ([] (slf4j-logger (str *ns*)))
  ([log-ns] (LoggerFactory/getLogger log-ns)))

(defn init-logs
  ([] (init-logs (-> env :log-level keyword)))
  ([level]
   (start-publisher! {:type :console :pretty? true})
   (set-level! level)))
