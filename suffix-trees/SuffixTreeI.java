import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

class Pointer {
    int start;
    int end;

    Pointer(int start, int end) {
        this.start = start;
        this.end = end;
    }
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
        int len = text.length();
        for (int i = 0; i < len - 1; i++ ) {
            root.add(new Pointer(i, len));
        }
    }

    void walk(Consumer<Edge> lambda) {
        walk(root, lambda);
    }

    void walk(Node node, Consumer<Edge> lambda) {
        for (Edge edge : node.edges.values()) {
            lambda.accept(edge);
            walk(edge.to, lambda);
        }
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
                    // Continue recursively with edge.to
                    edge.to.add(new Pointer(pointer.start + diffPos, pointer.end));
                } else {
                    // Append intermediate node between this and edge.node
                    Node intermediate = new Node();
                    this.edges.put(head, new Edge(new Pointer(pointer.start, pointer.start + diffPos), intermediate));

                    intermediate.edges.put(text.charAt(edge.pointer.start + diffPos),
                        new Edge(new Pointer(edge.pointer.start + diffPos, edge.pointer.end), edge.to));
                    intermediate.edges.put(text.charAt(pointer.start + diffPos),
                        new Edge(new Pointer(pointer.start + diffPos, pointer.end), new Node()));
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

