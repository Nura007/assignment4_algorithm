package org.example.algo.scc;

import org.example.model.DirectedGraph;
import org.example.model.Edge;
import org.example.algo.Result;

import java.util.*;

/**
 * Tarjan's Algorithm for Strongly Connected Components (SCC).
 */
public class TarjanSCC {
    private static int time;
    private static int[] disc, low;
    private static boolean[] stackMember;
    private static Stack<Integer> stack;
    private static int operations;

    public static Result run(DirectedGraph g) {
        long start = System.nanoTime();
        int n = g.getNodeCount();

        time = 0;
        operations = 0;
        disc = new int[n];
        low = new int[n];
        stackMember = new boolean[n];
        stack = new Stack<>();

        Arrays.fill(disc, -1);
        int sccCount = 0;

        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                dfs(i, g);
                sccCount++;
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.println("SCC Count: " + sccCount);
        return new Result("TarjanSCC", operations, timeMs);
    }

    private static void dfs(int u, DirectedGraph g) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        stackMember[u] = true;

        for (Edge e : g.getEdgesFrom(u)) {
            int v = e.getV();
            operations++;
            if (disc[v] == -1) {
                dfs(v, g);
                low[u] = Math.min(low[u], low[v]);
            } else if (stackMember[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            System.out.print("SCC: ");
            while (true) {
                int v = stack.pop();
                stackMember[v] = false;
                System.out.print(v + " ");
                if (v == u) break;
            }
            System.out.println();
        }
    }
}
