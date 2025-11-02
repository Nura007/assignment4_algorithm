package org.example;

import org.example.data.GraphLoader;
import org.example.model.DirectedGraph;
import org.example.algo.dagsp.DAGShortestPath;
import org.example.algo.scc.TarjanSCC;
import org.example.algo.topo.TopologicalSort;
import org.example.algo.Result;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for algorithms: Tarjan SCC, Topological Sort, DAG Shortest Path.
 */
public class GraphTest {

    @Test
    public void testGraphLoading() {
        List<DirectedGraph> graphs = GraphLoader.loadGraphs("small_graph.json");
        assertNotNull(graphs, "Graphs must load from JSON");
        assertFalse(graphs.isEmpty(), "Graphs list should not be empty");
    }

    @Test
    public void testTarjanAlgorithm() {
        DirectedGraph g = new DirectedGraph(4, true, 0);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(2, 3, 1);

        Result res = TarjanSCC.run(g);
        assertEquals("Tarjan SCC", res.algorithm);
        assertTrue(res.operations > 0);
        System.out.println("✅ Tarjan SCC passed | " + res);
    }

    @Test
    public void testTopologicalSort() {
        DirectedGraph g = new DirectedGraph(5, true, 0);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 1);

        Result res = TopologicalSort.run(g);
        assertEquals("Topological Sort", res.algorithm);
        assertTrue(res.operations > 0);
        System.out.println("✅ Topological Sort passed | " + res);
    }

    @Test
    public void testDAGShortestPath() {
        DirectedGraph g = new DirectedGraph(6, true, 0);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 6);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 2);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, -1);
        g.addEdge(4, 5, -2);

        Result res = DAGShortestPath.run(g);
        assertEquals("DAG Shortest Path", res.algorithm);
        assertNotNull(res.distances);
        System.out.println("✅ DAG Shortest Path passed | " + res);
    }
}
