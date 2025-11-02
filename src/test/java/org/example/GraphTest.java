package org.example;

import org.example.generator.DirectedGraphGenerator;
import org.example.data.GraphLoader;
import org.example.model.DirectedGraph;
import org.example.algo.dagsp.DAGShortestPath;
import org.example.algo.scc.TarjanSCC;
import org.example.algo.topo.TopologicalSort;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for algorithms and loader.
 * - Builds a small graph in code and verifies algorithms behave as expected.
 * - Runs the generator and verifies GraphLoader can read the generated file.
 */
public class GraphTest {

    @Test
    public void testAlgorithmsOnManualGraph() {
        // Build a small DAG with 4 nodes (0..3)
        // edges:
        // 0 -> 1 (5)
        // 0 -> 2 (3)
        // 2 -> 1 (2)
        // 1 -> 3 (6)
        // 2 -> 3 (7)
        DirectedGraph g = new DirectedGraph(4);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(2, 1, 2);
        g.addEdge(1, 3, 6);
        g.addEdge(2, 3, 7);
        g.setSource(0);

        // 1) Tarjan SCC: should run without exceptions and do some operations
        TarjanSCC tarjan = new TarjanSCC(g);
        tarjan.run();
        assertTrue(tarjan.getOperationCount() > 0, "Tarjan should perform >0 operations");

        // 2) Topological sort: should return an ordering of size == nodeCount
        TopologicalSort topo = new TopologicalSort(g);
        List<Integer> order = topo.run();
        assertNotNull(order, "Topological order must not be null");
        assertEquals(g.getNodeCount(), order.size(), "Topological order must include all vertices");

        // 3) DAG shortest path: expected distances from 0:
        // dist[0] = 0
        // dist[2] = 3
        // dist[1] = min(5, 3+2) = 5
        // dist[3] = min(5+6=11, 3+7=10) = 10
        DAGShortestPath dagsp = new DAGShortestPath(g);
        int[] dist = dagsp.run();
        assertNotNull(dist, "Distances must not be null");
        assertEquals(4, dist.length, "Distances length must equal node count");
        assertEquals(0, dist[0], "dist[0] == 0");
        assertEquals(3, dist[2], "dist[2] == 3");
        assertEquals(5, dist[1], "dist[1] == 5");
        assertEquals(10, dist[3], "dist[3] == 10");
        assertTrue(dagsp.getOperationCount() >= 0, "DAGSP operation count should be >= 0");
    }

    @Test
    public void testGeneratorAndLoaderProduceAndReadFiles() {
        // Run generator (creates small_graph.json etc. in project root)
        DirectedGraphGenerator.main(new String[]{});

        // Check file exists (small_graph.json should be created)
        File small = new File("small_graph.json");
        assertTrue(small.exists(), "small_graph.json must exist after generator runs");

        // Use GraphLoader to load the just-generated file
        List<DirectedGraph> graphs = GraphLoader.load("small_graph.json");
        assertNotNull(graphs, "GraphLoader.load must not return null");
        assertFalse(graphs.isEmpty(), "GraphLoader.load must return at least one graph");

        // Basic sanity checks on the first loaded graph
        DirectedGraph g = graphs.get(0);
        assertTrue(g.getNodeCount() > 0, "Loaded graph must have >0 nodes");
        assertTrue(g.getAllEdges().size() > 0, "Loaded graph must have >0 edges");

        // Run one algorithm to ensure loaded graph is usable
        TopologicalSort topo = new TopologicalSort(g);
        List<Integer> order = topo.run();
        assertNotNull(order, "Topological sort on loaded graph must not return null");
        // order size can differ if the graph is not a DAG; we only assert callability
    }
}
