import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

   void walk(MyTrie myTrie, List<Map<Character, Integer>> trie) {
       Map<Character, Integer> map = new HashMap<>();
       Integer i = trie.size();
       trie.add(map);
       
       for (Map.Entry<Character, MyTrie> entry : myTrie.edges.entrySet()) {
           map.put(entry.getKey(), trie.size());
           walk(entry.getValue(), trie);
       } 
   }
   
    List<Map<Character, Integer>> buildTrie(String[] patterns) {

        MyTrie root = new MyTrie();
        for (int i = 0; i < patterns.length; i++) {
            root.addPattern(patterns[i]); 
        }

        List<Map<Character, Integer>> trie = new ArrayList<>();
        walk(root, trie);
        
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        
        System.out.println(Arrays.toString(patterns));

        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}

