(ns suffix-array-and-tree.core)

(defn sort-by-char [len get-by-index] 
  (let [index-by-char (reduce (fn [m i] (update m (get-by-index i) (fn [key] (conj (if key key []) i))))  
                              (sorted-map) 
                              (range len))]
    (->> index-by-char
        (vals)
        (apply concat)
        (vec))))

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

(defn get-count[classes]
  (->> classes 
       (frequencies)
       (sort-by first)
       (reduce (fn [[prev-count v] [item cnt]] 
                 (let [new-count (+ prev-count cnt)] 
                   [new-count (conj v [item new-count])]))
               [0 []])
      (second)
      (mapv second)))


(defn sort-cycle-shifts
  ([v-string] 
    (let [order (sort-by-char (count v-string) v-string)
          clazz (get-class-by-char v-string order)]
      (sort-cycle-shifts v-string order clazz 1)))    
  
  ([v-string order clazz n] 
    (if (>= n (count v-string))
      order
      (let [len (count v-string)
            cnt (get-count clazz) 
            order (second (reduce (fn [[counts v] i] 
                            (let [start (mod (+ (- (order i) n) len) len)
                                  cl (clazz start)
                                  newCnt (dec (counts cl))]
                              (do (println i counts start cl newCnt v) [(assoc counts cl newCnt) (assoc v newCnt start)]))) 
                          [cnt (vec (int-array len))]
                          (range (dec len) -1 -1)))
           
           clazz 22]
        order)))) 
        ;(recur v-string order clazz (* 2 n))))))


