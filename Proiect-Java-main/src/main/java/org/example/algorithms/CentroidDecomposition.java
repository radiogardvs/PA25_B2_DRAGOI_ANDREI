package org.example.algorithms;

import org.example.model.*;
import java.util.*;

/**
 * Implementare a algoritmului de Centroid Decomposition.
 * Se aplica doar pe arbori (grafuri neorientate fara cicluri).
 * Returneaza o harta nod → parinte in arborele de centroizi construit recursiv.
 */
public class CentroidDecomposition implements GraphDecomposition<Map<Node, Node>> {
    private final Graph graph;
    private final Set<Node> removed = new HashSet<>();
    private final Map<Node, Integer> subtreeSize = new HashMap<>();
    private final Map<Node, Node> centroidParent = new HashMap<>();

    /**
     * Constructorul primeste graful de intrare.
     * @param graph Graful care trebuie descompus
     */
    public CentroidDecomposition(Graph graph) {
        this.graph = graph;
    }

    /**
     * Ruleaza decompozitia in centroidi.
     * Daca graful nu este un arbore valid (conex, fara cicluri), arunca exceptie.
     */
    @Override
    public void run() {
        if (!graph.getNodes().isEmpty()) {
            if (hasCycle()) {
                throw new IllegalArgumentException("Centroid Decomposition se aplica doar pe arbori (grafuri fara cicluri)!");
            }
            decompose(graph.getNodes().get(0), null);
        }
    }

    /**
     * Decompune recursiv arborele in functie de centroidul fiecarei componente.
     * @param node Nodul de start
     * @param parent Parintele centroidului in arborele rezultat
     */
    private void decompose(Node node, Node parent) {
        subtreeSize.clear();
        Set<Node> visited = new HashSet<>();
        int size = computeSubtreeSize(node, null, visited);
        Node centroid = findCentroid(node, null, size, new HashSet<>());
        centroidParent.put(centroid, parent);
        removed.add(centroid);

        for (Node neighbor : graph.getNeighbors(centroid)) {
            if (!removed.contains(neighbor)) {
                decompose(neighbor, centroid);
            }
        }
    }

    /**
     * Calculeaza dimensiunile subarborilor pentru fiecare nod (DFS).
     */
    private int computeSubtreeSize(Node node, Node parent, Set<Node> visited) {
        visited.add(node);
        int size = 1;
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent) && !removed.contains(neighbor) && !visited.contains(neighbor)) {
                size += computeSubtreeSize(neighbor, node, visited);
            }
        }
        subtreeSize.put(node, size);
        return size;
    }

    /**
     * Gaseste centroidul subarborelui curent.
     * Centroid = nodul care, daca este eliminat, toate componentele rezultate sunt de dimensiune <= total / 2
     */
    private Node findCentroid(Node node, Node parent, int totalSize, Set<Node> visited) {
        visited.add(node);
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent) && !removed.contains(neighbor) && !visited.contains(neighbor)) {
                int size = subtreeSize.getOrDefault(neighbor, 0);
                if (size > totalSize / 2) {
                    return findCentroid(neighbor, node, totalSize, visited);
                }
            }
        }
        return node;
    }

    /**
     * Verifica daca graful contine cicluri sau nu este conex.
     * Centroid Decomposition necesita un arbore valid: fara cicluri si cu n-1 muchii.
     */
    private boolean hasCycle() {
        Set<Node> visited = new HashSet<>();
        boolean cycleDetected = false;
        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                if (hasCycleDFS(node, null, visited)) {
                    cycleDetected = true;
                    break;
                }
            }
        }

        int edgeCount = graph.getEdges().size() / 2;
        int nodeCount = graph.getNodes().size();
        boolean isConnected = visited.size() == nodeCount;

        return cycleDetected || !isConnected || edgeCount != nodeCount - 1;
    }

    /**
     * Detecteaza cicluri in graf folosind DFS clasic.
     */
    private boolean hasCycleDFS(Node node, Node parent, Set<Node> visited) {
        visited.add(node);
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent)) {
                if (visited.contains(neighbor) || hasCycleDFS(neighbor, node, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returneaza descrierea algoritmului.
     */
    @Override
    public String getDescription() {
        return "Centroid Decomposition";
    }

    /**
     * Returneaza arborele de centroidi sub forma de map nod → parinte.
     */
    @Override
    public Map<Node, Node> getResult() {
        return centroidParent;
    }
}
