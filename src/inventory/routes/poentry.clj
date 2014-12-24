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
 (cond
   (empty? (str/trim po))
     (po-entry "Please enter the PO number")
   (empty? date)
     (po-entry "Date is an important field. Please enter the purchase order date.")
   (empty? desc)
     (po-entry "Please enter information about this purcahse order in the description field.")
   (empty? (str/trim qty))
     (po-entry "Please enter the quantity")
  :else
   (do
      (save-po
         {"po" po}
         {"date" date "desc" desc "itemtype" itemtype "qty" qty})
      (po-entry "Purchase order has been archived sucessfully!"))))

(defroutes poentry-routes
  (GET "/poentry" [_] (po-entry))
  (POST "/poentry" [PONumber PODate Description ItemType Qty]
        (save-message PONumber PODate Description ItemType Qty)))


