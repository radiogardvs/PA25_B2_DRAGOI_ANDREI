package org.example.algorithms.dm;

import java.util.*;

/**
 * Implementeaza algoritmul Hopcroft-Karp pentru gasirea unui matching maxim
 * intr-un graf bipartit.
 *
 * Algoritmul foloseste drumuri augmentante si realizeaza mai multe faze de BFS + DFS
 * pentru a extinde simultan mai multe perechi, fiind mult mai eficient decat metodele naive.
 */
public class HopcroftKarpMatcher {
    private final BipartiteGraph graph;
    private final Map<String, String> pairU = new HashMap<>();
    private final Map<String, String> pairV = new HashMap<>();
    private final Map<String, Integer> dist = new HashMap<>();

    /**
     * Constructor care primeste graful bipartit.
     * @param graph graful bipartit
     */
    public HopcroftKarpMatcher(BipartiteGraph graph) {
        this.graph = graph;
    }

    /**
     * Calculeaza matching-ul maxim in graful bipartit.
     * Foloseste mai multe runde de BFS si DFS pentru a gasi toate drumurile augmentante.
     * @return un map unde fiecare nod din X este cuplat cu un nod din Y
     */
    public Map<String, String> maximumMatching() {
        for (String u : graph.getX()) {
            pairU.put(u, null);
        }
        for (String v : graph.getY()) {
            pairV.put(v, null);
        }

        while (bfs()) {
            for (String u : graph.getX()) {
                if (pairU.get(u) == null) {
                    dfs(u);
                }
            }
        }

        Map<String, String> matching = new HashMap<>();
        for (String u : graph.getX()) {
            String v = pairU.get(u);
            if (v != null) {
                matching.put(u, v);
            }
        }
        return matching;
    }

    /**
     * BFS construieste nivelele (straturile) in functie de distanta de la nodurile libere.
     * Ajuta la identificarea drumurilor augmentante scurte.
     * @return true daca exista cel putin un drum augmentant
     */
    private boolean bfs() {
        Queue<String> queue = new LinkedList<>();
        for (String u : graph.getX()) {
            if (pairU.get(u) == null) {
                dist.put(u, 0);
                queue.add(u);
            } else {
                dist.put(u, Integer.MAX_VALUE);
            }
        }

        boolean found = false;
        while (!queue.isEmpty()) {
            String u = queue.poll();
            for (String v : graph.getNeighbors(u)) {
                String u2 = pairV.get(v);
                if (u2 != null && dist.getOrDefault(u2, Integer.MAX_VALUE) == Integer.MAX_VALUE) {
                    dist.put(u2, dist.get(u) + 1);
                    queue.add(u2);
                }
                if (u2 == null) {
                    found = true;
                }
            }
        }

        return found;
    }

    /**
     * DFS construieste si aplica drumurile augmentante gasite cu BFS.
     * @param u nod curent din multimea X
     * @return true daca s-a gasit si aplicat un drum augmentant
     */
    private boolean dfs(String u) {
        for (String v : graph.getNeighbors(u)) {
            String u2 = pairV.get(v);
            if (u2 == null || (dist.getOrDefault(u2, Integer.MAX_VALUE) == dist.get(u) + 1 && dfs(u2))) {
                pairU.put(u, v);
                pairV.put(v, u);
                return true;
            }
        }
        dist.put(u, Integer.MAX_VALUE);
        return false;
    }
}
