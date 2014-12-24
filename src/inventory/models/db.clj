(ns inventory.models.db
  (:require  [taoensso.carmine :as car :refer [wcar]]
             [clojure.string :as str]))

(def server1-conn {:pool {}
                   :spec {:host "localhost"
                          :port 6379}})

(defmacro wcar* [& body]
  `(car/wcar server1-conn ~@body))

;(defn save-to-db [po date desc itemtype qty]
;  (wcar*  (car/hset (str "PO:" po) "date" date)
;          (car/hset (str "PO:" po) "desc" desc )
;          (car/hset (str "PO:" po) "itemtype" itemtype)
;          (car/hset (str "PO:" po) "qty" qty)))

;(defn save-to-db [key-name key-data]
;  (doall (map #(wcar* (car/hset (str (first (keys key-name)) ":" (first (vals key-name))) % (key-data %))) (keys key-data))))

(defn save-hash [key-name key-data]
  (let [hash-name (first (join-keyval key-name ":"))]
    (doall (map #(wcar* (car/hset hash-name % (key-data %))) (keys key-data)))))

(defn save-po [key-name key-data]
  (save-hash key-name key-data))

(defn join-keyval [jmap separator]
  (if (map? jmap)
    (doall (map #(str (first %) separator (second %)) jmap))
    nil))
