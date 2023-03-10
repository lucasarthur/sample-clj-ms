(ns sample.util.sse)

(def ^:private EOL "\n")

(defn ->sse
  ([data] (->sse data "message"))
  ([data event] (->sse data event (random-uuid)))
  ([data event id]
   (-> (str "id:" id EOL)
       (str "event:" event EOL)
       (str "data:" data EOL EOL))))
