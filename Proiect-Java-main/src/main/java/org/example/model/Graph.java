package org.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezinta un graf compus dintr-o lista de noduri si o lista de muchii.
 * Clasa este simpla si usor de utilizat pentru aplicatii de tip educational sau algoritmic.
 */
public class Graph {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    /**
     * Adauga un nod in graf.
     * @param node Nodul care va fi adaugat.
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Adauga o muchie in graf.
     * @param edge Muchia care va fi adaugata.
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Returneaza lista tuturor nodurilor.
     * @return Lista de noduri.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Returneaza lista tuturor muchiilor.
     * @return Lista de muchii.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Returneaza vecinii (adiacentele) unui nod dat.
     * @param node Nodul pentru care se cauta vecinii.
     * @return Lista de noduri vecine.
     */
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(node))
                neighbors.add(edge.getTo());
            else if (edge.getTo().equals(node))
                neighbors.add(edge.getFrom());
        }
        return neighbors;
    }

    /**
     * Reprezentare sub forma de text a grafului.
     * @return Nodurile si muchiile in format text.
     */
    @Override
    public String toString() {
        return "Graph{\n nodes=" + nodes + ",\n edges=" + edges + "\n}";
    }
}
