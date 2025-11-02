package org.example.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Generates 3 JSON graph files:
 * small_graph.json, medium_graph.json, large_graph.json
 * Each file contains 3 directed acyclic connected graphs (DAGs).
 */
public class DirectedGraphGenerator {

    public static void main(String[] args) {
        generateGraph("small_graph.json", 6, 10);
        generateGraph("medium_graph.json", 12, 25);
        generateGraph("large_graph.json", 25, 60);
    }

    private static void generateGraph(String filename, int n, int edges) {
        Random random = new Random();
        Map<String, Object> root = new HashMap<>();
        List<Map<String, Object>> graphs = new ArrayList<>();

        // === создаем 3 графа в одном файле ===
        for (int gIndex = 0; gIndex < 3; gIndex++) {
            Map<String, Object> graph = new HashMap<>();
            graph.put("directed", true);
            graph.put("n", n);

            List<Map<String, Object>> edgeList = new ArrayList<>();
            Set<String> seen = new HashSet<>();

            // === гарантируем связность (цепочка от 0 до n-1) ===
            for (int i = 0; i < n - 1; i++) {
                int u = i;
                int v = i + 1;
                int w = random.nextInt(9) + 1;
                edgeList.add(Map.of("u", u, "v", v, "w", w));
                seen.add(u + "-" + v);
            }

            // === добавляем случайные рёбра, но только вперёд (u < v), чтобы избежать циклов ===
            while (edgeList.size() < edges) {
                int u = random.nextInt(n - 1);
                int v = random.nextInt(n - u - 1) + u + 1; // гарантированно v > u
                String key = u + "-" + v;
                if (seen.contains(key)) continue;
                seen.add(key);
                int w = random.nextInt(9) + 1;
                edgeList.add(Map.of("u", u, "v", v, "w", w));
            }

            graph.put("edges", edgeList);
            graph.put("source", 0);
            graph.put("weight_model", "edge");
            graphs.add(graph);
        }

        // === сохраняем как JSON ===
        root.put("graphs", graphs);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(root, writer);
            System.out.println("✅ Saved 3 DAG graphs → " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
