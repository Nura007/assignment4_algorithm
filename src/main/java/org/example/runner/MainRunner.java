package org.example.runner;

import org.example.algo.Result;
import org.example.algo.scc.TarjanSCC;
import org.example.algo.topo.TopologicalSort;
import org.example.algo.dagsp.DAGShortestPath;
import org.example.data.GraphLoader;
import org.example.model.DirectedGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainRunner {

    public static void main(String[] args) {
        String[] files = {
                "small_graph.json",
                "medium_graph.json",
                "large_graph.json"
        };

        List<Result> allResults = new ArrayList<>();

        for (String file : files) {
            System.out.println("\n===============================");
            System.out.println("📂 Processing " + file);
            System.out.println("===============================");

            DirectedGraph g = GraphLoader.loadGraph(file);
            if (g == null) {
                System.out.println("⚠️ Skipping " + file + " (not found)");
                continue;
            }

            // Run algorithms
            System.out.println("\n--- Tarjan SCC ---");
            Result tarjan = TarjanSCC.run(g);
            allResults.add(tarjan);

            System.out.println("\n--- Topological Sort ---");
            Result topo = TopologicalSort.run(g);
            allResults.add(topo);

            System.out.println("\n--- DAG Shortest Path ---");
            Result dag = DAGShortestPath.run(g);
            allResults.add(dag);

            System.out.println("\n✅ Finished processing " + file);
        }

        // Save all results to CSV
        saveToCSV(allResults, "results.csv");
        System.out.println("\n💾 Results saved to results.csv");
    }

    /**
     * Writes algorithm results to a CSV file.
     */
    private static void saveToCSV(List<Result> results, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Algorithm,Time(ms),Operations,NegativeCycle\n");

            for (Result r : results) {
                writer.append(r.algorithm).append(",")
                        .append(String.format("%.3f", r.execTimeMs)).append(",")
                        .append(String.valueOf(r.operations)).append(",")
                        .append(String.valueOf(r.negativeCycle)).append("\n");
            }

            System.out.println("✅ CSV file created: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error writing CSV: " + e.getMessage());
        }
    }
}
