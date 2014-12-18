(ns inventory.routes.home
  (:require [compojure.core :refer :all]
            [inventory.views.layout :as layout]
            [selmer.parser :refer [render-file]]))

(defn home []
  (render-file "inventory/views/templates/welcome.html"
               {}))

(defroutes home-routes
  (GET "/" [] (home)))

