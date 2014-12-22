(ns inventory.routes.poentry
  (:require [compojure.core :refer :all]
            [clojure.string :as str]
            [inventory.views.layout :as layout]
            [selmer.parser :refer [render-file]]
            [inventory.models.db :refer :all]
            [inventory.views.layout :as layout]))


(defn po-entry [& [msgs]]
  (render-file "inventory/views/templates/poentry.html"
            {:usrmsgs msgs}))

;(defn po-entry [& [msgs]]
;  (layout/render "poentry.html"
;                 {:usrmsgs msgs}))

(defn save-message [po date desc itemtype qty]
 (po-entry "no database yet"))

(defroutes poentry-routes
  (GET "/poentry" [_] (po-entry))
  (POST "/poentry" [PONumber PODate Description ItemType Qty]
        (save-message PONumber PODate Description ItemType Qty)))


