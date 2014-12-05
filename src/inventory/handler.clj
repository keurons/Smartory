(ns inventory.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [inventory.routes.home :refer [home-routes]]
            [inventory.routes.poentry :refer [poentry-routes]]
            [inventory.routes.podisplay :refer [podisplay-routes]]
            [inventory.models.db :as db]))

(defn init []
  (println "Inventory service is starting")
  (if-not (.exists (java.io.File. "./invdb.sq3"))
    (db/create-po-table)))

(defn destroy []
  (println "Inventory service is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes poentry-routes podisplay-routes app-routes)
      (handler/site)
      (wrap-base-url)))
