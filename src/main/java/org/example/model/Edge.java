package org.example.model;

public class Edge {
    public int u; // from
    public int v; // to
    public int w; // weight

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public String toString() {
        return u + " -> " + v + " (w=" + w + ")";
    }
}
