(defproject inventory "0.1.0-SNAPSHOT"
  :description "Internal inventory management system"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 ;[org.clojure/java.jdbc "0.3.6"] ;korma should pull this automatically
                 [org.xerial/sqlite-jdbc "3.8.7"]
                 [korma "0.4.0"]
                 [selmer "0.7.3"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler inventory.handler/app
         :init inventory.handler/init
         :destroy inventory.handler/destroy}
  :profiles {
              :uberjar {:aot :all}
              :production {
                            :ring {
                                    :open-browser? false,
                                    :stacktraces? false,
                                    :auto-reload? true,
                                    :auto-refresh? true }}
             :dev {
                   :dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
