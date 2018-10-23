(ns suffix-array-and-tree.core

(defn sort-by-char[strings] 
  (let [index-by-char (reduce (fn [m [[c] i]] (update m c (fn [key] (conj (if key key []) i))))  
                              (sorted-map) 
                              (map vector strings (range)))]
    (->> index-by-char
        (vals)
        (apply concat))))
