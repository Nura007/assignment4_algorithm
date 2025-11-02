package org.example.model;

import java.util.*;

/**
 * Directed weighted graph structure for Tarjan, Topological Sort, and DAG Shortest Path.
 */
public class DirectedGraph {
    private final int n;
    private final boolean directed;
    private final int source;
    private final Map<Integer, List<Edge>> adj = new HashMap<>();

    /**
     * Constructor
     *
     * @param n        number of nodes
     * @param directed true if the graph is directed
     * @param source   source vertex for path algorithms
     */
    public DirectedGraph(int n, boolean directed, int source) {
        this.n = n;
        this.directed = directed;
        this.source = source;

        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    /** Adds an edge to the graph */
    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
        if (!directed) {
            adj.get(v).add(new Edge(v, u, w));
        }
    }

    /** Returns adjacency list for a given node */
    public List<Edge> getEdgesFrom(int u) {
        return adj.get(u);
    }

    /** Returns all edges in the graph (used by some algorithms) */
    public List<Edge> getAllEdges() {
        List<Edge> all = new ArrayList<>();
        for (List<Edge> list : adj.values()) {
            all.addAll(list);
        }
        return all;
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
        sb.append("DirectedGraph (n=").append(n)
                .append(", edges=").append(getEdgeCount()).append(")\n");
        for (int u = 0; u < n; u++) {
            for (Edge e : adj.get(u)) {
                sb.append("  ").append(e).append("\n");
            }
        }
        return sb.toString();
    }
}
