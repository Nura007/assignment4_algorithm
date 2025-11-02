package org.example.algo.dagsp;

import org.example.model.DirectedGraph;
import org.example.model.Edge;
import org.example.algo.Result;

import java.util.*;

/**
 * Shortest Path in Directed Acyclic Graph (DAG) using Topological Order.
 */
public class DAGShortestPath {

    public static Result run(DirectedGraph g) {
        long start = System.nanoTime();
        int n = g.getNodeCount();
        int src = g.getSource();
        int operations = 0;

        // Step 1: Topological order
        Stack<Integer> stack = topoSort(g);

        // Step 2: Initialize distances
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Step 3: Relax edges following topological order
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge e : g.getEdgesFrom(u)) {
                    int v = e.getV();
                    int w = e.getW();
                    if (dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                    }
                    operations++;
                }
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.println("Shortest distances from source " + src + ": " + Arrays.toString(dist));
        return new Result("DAGShortestPath", dist, operations, timeMs);
    }

    private static Stack<Integer> topoSort(DirectedGraph g) {
        int n = g.getNodeCount();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) dfs(i, g, visited, stack);
        }
        return stack;
    }

    private static void dfs(int u, DirectedGraph g, boolean[] visited, Stack<Integer> stack) {
        visited[u] = true;
        for (Edge e : g.getEdgesFrom(u)) {
            if (!visited[e.getV()]) dfs(e.getV(), g, visited, stack);
        }
        stack.push(u);
    }
}
