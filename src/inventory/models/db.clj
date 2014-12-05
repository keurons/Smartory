(ns inventory.models.db
  (:require [korma.db :refer :all])
  (:require [korma.core :refer :all])
  (:require [clojure.string :as str])
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))


(def dbspec {:classname   "org.sqlite.JDBC",
             :subprotocol "sqlite",
             :subname     "invdb.sq3"})

(defn create-po-table []
  (sql/db-do-commands
    dbspec
    (sql/create-table-ddl
     :purchaseorder
     [:po "varchar(32)" :primary :key]
     [:date :date]
     [:item :text]
     [:qty  :int]
     [:desc :text]
     :entities str/upper-case)))

(defdb korma-db dbspec)

(defentity purchaseorder)

(defn save-msg-db [po date desc item qty]
  (insert purchaseorder
            (values [{:po po :date date :desc desc :item item :qty qty}])))

(defn get-all-pos []
  (select purchaseorder
          (fields :po :date :item :qty :desc)))
;          (where (or (= :qty 21)
;                     (>= :qty 50)))))

(defn po-count []
   (first
    (map :cnt
      (select purchaseorder
        (aggregate (count :PO) :cnt)))))

;; Code for deleting orders
;(delete purchaseorder
;        (where {:po ""}))


(defn drop-tables []
  (sql/db-do-commands
   dbspec
   (sql/drop-table-ddl
    :purchaseorder)))


