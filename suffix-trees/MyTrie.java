import java.util.Map;
import java.util.HashMap;

public class MyTrie {
    Map<Character, MyTrie> edges = new HashMap<>();
    boolean patternEnd = false;     

    void addPattern(String pattern) {
		MyTrie current = this;
		for (int i = 0; i < pattern.length(); i++) {
		    MyTrie next = current.edges.get(pattern.charAt(i));
			if (next == null) {
			    next = new MyTrie();
				current.edges.put(pattern.charAt(i), next);
			} 
		    current = next;
		}    
        current.patternEnd = true;
    } 

    boolean match(String text) {
        MyTrie current = this;
        for (int i = 0; i < text.length(); i++) {
            if (current.patternEnd) {
                return true;
            }
            MyTrie next = current.edges.get(text.charAt(i));
            if (next == null) {
                return false;
		    }
            current = next;
        }
        return current.patternEnd;
    }
}


