package org.example.algorithms;

import org.example.model.*;

import java.util.*;

/**
 * Implementare a descompunerii arborilor folosind tehnica Heavy Path Decomposition.
 * Arborele este impartit in lanturi (liste de noduri) pornind din radacina spre frunza,
 * urmand mereu fiul cel mai "greu" (cu cel mai mare subarbore).
 */
public class TreeDecomposition implements GraphDecomposition<List<List<Node>>> {
    private final Graph graph;
    private final List<List<Node>> heavyPaths = new ArrayList<>();
    private final Map<Node, Integer> subtreeSize = new HashMap<>();
    private final Set<Node> visitedHeavy = new HashSet<>();

    /**
     * Constructor care primeste graful de descompus.
     * @param graph Graful de intrare (arbore)
     */
    public TreeDecomposition(Graph graph) {
        this.graph = graph;
    }

    /**
     * Ruleaza algoritmul de descompunere in lanturi grele.
     * Pentru fiecare componenta conexa a grafului se construieste descompunerea separata.
     */
    @Override
    public void run() {
        Set<Node> visited = new HashSet<>();
        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                computeSubtreeSize(node, null, visited);
                buildHeavyPaths(node, null, new ArrayList<>());
            }
        }
        heavyPaths.sort((a, b) -> b.size() - a.size()); // sortam lanturile descrescator dupa lungime
    }

    /**
     * Calculeaza dimensiunea subarborelui pentru fiecare nod.
     * @param node Nodul curent
     * @param parent Parintele nodului
     * @param visited Setul de noduri deja vizitate
     * @return Dimensiunea subarborelui
     */
    private int computeSubtreeSize(Node node, Node parent, Set<Node> visited) {
        int size = 1;
        visited.add(node);
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent) && !visited.contains(neighbor)) {
                size += computeSubtreeSize(neighbor, node, visited);
            }
        }
        subtreeSize.put(node, size);
        return size;
    }

    /**
     * Construieste lanturile grele pornind de la un nod.
     * @param node Nodul curent
     * @param parent Parintele nodului
     * @param currentPath Lantul curent care se construieste
     */
    private void buildHeavyPaths(Node node, Node parent, List<Node> currentPath) {
        if (visitedHeavy.contains(node)) return;

        currentPath.add(node);
        visitedHeavy.add(node);

        Node heavyChild = null;
        int maxSize = -1;

        // Cauta copilul cel mai "greu" (cu subarborele cel mai mare)
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent)) {
                int size = subtreeSize.getOrDefault(neighbor, 0);
                if (size > maxSize) {
                    maxSize = size;
                    heavyChild = neighbor;
                }
            }
        }

        // Continua pe heavy child
        if (heavyChild != null && !visitedHeavy.contains(heavyChild)) {
            buildHeavyPaths(heavyChild, node, currentPath);
        }

        // Pentru ceilalti copii se incep noi lanturi
        for (Node neighbor : graph.getNeighbors(node)) {
            if (!neighbor.equals(parent) && !neighbor.equals(heavyChild)) {
                List<Node> newPath = new ArrayList<>();
                buildHeavyPaths(neighbor, node, newPath);
                if (!newPath.isEmpty()) {
                    heavyPaths.add(newPath);
                }
            }
        }

        if (!currentPath.isEmpty()) {
            heavyPaths.add(currentPath);
        }
    }

    /**
     * Returneaza o descriere textuala a algoritmului.
     */
    @Override
    public String getDescription() {
        return "Tree Decomposition (Heavy Path Decomposition)";
    }

    /**
     * Returneaza lista de lanturi rezultate (fiecare lant este o lista de noduri).
     */
    @Override
    public List<List<Node>> getResult() {
        return heavyPaths;
    }
}
