(ns suffix-array-and-tree.core)

(defn sort-by-char[strings] 
  (let [index-by-char (reduce (fn [m [[c] i]] (update m c (fn [key] (conj (if key key []) i))))  
                              (sorted-map) 
                              (map vector strings (range)))]
    (->> index-by-char
        (vals)
        (apply concat))))

(defn get-classes[strings index-by-char]
  (->> index-by-char 
       (reduce (fn [{:keys [last-char last-order classes]} index]
                 (let [ch (first (strings index))
                       order (+ last-order (if (= ch last-char) 0 1))]   
                   {:last-char ch
                    :last-order order
                    :classes (conj classes order)}))
               {:last-char (-> index-by-char (first) (strings) (first))
                :last-order 0
                :classes []})
       (:classes)))         

(defn t [strings]
  (->> strings
       (sort-by-char)
       (get-classes strings)))

