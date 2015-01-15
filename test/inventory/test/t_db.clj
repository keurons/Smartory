(ns inventory.test.t_db
  (:use midje.sweet
        inventory.models.db))


(facts "About combining maps' keys & values into strings"

       (fact "returns vector of strings combining keys and their respective values
               in a map"
              (mapfield-concat {"name" "kou"}, ":") => ["name:kou"]) ;; one k-v pair

       (fact "order of strings in vectors is preserved as per the input map"
              (mapfield-concat {"name" "kou" "age" 41 "loc" "baner"}, ":") => ["name:kou" "age:41" "loc:baner"]) ;; multiple pairs

       (fact "works with different separators"
              (mapfield-concat {"name" "kou"}, ",") => ["name,kou"]
              (mapfield-concat {"name" "kou"}, "=>") => ["name=>kou"]
              (mapfield-concat {"name" "kou"}, "\\") => ["name\\kou"]
              (mapfield-concat {"name" "kou"}, "") => ["namekou"])
              ;; is a check required for length of separator?

       (fact "works only for non empty maps returning nil otherwise"
              (nil? (mapfield-concat [] ":")) => true
              (empty? (mapfield-concat {} ":")) => true))


(facts "About fetching all purchase orders"

       (fact "returns vector of maps with each map representing a purchase order"
             (get-pos) => [{:PO "PO22" :QTY "25"}{:PO "PO23" :DESC "new macbooks" :DATE "31.12.2014"}]
             (provided
                (get-po-list) => [..po1.. ..po2..]
                (get-po-details ..po1..) => ["po" "PO22" "qty" "25"]
                (get-po-details ..po2..) => ["po" "PO23" "desc" "new macbooks" "date" "31.12.2014"]))

       (fact "returns a stream of json objects each representing a purchase order"
             (get-pos-json) => [{"po":"PO41","itemtype":"macbook"},{"po":"PO32","itemtype":"adapter"}]
             (provided
              (get-po-list) => [..po3.. ..po4..]
              (get-po-details ..po3..) => ["po" "PO41" "itemtype" "macbook"]
              (get-po-details ..po4..) => ["po" "PO32" "itemtype" "adapter"])))


