(ns suffix-array-and-tree.core)

(defn sort-by-char [len get-by-index] 
  (let [index-by-char (reduce (fn [m i] (update m (get-by-index i) (fn [key] (conj (if key key []) i))))  
                              (sorted-map) 
                              (range len))]
    (->> index-by-char
        (vals)
        (apply concat))))

(defn get-classes [get-by-index index-by-char]
  (->> index-by-char 
       (reduce (fn [{:keys [last-char last-order classes]} index]
                 (let [ch (get-by-index index)
                       order (+ last-order (if (= ch last-char) 0 1))]   
                   {:last-char ch
                    :last-order order
                    :classes (assoc classes index order)}))
               {:last-char (-> index-by-char (first) (get-by-index))
                :last-order 0
                :classes (vec (int-array (count index-by-char)))})
       (:classes)))         

(defn t [strings] 
  (let [get-by-index #(first (strings %))]
     (->> (sort-by-char (count strings) get-by-index)
          (get-classes get-by-index))))

(defn u [v] 
  (->> v
       (sort-by-char (count v))
       (get-classes v)))

