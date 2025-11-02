package org.example.data;

import com.google.gson.*;
import org.example.model.DirectedGraph;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * GraphLoader
 * Reads a JSON file and converts it into a DirectedGraph object.
 */
public class GraphLoader {

    public static DirectedGraph loadGraph(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();

            boolean directed = obj.get("directed").getAsBoolean();
            int n = obj.get("n").getAsInt();
            int source = obj.get("source").getAsInt();

            DirectedGraph graph = new DirectedGraph(n, directed, source);

            JsonArray edgesArray = obj.getAsJsonArray("edges");
            for (JsonElement e : edgesArray) {
                JsonObject edge = e.getAsJsonObject();
                int u = edge.get("u").getAsInt();
                int v = edge.get("v").getAsInt();
                int w = edge.get("w").getAsInt();
                graph.addEdge(u, v, w);
            }

            System.out.println("✅ Graph loaded successfully from " + filePath);
            System.out.println("Nodes: " + n + ", Edges: " + edgesArray.size());
            return graph;

        } catch (IOException e) {
            System.out.println("❌ Error loading graph: " + e.getMessage());
            return null;
        }
    }

    public static List<DirectedGraph> loadGraphs(String inputFile) {
        return List.of();
    }
}
