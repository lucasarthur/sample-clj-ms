(ns sample.util.app
  (:require [clojure.repl :refer [set-break-handler!]]))

(defn on-shutdown [f]
  (set-break-handler! (fn [_] (f)))
  (->> f Thread. (.addShutdownHook (Runtime/getRuntime))))
