import java.util.Map;
import java.util.HashMap;

class Pointer {
    int start;
    int end;
}

class Edge {
    Pointer pointer;
    SuffixTreeI.Node to;

    Edge(Pointer pointer, SuffixTreeI.Node to) {
        this.pointer = pointer;
        this.to = to;
    }
}

public class SuffixTreeI {
    String text;
    Node root = new Node();
    
    SuffixTreeI(String text) {
       this.text = text;
    }

    class Node {
        Map<Character, Edge> edges = new HashMap<>();

        void add(Pointer pointer) {
            Character head = SuffixTreeI.this.text.charAt(pointer.start);
            Edge edge = edges.get(head);
            if (edge == null) {
                edges.put(head, new Edge(pointer, new Node())); 
                 
            } else {
                int diffPos = getFirstDiffPosition(pointer, edge.pointer);
                if (diffPos == edge.pointer.end - edge.pointer.start) {
                    // Continue recursively with edge.node
                } else {
                    // Append intermediate node between this and edge.node
                
                }
            } 
         }

         int getFirstDiffPosition(Pointer p1, Pointer p2) {
             int current = 1;
             int max = Math.max(p1.end - p1.start, p2.end - p2.start);
             String text = SuffixTreeI.this.text;
             while (current < max) {
                 if (text.charAt(p1.start + current) == text.charAt(p2.start + current)) {
                     current++;
                 } else {
                     break;
                 }
             }
             return current;
         }

    }
}

