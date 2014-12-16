(ns inventory.views.layout
   (:require [ring.util.response :refer [content-type response]]
             [compojure.core :refer :all]
             [compojure.response :refer [Renderable]]
             [selmer.parser :as parser]
             [selmer.parser :refer [render-file]]))


(def template-folder "inventory/views/templates/")

(defn utf-8-response [html]
  (content-type (response html) "text/html; charset=utf-8"))

(deftype RenderablePage [template params]
  Renderable
  (render [this request]
          (->> (assoc params
                 :context (:context request))
          (parser/render-file (str template-folder template))
          utf-8-response)))

(defn render [template & [params]]
  (RenderablePage. template params))
