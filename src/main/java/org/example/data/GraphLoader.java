package org.example.data;

import com.google.gson.Gson;
import org.example.generator.DirectedGraphGenerator;
import org.example.model.DirectedGraph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * GraphLoader
 * Loads directed graphs from JSON files.
 * If a file does not exist, it automatically generates it.
 */
public class GraphLoader {

    public static List<DirectedGraph> load(String fileName) {
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            file = new File(fileName);
        }

        // 🔥 если файла нет — генерируем заново
        if (!file.exists()) {
            System.out.println("⚠️ File not found → Generating: " + fileName);
            if (fileName.contains("small")) {
                DirectedGraphGenerator.main(new String[]{});
            } else if (fileName.contains("medium")) {
                DirectedGraphGenerator.main(new String[]{});
            } else if (fileName.contains("large")) {
                DirectedGraphGenerator.main(new String[]{});
            }
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            InputData input = gson.fromJson(reader, InputData.class);

            if (input == null || input.graphs == null) {
                System.out.println("⚠️ No graphs found in " + file.getPath());
                return new ArrayList<>();
            }

            List<DirectedGraph> result = new ArrayList<>();
            for (GraphJson json : input.graphs) {
                DirectedGraph g = new DirectedGraph(json.n);
                g.setSource(json.source);
                for (EdgeJson e : json.edges) {
                    g.addEdge(e.u, e.v, e.w);
                }
                result.add(g);
            }

            System.out.println("✅ Loaded " + result.size() + " graphs from " + file.getPath());
            return result;

        } catch (IOException e) {
            System.out.println("❌ Error reading " + fileName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // === внутренние структуры для JSON ===
    private static class InputData {
        List<GraphJson> graphs;
    }

    private static class GraphJson {
        boolean directed;
        int n;
        int source;
        List<EdgeJson> edges;
    }

    private static class EdgeJson {
        int u;
        int v;
        int w;
    }
}
