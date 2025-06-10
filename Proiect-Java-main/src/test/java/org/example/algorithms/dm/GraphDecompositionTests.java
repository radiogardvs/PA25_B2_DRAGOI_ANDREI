package org.example.algorithms.dm;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.model.*;
import org.example.algorithms.*;

public class GraphDecompositionTests {

    @Test
    void testTreeDecomposition() {
        Graph graph = new Graph();
        Node a = new Node(0), b = new Node(1), c = new Node(2), d = new Node(3);
        graph.addNode(a); graph.addNode(b); graph.addNode(c); graph.addNode(d);

        graph.addEdge(new Edge(a, b, 1));
        graph.addEdge(new Edge(b, c, 2));
        graph.addEdge(new Edge(c, d, 3));

        TreeDecomposition algo = new TreeDecomposition(graph);
        algo.run();

        List<List<Node>> result = algo.getResult();
        assertNotNull(result);
        assertFalse(result.isEmpty());

        System.out.println("Heavy Paths:");
        result.forEach(System.out::println);
    }

    @Test
    void testCliqueDecomposition() {
        Graph graph = new Graph();
        Node a = new Node(0), b = new Node(1), c = new Node(2);
        graph.addNode(a); graph.addNode(b); graph.addNode(c);

        graph.addEdge(new Edge(a, b, 1));
        graph.addEdge(new Edge(b, c, 1));
        graph.addEdge(new Edge(c, a, 1)); // formăm o clică

        CliqueDecomposition algo = new CliqueDecomposition(graph, 1.0);
        algo.run();

        List<Set<Node>> cliques = algo.getResult();
        assertFalse(cliques.isEmpty());

        System.out.println("Cliques:");
        cliques.forEach(System.out::println);
    }

    @Test
    void testBiconnectedDecomposition() {
        Graph graph = new Graph();
        Node a = new Node(0), b = new Node(1), c = new Node(2), d = new Node(3);
        graph.addNode(a); graph.addNode(b); graph.addNode(c); graph.addNode(d);

        graph.addEdge(new Edge(a, b, 1));
        graph.addEdge(new Edge(b, c, 2));
        graph.addEdge(new Edge(c, a, 3)); // biconex
        graph.addEdge(new Edge(c, d, 4)); // muchie critică

        BiconnectedDecomposition algo = new BiconnectedDecomposition(graph);
        algo.run();

        List<List<Edge>> components = algo.getResult();
        assertFalse(components.isEmpty());

        System.out.println("Biconnected Components:");
        components.forEach(comp -> {
            comp.forEach(e -> System.out.println(e.getFrom() + " --(" + e.getWeight() + ")--> " + e.getTo()));
            System.out.println("---");
        });

        Set<Node> artPoints = algo.getArticulationPoints();
        System.out.println("Articulation Points: " + artPoints);
    }

    @Test
    void testCentroidDecomposition() {
        Graph graph = new Graph();
        Node a = new Node(0), b = new Node(1), c = new Node(2), d = new Node(3);
        graph.addNode(a); graph.addNode(b); graph.addNode(c); graph.addNode(d);

        graph.addEdge(new Edge(a, b, 1));
        graph.addEdge(new Edge(b, c, 2));
        graph.addEdge(new Edge(b, d, 3));
        graph.addEdge(new Edge(a, d, 4));
        CentroidDecomposition algo = new CentroidDecomposition(graph);
        algo.run();

        Map<Node, Node> result = algo.getResult();
        assertFalse(result.isEmpty());

        System.out.println("Centroid Parent Map:");
        result.forEach((child, parent) -> System.out.println("Centroid: " + child + " → Parent: " + parent));
    }

    @Test
    void testMSTDecomposition() {
        Graph graph = new Graph();
        Node a = new Node(0), b = new Node(1), c = new Node(2), d = new Node(3);
        graph.addNode(a); graph.addNode(b); graph.addNode(c); graph.addNode(d);

        graph.addEdge(new Edge(a, b, 3));
        graph.addEdge(new Edge(b, c, 1));
        graph.addEdge(new Edge(c, d, 4));
        graph.addEdge(new Edge(a, d, 2));

        MSTDecomposition algo = new MSTDecomposition(graph);
        algo.run();

        List<List<Edge>> forest = algo.getResult();
        assertFalse(forest.isEmpty());

        System.out.println("Minimum Spanning Forest:");
        forest.forEach(tree -> {
            tree.forEach(e -> System.out.println(e.getFrom() + " --(" + e.getWeight() + ")--> " + e.getTo()));
            System.out.println("---");
        });
    }

    @Test
    void testMSTFromJsonFile() throws Exception {
        Graph graph = GraphSerializer.loadGraph("graphs/mst_exemplu.json");
        MSTDecomposition algo = new MSTDecomposition(graph);
        algo.run();
        List<List<Edge>> forest = algo.getResult();
        assertFalse(forest.isEmpty());

        System.out.println("=== MST din mst_exemplu.json ===");
        for (List<Edge> tree : forest) {
            for (Edge e : tree) {
                System.out.println(e.getFrom() + " --(" + e.getWeight() + ")--> " + e.getTo());
            }
            System.out.println("---");
        }
    }

    @Test
    void testBiconexFromJsonFile() throws Exception {
        Graph graph = GraphSerializer.loadGraph("graphs/graf_biconex.json");
        BiconnectedDecomposition algo = new BiconnectedDecomposition(graph);
        algo.run();

        List<List<Edge>> components = algo.getResult();
        Set<Node> points = algo.getArticulationPoints();
        assertFalse(components.isEmpty());

        System.out.println("=== Componente biconexe ===");
        for (List<Edge> comp : components) {
            for (Edge e : comp) {
                System.out.println(e.getFrom() + " --(" + e.getWeight() + ")--> " + e.getTo());
            }
            System.out.println("---");
        }
        System.out.println("Puncte de articulatie: " + points);
    }

    @Test
    void testCentroidFromJsonFile() throws Exception {
        Graph graph = GraphSerializer.loadGraph("graphs/arbore1.json");
        CentroidDecomposition algo = new CentroidDecomposition(graph);
        algo.run();

        Map<Node, Node> result = algo.getResult();
        assertFalse(result.isEmpty());

        System.out.println("=== Centroid Decomposition din arbore1.json ===");
        result.forEach((child, parent) ->
                System.out.println("Centroid: " + child + " → Parent: " + parent)
        );
    }
}