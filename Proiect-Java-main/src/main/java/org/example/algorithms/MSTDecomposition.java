package org.example.algorithms;

import org.example.model.*;
import java.util.*;

/**
 * Implementare a descompunerii grafului in arbori de acoperire de cost minim (MST-uri)
 * folosind algoritmul Kruskal.
 * Pentru grafuri neconexe se obtine o padure (lista de arbori disjuncti).
 */
public class MSTDecomposition implements GraphDecomposition<List<List<Edge>>> {
    private final Graph graph;
    private final List<List<Edge>> forest = new ArrayList<>();

    /**
     * Constructor care primeste graful initial.
     * @param graph Graful de descompus.
     */
    public MSTDecomposition(Graph graph) {
        this.graph = graph;
    }

    /**
     * Ruleaza algoritmul Kruskal pentru a obtine MST-ul (sau padurea MST).
     * Foloseste un set disjunct pentru detectia ciclurilor.
     */
    @Override
    public void run() {
        List<Edge> allEdges = new ArrayList<>(graph.getEdges());
        allEdges.sort(Comparator.comparingInt(Edge::getWeight)); // sortare crescatoare

        DisjointSet<Node> ds = new DisjointSet<>(graph.getNodes());
        Map<Node, List<Edge>> treeMap = new HashMap<>();

        for (Edge edge : allEdges) {
            Node u = edge.getFrom();
            Node v = edge.getTo();
            if (ds.union(u, v)) {
                Node root = ds.find(u);
                treeMap.putIfAbsent(root, new ArrayList<>());
                treeMap.get(root).add(edge);
            }
        }

        forest.clear();
        forest.addAll(treeMap.values());
    }

    /**
     * Returneaza descrierea algoritmului.
     */
    @Override
    public String getDescription() {
        return "Minimum Spanning Tree Decomposition (Kruskal)";
    }

    /**
     * Returneaza lista de arbori de acoperire obtinuti.
     * Fiecare arbore este reprezentat ca o lista de muchii.
     */
    @Override
    public List<List<Edge>> getResult() {
        return forest;
    }

    /**
     * Clasa interna pentru implementarea unui sistem de multimi disjuncte (union-find).
     * Permite unirea a doua noduri si aflarea radacinii cu compresie de cale.
     */
    private static class DisjointSet<T> {
        private final Map<T, T> parent = new HashMap<>();

        /**
         * Initializeaza fiecare element ca fiind propriul sau parinte.
         */
        public DisjointSet(Collection<T> items) {
            for (T item : items) {
                parent.put(item, item);
            }
        }

        /**
         * Gaseste reprezentantul (radacina) al unui element.
         * Aplica compresie de cale.
         */
        public T find(T x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        /**
         * Uneste doua multimi daca au radacini diferite.
         * @return true daca s-a realizat unirea; false daca deja erau in aceeasi multime
         */
        public boolean union(T x, T y) {
            T rootX = find(x);
            T rootY = find(y);
            if (rootX.equals(rootY)) return false;
            parent.put(rootX, rootY);
            return true;
        }
    }
}
