package org.example.generator;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * GraphGenerator
 * Generates directed weighted graphs for Assignment 4.
 *
 * Example categories:
 *  - small.json (n=5–10)
 *  - medium.json (n=50–150)
 *  - large.json (n=300–600)
 */
public class DirectedGraphGenerator {

    private static final Random random = new Random();

    public static void main(String[] args) {
        generate("small_graph.json", 5, 10);
        generate("medium_graph.json", 50, 150);
        generate("large_graph.json", 300, 600);
    }

    /**
     * Generates a random directed graph and saves it as JSON.
     */
    private static void generate(String fileName, int minNodes, int maxNodes) {
        int n = randBetween(minNodes, maxNodes);
        boolean directed = true;
        int source = random.nextInt(n);
        List<Map<String, Object>> edges = new ArrayList<>();

        // approximate edge density
        int edgeCount = (int) (n * 1.5);
        Set<String> unique = new HashSet<>();

        while (edges.size() < edgeCount) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);
            if (u == v) continue;
            String key = u + "-" + v;
            if (unique.contains(key)) continue;

            unique.add(key);
            int w = randBetween(1, 20);
            Map<String, Object> e = new HashMap<>();
            e.put("u", u);
            e.put("v", v);
            e.put("w", w);
            edges.add(e);
        }

        Map<String, Object> graph = new LinkedHashMap<>();
        graph.put("directed", directed);
        graph.put("n", n);
        graph.put("edges", edges);
        graph.put("source", source);
        graph.put("weight_model", "edge");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(graph, writer);
            System.out.println("✅ Generated " + fileName + " | Nodes=" + n + " | Edges=" + edges.size());
        } catch (IOException e) {
            System.out.println("❌ Error writing " + fileName + ": " + e.getMessage());
        }
    }

    private static int randBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
