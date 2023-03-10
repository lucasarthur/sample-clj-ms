(defproject sample-clj-ms "1.0.0"
  :description "a microservice sample in clojure"
  :url "https://github.com/lucasarthur/sample-clj-ms"
  :license {:name "GNU General Public License v3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.pt-br.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]                                                      ;; clojure, of coure
                 [manifold "0.3.0" :exclusions [org.clj-commons/dirigiste]]                          ;; async and event-driven communication
                 [aleph "0.6.1"]                                                                     ;; netty async web server
                 [ring/ring-defaults "0.3.4"]                                                        ;; wrap-defaults
                 [ring/ring-json "0.5.1"]                                                            ;; wrap-json
                 [compojure "1.7.0"]                                                                 ;; routing
                 [metosin/compojure-api "2.0.0-alpha31"]                                             ;; swagger, etc
                 [cheshire "5.11.0"]                                                                 ;; json
                 [com.appsflyer/ketu "1.0.0" :exclusions [org.slf4j/slf4j-api]]                      ;; async kafka client
                 [com.brunobonacci/mulog "0.9.0"]                                                    ;; logs (guess what, async)
                 [nonseldiha/slf4j-mulog "0.2.1"]                                                    ;; slf4j backend -> mulog
                 [environ "1.2.0"]                                                                   ;; env vars/configs
                 [com.github.seancorfield/next.jdbc "1.3.858"]                                       ;; jdcb client
                 [com.github.seancorfield/honeysql "2.4.1002"]                                       ;; sql clojure dsl
                 [lobos "1.0.0-beta3"]                                                               ;; sql migration
                 [clj-commons/iapetos "0.1.13"]                                                      ;; prometheus client - metrics
                 [io.prometheus/simpleclient_hotspot "0.16.0"]]                                      ;; jvm metrics
  :plugins [[lein-environ "1.2.0"]]                                                                  ;; generate .lein-env based on :env from project
  :main ^:skip-aot sample.core
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
