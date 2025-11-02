package org.example.runner;

import org.example.algo.Result;
import org.example.algo.dagsp.DAGShortestPath;
import org.example.algo.scc.TarjanSCC;
import org.example.algo.topo.TopologicalSort;
import org.example.data.GraphLoader;
import org.example.model.DirectedGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainRunner {
    public static void main(String[] args) {
        String[] graphFiles = {"small_graph.json", "medium_graph.json", "large_graph.json"};
        List<Result> allResults = new ArrayList<>();

        for (String file : graphFiles) {
            System.out.println("\n✅ Loaded graph: " + file);
            List<DirectedGraph> graphs = GraphLoader.load(file);

            if (graphs == null || graphs.isEmpty()) {
                System.out.println("⚠️ No graphs found in " + file);
                continue;
            }

            for (DirectedGraph g : graphs) {
                System.out.println("\n🚀 Running algorithms on graph: " + file + " (N=" + g.getNodeCount() + ")");

                // === Tarjan SCC ===
                TarjanSCC tarjan = new TarjanSCC(g);
                long start = System.nanoTime();
                tarjan.run();
                long end = System.nanoTime();
                double timeMs = (end - start) / 1_000_000.0;
                Result tarjanResult = new Result("Tarjan SCC", tarjan.getOperationCount(), timeMs);
                allResults.add(tarjanResult);
                System.out.println("✔ Tarjan SCC done: " + tarjanResult);

                // === Topological Sort ===
                TopologicalSort topo = new TopologicalSort(g);
                start = System.nanoTime();
                List<Integer> order = topo.run();
                end = System.nanoTime();
                timeMs = (end - start) / 1_000_000.0;
                Result topoResult = new Result("Topological Sort", topo.getOperationCount(), timeMs);
                allResults.add(topoResult);
                System.out.println("✔ Topological Sort done: " + order);

                // === DAG Shortest Path ===
                DAGShortestPath dag = new DAGShortestPath(g);
                start = System.nanoTime();
                int[] distances = dag.run();
                end = System.nanoTime();
                timeMs = (end - start) / 1_000_000.0;
                Result dagResult = new Result("DAG Shortest Path", distances, dag.getOperationCount(), timeMs);
                allResults.add(dagResult);
                System.out.println("✔ DAG Shortest Path done: " + java.util.Arrays.toString(distances));
            }
        }

        saveResultsToCSV(allResults);
        System.out.println("\n📊 Results saved to results.csv");
    }

    private static void saveResultsToCSV(List<Result> results) {
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("Algorithm,Operations,Time(ms),NegativeCycle,Distances\n");
            for (Result r : results) {
                writer.write(String.format("%s,%d,%.3f,%b,%s\n",
                        r.algorithm,
                        r.operations,
                        r.execTimeMs,
                        r.negativeCycle,
                        (r.distances != null ? java.util.Arrays.toString(r.distances) : "N/A")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
