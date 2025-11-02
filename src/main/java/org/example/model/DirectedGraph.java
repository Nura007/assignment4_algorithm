package org.example.model;

import java.util.*;

public class DirectedGraph {
    private final Map<Integer, List<Edge>> adj = new HashMap<>();
    private int nodeCount;
    private int source = 0;

    public DirectedGraph(int nodeCount) {
        this.nodeCount = nodeCount;
        for (int i = 0; i < nodeCount; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
    }

    public List<Edge> getEdgesFrom(int u) {
        return adj.getOrDefault(u, new ArrayList<>());
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> list : adj.values()) {
            edges.addAll(list);
        }
        return edges;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DirectedGraph:\n");
        for (Map.Entry<Integer, List<Edge>> e : adj.entrySet()) {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
