package org.example.algo.topo;

import org.example.model.DirectedGraph;
import org.example.model.Edge;
import org.example.algo.Result;

import java.util.*;

/**
 * Topological Sort for Directed Acyclic Graph (DAG).
 */
public class TopologicalSort {

    public static Result run(DirectedGraph g) {
        long start = System.nanoTime();
        int n = g.getNodeCount();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        int operations = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                operations += dfs(i, g, visited, stack);
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.println("Topological Order: " + stack);
        return new Result("TopologicalSort", operations, timeMs);
    }

    private static int dfs(int u, DirectedGraph g, boolean[] visited, Stack<Integer> stack) {
        visited[u] = true;
        int operations = 1;
        for (Edge e : g.getEdgesFrom(u)) {
            int v = e.getV();
            if (!visited[v]) operations += dfs(v, g, visited, stack);
        }
        stack.push(u);
        return operations;
    }
}
