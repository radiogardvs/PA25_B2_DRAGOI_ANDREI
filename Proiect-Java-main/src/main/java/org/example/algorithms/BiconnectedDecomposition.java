package org.example.algorithms;

import org.example.model.*;
import java.util.*;

/**
 * Implementare a algoritmului Tarjan pentru descompunerea unui graf in componente biconexe.
 * Detecteaza si punctele de articulatie (noduri critice care separa graful daca sunt eliminate).
 */
public class BiconnectedDecomposition implements GraphDecomposition<List<List<Edge>>> {

    private final Graph graph;
    private final Map<Node, Integer> disc = new HashMap<>(); // momentul descoperirii unui nod
    private final Map<Node, Integer> low = new HashMap<>();  // cel mai mic timp accesibil dintr-un nod
    private final Set<Node> visited = new HashSet<>();
    private final Stack<Edge> edgeStack = new Stack<>();
    private final List<List<Edge>> biconnectedComponents = new ArrayList<>();
    private final Set<Node> articulationPoints = new HashSet<>();
    private int time = 0;

    /**
     * Constructor care primeste graful de descompus.
     * @param graph Graful de intrare.
     */
    public BiconnectedDecomposition(Graph graph) {
        this.graph = graph;
    }

    /**
     * Ruleaza algoritmul Tarjan pentru toate componentele conexe.
     */
    @Override
    public void run() {
        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfs(node, null);
            }
        }
    }

    /**
     * DFS clasic cu logica pentru puncte de articulatie si componente biconexe.
     * @param u Nodul curent
     * @param parent Parintele nodului in DFS
     */
    private void dfs(Node u, Node parent) {
        visited.add(u);
        disc.put(u, time);
        low.put(u, time);
        time++;

        int children = 0;

        for (Edge edge : graph.getEdges()) {
            Node v = null;
            if (edge.getFrom().equals(u)) v = edge.getTo();
            else if (edge.getTo().equals(u)) v = edge.getFrom();
            else continue;

            if (v.equals(parent)) continue;

            if (!visited.contains(v)) {
                children++;
                edgeStack.push(edge);
                dfs(v, u);

                low.put(u, Math.min(low.get(u), low.get(v)));

                // Daca este punct de articulatie
                if ((parent == null && children > 1) ||
                        (parent != null && low.get(v) >= disc.get(u))) {
                    articulationPoints.add(u);

                    List<Edge> component = new ArrayList<>();
                    Edge e;
                    do {
                        e = edgeStack.pop();
                        component.add(e);
                    } while (!e.equals(edge) && !edgeStack.isEmpty());

                    biconnectedComponents.add(component);
                }

            } else if (disc.get(v) < disc.get(u)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
                edgeStack.push(edge);
            }
        }
    }

    /**
     * Returneaza descrierea algoritmului.
     */
    @Override
    public String getDescription() {
        return "Biconnected Component Decomposition (Tarjan)";
    }

    /**
     * Returneaza lista de componente biconexe.
     * Fiecare componenta este o lista de muchii.
     */
    @Override
    public List<List<Edge>> getResult() {
        return biconnectedComponents;
    }

    /**
     * Returneaza setul de puncte de articulatie identificate.
     * @return Set de noduri critice.
     */
    public Set<Node> getArticulationPoints() {
        return articulationPoints;
    }
}
