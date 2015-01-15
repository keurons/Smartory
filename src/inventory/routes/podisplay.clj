(ns inventory.routes.podisplay
  (:require [compojure.core :refer :all]
            [clojure.string :as str]
            [inventory.views.layout :as layout]
            [selmer.parser :refer [render-file render]]
            [inventory.models.db :refer :all]))


(defn po-display []
    (if (> (po-count) 0)
      (render-file "inventory/views/templates/podisplay.html"
               {})
      (render-file "inventory/views/templates/podisplay.html"
               {:usermsgs "No purchase orders present in the database"})))


(defn po-data []
  (get-pos-json))


(defroutes podisplay-routes
  (GET "/podisplay" [_] (po-display))
  (GET "/podata" [_] (po-data)))

