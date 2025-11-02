package org.example.algo;

import java.util.Arrays;

/**
 * Common result container for graph algorithms.
 * Used by TarjanSCC, TopologicalSort, and DAGShortestPath.
 */
public class Result {
    public String algorithm;       // Algorithm name
    public int operations;         // Number of operations performed
    public double execTimeMs;      // Execution time (ms)
    public boolean negativeCycle;  // For future use (Bellman-Ford etc.)
    public int[] distances;        // For shortest path results (optional)

    // Constructor for algorithms without distance array
    public Result(String algorithm, int operations, double execTimeMs) {
        this.algorithm = algorithm;
        this.operations = operations;
        this.execTimeMs = execTimeMs;
        this.negativeCycle = false;
    }

    // Constructor for algorithms with distances (e.g. DAGShortestPath)
    public Result(String algorithm, int[] distances, int operations, double execTimeMs) {
        this.algorithm = algorithm;
        this.distances = distances;
        this.operations = operations;
        this.execTimeMs = execTimeMs;
        this.negativeCycle = false;
    }

    @Override
    public String toString() {
        String distStr = (distances != null) ? Arrays.toString(distances) : "N/A";
        return String.format(
                "%s | Time: %.3f ms | Ops: %d | NegCycle: %b | Distances: %s",
                algorithm, execTimeMs, operations, negativeCycle, distStr
        );
    }
}
