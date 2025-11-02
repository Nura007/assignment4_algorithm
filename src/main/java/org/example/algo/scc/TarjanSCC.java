package org.example.algo.scc;

import org.example.model.DirectedGraph;
import org.example.model.Edge;

import java.util.*;

public class TarjanSCC {
    private final DirectedGraph graph;
    private int time = 0;
    private int operationCount = 0;
    private final Map<Integer, Integer> disc = new HashMap<>();
    private final Map<Integer, Integer> low = new HashMap<>();
    private final Stack<Integer> stack = new Stack<>();
    private final Set<Integer> inStack = new HashSet<>();

    public TarjanSCC(DirectedGraph graph) {
        this.graph = graph;
    }

    public void run() {
        for (int node = 0; node < graph.getNodeCount(); node++) {
            if (!disc.containsKey(node)) {
                dfs(node);
            }
        }
    }

    private void dfs(int u) {
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.add(u);
        operationCount++;

        for (Edge e : graph.getEdgesFrom(u)) {
            int v = e.v;
            if (!disc.containsKey(v)) {
                dfs(v);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (inStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        // Root of SCC found
        if (Objects.equals(low.get(u), disc.get(u))) {
            System.out.print("SCC: ");
            while (true) {
                int v = stack.pop();
                inStack.remove(v);
                System.out.print(v + " ");
                if (v == u) break;
            }
            System.out.println();
        }
    }

    public int getOperationCount() {
        return operationCount;
    }
}
