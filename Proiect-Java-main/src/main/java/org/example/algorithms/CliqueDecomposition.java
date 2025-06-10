package org.example.algorithms;

import org.example.model.*;
import java.util.*;

/**
 * Implementare a algoritmului Bron–Kerbosch pentru gasirea clicilor maxime dintr-un graf.
 * Clicile sunt submultimi de noduri care formeaza subgrafuri complete.
 * Se foloseste o tehnica probabilistica pentru a reduce numarul de clici generate in grafuri mari.
 */
public class CliqueDecomposition implements GraphDecomposition<List<Set<Node>>> {
    private final Graph graph;
    private final List<Set<Node>> cliques = new ArrayList<>();
    private final Random random = new Random();
    private final double keepProbability;

    /**
     * Constructor care primeste graful si probabilitatea de pastrare a unei clici gasite.
     * @param graph Graful de intrare
     * @param keepProbability Probabilitatea cu care pastram o clica (valoare intre 0 si 1)
     */
    public CliqueDecomposition(Graph graph, double keepProbability) {
        this.graph = graph;
        this.keepProbability = keepProbability;
    }

    /**
     * Executa algoritmul Bron–Kerbosch pornind de la toate nodurile.
     * Se folosesc trei seturi:
     * R - noduri care fac parte din clici curente
     * P - candidati ramasi
     * X - noduri deja procesate
     */
    @Override
    public void run() {
        Set<Node> allNodes = new HashSet<>(graph.getNodes());
        bronKerbosch(new HashSet<>(), allNodes, new HashSet<>());
    }

    /**
     * Functie recursiva care gaseste toate clicile maxime pornind de la o configuratie curenta.
     * @param R Noduri incluse in clici
     * @param P Candidati ramasi
     * @param X Noduri deja explorate
     */
    private void bronKerbosch(Set<Node> R, Set<Node> P, Set<Node> X) {
        if (P.isEmpty() && X.isEmpty()) {
            if (random.nextDouble() < keepProbability) {
                cliques.add(new HashSet<>(R)); // pastram doar o parte dintre clici
            }
            return;
        }

        Set<Node> union = new HashSet<>(P);
        union.addAll(X);
        Node pivot = union.iterator().next();

        Set<Node> candidates = new HashSet<>(P);
        graph.getNeighbors(pivot).forEach(candidates::remove);

        for (Node node : candidates) {
            Set<Node> newR = new HashSet<>(R);
            newR.add(node);

            Set<Node> newP = new HashSet<>(P);
            newP.retainAll(graph.getNeighbors(node));

            Set<Node> newX = new HashSet<>(X);
            newX.retainAll(graph.getNeighbors(node));

            bronKerbosch(newR, newP, newX);

            P.remove(node);
            X.add(node);
        }
    }

    /**
     * Returneaza descrierea algoritmului.
     */
    @Override
    public String getDescription() {
        return "Clique Decomposition (Bron–Kerbosch with random pruning)";
    }

    /**
     * Returneaza lista de clici gasite.
     * Fiecare clică este un set de noduri.
     */
    @Override
    public List<Set<Node>> getResult() {
        return cliques;
    }
}
