package org.example.algorithms.dm;

import java.util.*;

/**
 * Reprezinta un graf bipartit, unde nodurile sunt impartite in doua multimi disjuncte X si Y,
 * iar muchiile se pot forma doar intre noduri din multimi diferite.
 */
public class BipartiteGraph {
    private final Set<String> X = new HashSet<>();
    private final Set<String> Y = new HashSet<>();
    private final Map<String, Set<String>> adj = new HashMap<>();

    /**
     * Adauga o muchie intre un nod din X si un nod din Y.
     * Daca nodurile nu exista, sunt adaugate automat.
     *
     * @param xNode nodul din partea X
     * @param yNode nodul din partea Y
     */
    public void addEdge(String xNode, String yNode) {
        X.add(xNode);
        Y.add(yNode);

        adj.computeIfAbsent(xNode, __ -> new HashSet<>()).add(yNode);
        adj.computeIfAbsent(yNode, __ -> new HashSet<>()).add(xNode);
    }

    /**
     * Returneaza multimea de noduri din partea X.
     *
     * @return set de stringuri
     */
    public Set<String> getX() {
        return X;
    }

    /**
     * Returneaza multimea de noduri din partea Y.
     *
     * @return set de stringuri
     */
    public Set<String> getY() {
        return Y;
    }

    /**
     * Returneaza vecinii unui nod (nodurile adiacente).
     *
     * @param node nodul pentru care se cer vecinii
     * @return set de stringuri vecine sau set gol daca nu exista
     */
    public Set<String> getNeighbors(String node) {
        return adj.getOrDefault(node, Collections.emptySet());
    }

    /**
     * Returneaza reprezentarea textuala a grafului bipartit.
     */
    @Override
    public String toString() {
        return "X=" + X + ", Y=" + Y + ", Edges=" + adj;
    }
}
