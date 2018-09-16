import java.io.*;
import java.util.*;

class Position {
    SuffixTreeI.Node node;
    int count;

    Position(SuffixTreeI.Node node, int count) {
        this.node = node;
        this.count = count;
    }
}

public class NonSharedSubstring implements Runnable {
	String solve(String p, String q) {

	    String combined = q + p + "$";
        SuffixTreeI tree = new SuffixTreeI(combined);
		List<Position> levelNodes = Arrays.asList(new Position(tree.root, 0));
        int qLen = q.length();
        int len = combined.length();

        while (!levelNodes.isEmpty()) {
            List<Position> newLevelNodes = new ArrayList<>();
            for (Position pos : levelNodes) {
                for (Edge edge: pos.node.edges.values()) {
                    if (edge.pointer.start == len - 1) { // Ignore $
                        continue;
                    }
                    if (edge.pointer.start >= qLen) {
                        int i = edge.pointer.start - qLen - pos.count;
                        return p.substring(i, i + pos.count + 1);
                    }
                    newLevelNodes.add(new Position(edge.to, pos.count + edge.pointer.end - edge.pointer.start));
                }     
            }
            levelNodes = newLevelNodes;
        }
		throw new IllegalStateException("Should not happen");
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String p = in.readLine();
			String q = in.readLine();

			String ans = solve(p, q);

			System.out.println(ans);
		}
		catch(Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String [] args) {
		new Thread(new NonSharedSubstring()).start();
	}
}
