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
    

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
