(ns bw.core
  (:gen-class))

(defn shift-right[s] 
  (str (last s) (subs s 0 (dec (count s)))))

(defn transform-burrows-wheeler0 [s]
  (apply str (map last 
                  (sort (take (count s) 
                              (iterate shift-right s))))))

(defn transform-burrows-wheeler [s]
  (->> s (iterate shift-right)
          (take (count s))
          (sort)
          (map last)
          (apply str)
        ))


(defn invert0 [s] 
  (letfn [(step [items] (sort (map str s items)))] 
    (first (nth (iterate step (repeat (count s) "")) (count s))))) 
   
(defn invert [s] 
  (letfn [(step [items] (sort (map str s items)))] 
    (-> s
        (count)
        (repeat "")
        (->> (iterate step))
        (nth (count s))
        (first))))

; [2 3 1 1 2] -> :indexByElem{1 [2 3], 2 [0 4], 3 [1]},  :indexByPos [0 0 0 1 1] 
(defn get-indexes [s]
  (reduce (fn [{:keys [index-by-elem  index-by-pos]} ch] 
             (let [pos (count index-by-pos)
                   elems (get index-by-elem ch [])]
               {:index-by-pos (conj index-by-pos (count elems)) 
                :index-by-elem (assoc index-by-elem ch (conj elems pos))}))
          {:index-by-pos [], :index-by-elem {}} 
          s))

; [1 1 2 3 3 3] -> {1 0, 2 2, 3 3}
(defn get-index-for-sorted [s]
  (reduce (fn [index-map [i ch]] 
            (if (contains? index-map ch) 
              index-map 
              (assoc index-map ch i)))
          {}
          (map vector (range) s))) 

; [2 3 1 1 2] -> [0 0 0 1 1]
(defn get-index-for-unsorted [s]
  (->> s
       (reduce (fn [{:keys [count-by-elem index]} ch]
                 (let [cnt (get count-by-elem ch 0)]
                    {:count-by-elem (assoc count-by-elem ch (inc cnt))
                     :index (conj index cnt)}))    
               {:count-by-elem {}
               :index []})
      (:index)))         


(defn invert-better [s] 
  (let [lst (vec s)
        fst (vec (sort lst))
        fst-index (get-index-for-sorted fst)
        lst-index (get-index-for-unsorted lst)
        step (fn [items] 
          (let [i (first items)
                ch (lst i)
                pos (lst-index i)
                j  (+ (get fst-index ch) pos)]
             (cons j items)))]   
    (->> '(0)
         (iterate step)
         ((fn [list] (nth list (dec (count lst)))))
         (map lst)
         (apply str))))
    
(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
