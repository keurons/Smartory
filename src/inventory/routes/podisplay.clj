(ns inventory.routes.podisplay
  (:require [compojure.core :refer :all]
            [clojure.string :as str]
            [inventory.views.layout :as layout]
            [selmer.parser :refer [render-file render]]
            [inventory.models.db :refer :all]))

(defn po-display []
    (render-file "inventory/views/templates/podisplay.html"
               {:usermsgs "Working with new db"}))


(defroutes podisplay-routes
  (GET "/podisplay" [_] (po-display)))

