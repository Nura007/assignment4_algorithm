package org.example.algo.topo;

import org.example.model.DirectedGraph;
import org.example.model.Edge;

import java.util.*;

public class TopologicalSort {
    private final DirectedGraph graph;
    private int operationCount = 0;

    public TopologicalSort(DirectedGraph graph) {
        this.graph = graph;
    }

    public List<Integer> run() {
        boolean[] visited = new boolean[graph.getNodeCount()];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < graph.getNodeCount(); i++) {
            if (!visited[i]) dfs(i, visited, stack);
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) result.add(stack.pop());
        return result;
    }

    private void dfs(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        operationCount++;

        for (Edge e : graph.getEdgesFrom(node)) {
            if (!visited[e.v]) dfs(e.v, visited, stack);
        }

        stack.push(node);
    }

    public int getOperationCount() {
        return operationCount;
    }
}
