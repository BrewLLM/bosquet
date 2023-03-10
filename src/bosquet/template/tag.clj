(ns bosquet.template.tag
  (:require
    [bosquet.openai :as openai]
    [clojure.string :as string]
    [selmer.parser :as parser]))

(defn args->map [args]
  (reduce (fn [m arg]
            (let [[k v] (string/split arg #"=")]
              (assoc m (keyword k) v)))
    {} args))

(defn add-tags []
  (parser/add-tag! :llm-generate
    (fn [args {pretext :selmer/preceding-text}]
      (openai/complete pretext (args->map args)))))
