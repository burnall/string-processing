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


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
