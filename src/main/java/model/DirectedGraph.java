package org.example.graph;

import java.util.*;

/**
 * Directed weighted graph representation.
 */
public class DirectedGraph {
    private final int n;
    private final boolean directed;
    private final int source;
    private final Map<Integer, List<Edge>> adj = new HashMap<>();

    public DirectedGraph(int n, boolean directed, int source) {
        this.n = n;
        this.directed = directed;
        this.source = source;

        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
        if (!directed) {
            adj.get(v).add(new Edge(v, u, w));
        }
    }

    public List<Edge> getEdgesFrom(int u) {
        return adj.get(u);
    }

    public int getNodeCount() {
        return n;
    }

    public int getEdgeCount() {
        return adj.values().stream().mapToInt(List::size).sum();
    }

    public int getSource() {
        return source;
    }

    public boolean isDirected() {
        return directed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int u = 0; u < n; u++) {
            for (Edge e : adj.get(u)) {
                sb.append(u).append(" -> ").append(e.v)
                        .append(" (w=").append(e.w).append(")\n");
            }
        }
        return sb.toString();
    }

    /** Inner Edge class */
    public static class Edge {
        public final int u, v, w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}
