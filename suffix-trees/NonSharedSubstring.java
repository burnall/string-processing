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

	    String combined = q + "#" + p + "$";
        SuffixTreeI tree = new SuffixTreeI(combined);
		List<Position> levelNodes = Arrays.asList(new Position(tree.root, 0));
        int qLen = q.length() + 1;
        int len = combined.length();

        int minCount = len;
        int indexOfMin = -1;

        while (!levelNodes.isEmpty()) {
            List<Position> newLevelNodes = new ArrayList<>();
            for (Position pos : levelNodes) {
                if (pos.count >= minCount) {
                    continue;
                }
                for (Edge edge: pos.node.edges.values()) {
                    if (edge.pointer.start < qLen && edge.pointer.end >= qLen) {
                        continue;
                    }
                    if (edge.pointer.start == len - 1) { // Ignore $
                        continue;
                    }
                    if (edge.pointer.start >= qLen) {
                        if (pos.count < minCount) {
                            minCount = pos.count;
                            indexOfMin =  edge.pointer.start - qLen - pos.count;
                        }
                        continue;
                    }
                    newLevelNodes.add(new Position(edge.to, pos.count + edge.pointer.end - edge.pointer.start));
                }     
            }
            levelNodes = newLevelNodes;
        }
        return p.substring(indexOfMin, indexOfMin + minCount + 1);
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
