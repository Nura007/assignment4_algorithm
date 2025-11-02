package org.example.algo.dagsp;

import org.example.model.DirectedGraph;
import org.example.model.Edge;
import org.example.algo.topo.TopologicalSort;

import java.util.*;

public class DAGShortestPath {
    private final DirectedGraph graph;
    private int operationCount = 0;

    public DAGShortestPath(DirectedGraph graph) {
        this.graph = graph;
    }

    public int[] run() {
        int n = graph.getNodeCount();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[graph.getSource()] = 0;

        TopologicalSort topo = new TopologicalSort(graph);
        List<Integer> order = topo.run();

        for (int u : order) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge e : graph.getEdgesFrom(u)) {
                    if (dist[e.v] > dist[u] + e.w) {
                        dist[e.v] = dist[u] + e.w;
                        operationCount++;
                    }
                }
            }
        }

        System.out.println("Shortest distances from source " + graph.getSource() + ": " + Arrays.toString(dist));
        return dist;
    }

    public int getOperationCount() {
        return operationCount;
    }
}
