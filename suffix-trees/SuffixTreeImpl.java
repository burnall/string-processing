import java.util.Map;
import java.util.HashMap;

class Pointer {
    int start;
    int end;

    @Override
    public boolean equals(Object o) {
        Pointer p = (Pointer) o;
        return start == p.start && end == p.end;
    }

    @Override 
    public int hashCode() {
        return 31 * start + end; 
    }
}

public class SuffixTreeImpl {
    String text;
    Node root = new Node();
    
    SuffixTreeImpl(String text) {
       this.text = text;
    }

    class Node {
        Map<Pointer, Node> edges = new HashMap<>();
        void add(Pointer pointer) {
        
        }
    }
}
