package generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * DirectedGraphGenerator
 * Generates random directed weighted graphs (for Assignment 4)
 */
public class DirectedGraphGenerator {

    private static final Random random = new Random();

    public static void main(String[] args) {
        generateCategory("small", 6, 10, 3);
        generateCategory("medium", 10, 20, 3);
        generateCategory("large", 20, 50, 3);
    }

    private static void generateCategory(String category, int minNodes, int maxNodes, int count) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (int i = 1; i <= count; i++) {
            int n = randBetween(minNodes, maxNodes);
            int m = randBetween(n + 2, n * 2);

            List<Edge> edges = new ArrayList<>();
            Set<String> used = new HashSet<>();

            // --- Directed edges
            while (edges.size() < m) {
                int u = random.nextInt(n);
                int v = random.nextInt(n);
                if (u == v) continue;
                String key = u + "," + v;
                if (used.contains(key)) continue;
                used.add(key);
                edges.add(new Edge(u, v, randBetween(1, 10)));
            }

            // --- Add a few reverse edges (to form SCCs)
            for (int k = 0; k < Math.max(1, n / 5); k++) {
                int a = random.nextInt(n);
                int b = random.nextInt(n);
                if (a != b) edges.add(new Edge(b, a, randBetween(1, 10)));
            }

            Map<String, Object> graphData = new LinkedHashMap<>();
            graphData.put("directed", true);
            graphData.put("n", n);
            graphData.put("edges", edges);
            graphData.put("source", random.nextInt(n));
            graphData.put("weight_model", "edge");

            String fileName = "input_" + category + "_" + i + ".json";
            try (FileWriter writer = new FileWriter(fileName)) {
                gson.toJson(graphData, writer);
                System.out.println("✅ Saved: " + fileName + " (nodes=" + n + ", edges=" + edges.size() + ")");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int randBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    static class Edge {
        int u;
        int v;
        int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}
