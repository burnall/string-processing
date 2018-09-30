(ns knut-morris-pratt.core
  (:gen-class))

(defn compute-borders [s]
  (reduce (fn [borders i]
            (let [get-border (fn [border]
                    (cond (= (s border) (s i)) (inc border)
                          (zero? border) 0
                         :else (recur (borders (dec border)))))
                  border (get-border (peek borders))]
              (conj borders border)))
          [0]    
          (range 1 (count s)))) 

(defn kmp [s p] 
  (let [prefixes (compute-borders (vec (str p "$" s)))
        len-p (count p)]
    (->> prefixes 
         (drop len-p)
         (map vector (range))
         (filter (fn [[i prefix]] (= prefix len-p)))
         (map (fn [[i & _]] (- i len-p))))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
