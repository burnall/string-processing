(ns suffix-array-and-tree.core)

(defn sort-by-char [len get-by-index] 
  (let [index-by-char (reduce (fn [m i] (update m (get-by-index i) (fn [key] (conj (if key key []) i))))  
                              (sorted-map) 
                              (range len))]
    (->> index-by-char
        (vals)
        (apply concat))))

(defn get-class-by-char [get-by-index index-by-char]
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

(defn class-by-char-for-strings [strings] 
  (let [get-by-index #(first (strings %))]
     (->> (sort-by-char (count strings) get-by-index)
          (get-class-by-char get-by-index))))

(defn class-by-char-for-cycle-shifts [v-string] 
  (->> v-string
       (sort-by-char (count v-string))
       (get-class-by-char v-string)))

(defn sort-cycle-shifts
  ([v-string] 
    (let [order (sort-by-char (count v-string) v-string)
          clazz (get-class-by-char v-string order)]
      (sort-cycle-shifts v-string order clazz)))    
  
  ([v-string order clazz]

    ))


