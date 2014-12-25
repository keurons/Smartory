(ns inventory.models.db
  (:require  [taoensso.carmine :as car :refer [wcar]]
             [clojure.string :as str]))

(def server1-conn {:pool {}
                   :spec {:host "localhost"
                          :port 6379}})

(defmacro wcar* [& body]
  `(car/wcar server1-conn ~@body))

(defn save-hash [hash-name key-data]
;; Create and commit a hash to a redis database from a map, given a
;; hash name.
;; @TODO: Handle empty map
    (doall (map #(wcar* (car/hset hash-name % (key-data %))) (keys key-data))))

(defn save-po [key-name key-data]
  (let [hash-name (first (mapfield-concat key-name ":"))]
    (save-hash hash-name key-data)
    (wcar* (car/sadd "po:list" hash-name))))

(defn mapfield-concat [jmap separator]
;; Returns the concatenated key value pairs as vector of strings. Works only for
;; map datatypes.
;; @TODO: Incorporate default separator
  (if (map? jmap)
    (doall (map #(str (first %) separator (second %)) jmap))
    nil))
