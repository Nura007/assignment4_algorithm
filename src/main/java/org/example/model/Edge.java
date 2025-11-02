package org.example.model;

/**
 * Represents a directed weighted edge in a graph.
 */
public class Edge {
    private final int u;  // from
    private final int v;  // to
    private final int w;  // weight

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    @Override
    public String toString() {
        return u + " -> " + v + " (w=" + w + ")";
    }
}
