(ns inventory.models.db
  (:require  [taoensso.carmine :as car :refer [wcar]]
             [clojure.string :as str]
             [cheshire.core :refer :all]))

(def server1-conn {:pool {}
                   :spec {:host "localhost"
                          :port 6379}})

(defmacro wcar* [& body]
  `(car/wcar server1-conn ~@body))



(defn save-hash [hash-name key-data]
  " Create and commit a hash to a redis database from a map, given a hash name."
  ;; @TODO: Handle empty map
    (doall (map #(wcar* (car/hset hash-name % (key-data %))) (keys key-data))))



(defn mapfield-concat [jmap separator]
  " Returns the concatenated key value pairs as vector of strings. Works only for
    map datatypes."
  ;; @TODO: Incorporate default separator
  ;; @TODO: Order is not preserved
  (if (map? jmap)
    (vec (doall (map #(str (first %) separator (second %)) jmap)))))



(defn save-po [key-name key-data]
  " Saves the purchase order details in the redis db as a hash. The purchase order
    name is maintained in a set separately."
  (let [hash-name (first (mapfield-concat key-name ":"))]
    (save-hash hash-name key-data)
    (wcar* (car/sadd "po:list" hash-name))))



(defn po-count []
  " Returns the count of the number of purchase orders existing in the system."
  (car/wcar server1-conn (car/scard "po:list")))



(defn get-po-list []
  " Obtains a listing of all purchase orders."
  (wcar* (car/smembers "po:list")))



(defn get-po-details [po]
  " Obtains the details of a given purchase order"
  (wcar* (car/hgetall po)))



(defn get-pos []
  " Obtains all the purchase orders from the list and converts them suitably into
    a map with hash fields as keywords (in CAPS). Specifically done for the
    templating engine.
    [\"po\" \"PO01\" \"qty\" 25] [...] --> [{:PO \"PO01\" :QTY 25} {...}]"
  ;; @TODO: Refactor and make it more abstract
  (vec
   (doall
    (map #(into {}
                (for [[k v] (apply hash-map (get-po-details %))]
                  [(keyword (str/upper-case k)) v]))
          (get-po-list)))))


(defn get-pos-json []
  " Obtains all the purchase orders from the list and converts them into
    a json stream."
  (generate-string
   (vec
    (doall
     (map #(apply hash-map (get-po-details %)) (get-po-list))))))
