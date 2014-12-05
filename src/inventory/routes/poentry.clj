(ns inventory.routes.poentry
  (:require [compojure.core :refer :all]
            [clojure.string :as str]
            [inventory.views.layout :as layout]
            [selmer.parser :refer [render-file]]
            [inventory.models.db :refer :all]))

;(defn po-entry []
;  (layout/common [:h1 "Welcome to CDK Inventory System PO Entry page!"]))

(defn po-entry [& [msgs]]
  (render-file "inventory/views/templates/poentry.html"
             {:usrmsgs msgs
              :stylesheetloc "screen.css"}))


(defn save-message [po date desc itemtype qty]
  (cond
   (empty? (str/trim po))
     (po-entry "Please enter the PO number")
   (empty? date)
     (po-entry "Date is an important field. Please enter the purchase order date.")
   (empty? (str/trim qty))
     (po-entry "Please enter the quantity")
  :else
   (do
      (save-msg-db po date desc itemtype qty)
      (po-entry "Purchase order has been archived sucessfully!"))))


(defroutes poentry-routes
  (GET "/poentry" [_] (po-entry))
  (POST "/poentry" [PONumber PODate Description ItemType Qty] (save-message PONumber PODate Description ItemType Qty)))


