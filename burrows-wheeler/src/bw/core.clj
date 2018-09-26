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

(defn invert-better [s] 
  (let [lst (vec s)
        fst (vec (sort lst))
        {fst-by-elem :index-by-elem, fst-by-pos :index-by-pos} (get-indexes fst)
        {lst-by-elem :index-by-elem, lst-by-pos :index-by-pos}  (get-indexes lst)
        step (fn [items] 
          (let [i (first items)
                ch (lst i)
                pos (lst-by-pos i)
                j  ((get fst-by-elem ch) pos)]
             (cons j items)))]   
    (apply str (map lst (nth (iterate step '(0)) (dec (count lst)))))))   
       


(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
