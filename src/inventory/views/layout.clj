(ns inventory.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "CDK Inventory System"]
     (include-css "/css/screen.css")]
    [:body body]))


(def template-folder "inventory/views/templates")


