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
  (reduce (fn [{:keys [indexByElem  indexByPos]} ch] 
             (let [pos (count indexByPos)
                   elems (get indexByElem ch [])]
               {:indexByPos (conj indexByPos (count elems)) 
                :indexByElem (assoc indexByElem ch (conj elems pos))}))
          {:indexByPos [], :indexByElem {}} 
          s))

(defn invert-better [s] 
  (let [ss (sort s)
        s-i (get-indexes s)
        ss-i (get-indexes ss)
       ]
       22
))


(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
