(defproject sample-clj-ms "1.0.0"
  :description "a microservice sample in clojure"
  :url "https://github.com/lucasarthur/sample-clj-ms"
  :license {:name "GNU General Public License v3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.pt-br.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-web-commons "0.1.2"]
                 [environ "1.2.0"]
                 [com.github.seancorfield/next.jdbc "1.3.858"]
                 [com.github.seancorfield/honeysql "2.4.1002"]
                 [lobos "1.0.0-beta3"]]
  :plugins [[lein-environ "1.2.0"]]
  :main ^:skip-aot sample.core
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
