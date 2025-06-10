package org.example.algorithms.dm;

import java.util.*;

/**
 * Implementeaza decompozitia Dulmage-Mendelsohn (DM) pentru un graf bipartit cu un matching dat.
 *
 * Decompozitia DM separa nodurile grafului in trei componente disjuncte:
 * - Componenta A: noduri atinse din noduri libere din X pe graful orientat special (drumuri alternante)
 * - Componenta B: noduri imposibil de atins si neimplicate in matching
 * - Componenta C: noduri care formeaza matching complet si nu sunt in A
 */
public class DMDecomposer {
    private final BipartiteGraph graph;
    private final Map<String, String> matching;

    private final Set<String> componentA = new HashSet<>();
    private final Set<String> componentB = new HashSet<>();
    private final Set<String> componentC = new HashSet<>();

    /**
     * Constructorul primeste un graf bipartit si un matching maxim.
     * Aplica decompozitia automat la creare.
     *
     * @param graph graful bipartit
     * @param matching matching-ul maxim (u -> v)
     */
    public DMDecomposer(BipartiteGraph graph, Map<String, String> matching) {
        this.graph = graph;
        this.matching = matching;
        decompose();
    }

    /**
     * Aplica algoritmul de decompozitie DM.
     * Creeaza un graf orientat special si parcurge BFS din nodurile libere din X.
     * Clasifica nodurile in componente A, B si C.
     */
    private void decompose() {
        Map<String, List<String>> orientedGraph = new HashMap<>();

        for (String u : graph.getX()) {
            orientedGraph.putIfAbsent(u, new ArrayList<>());
            for (String v : graph.getNeighbors(u)) {
                orientedGraph.putIfAbsent(v, new ArrayList<>());

                if (matching.get(u) != null && matching.get(u).equals(v)) {
                    // muchie in matching: v -> u (inversa)
                    orientedGraph.get(v).add(u);
                } else {
                    // muchie normala: u -> v
                    orientedGraph.get(u).add(v);
                }
            }
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // BFS din nodurile libere din X
        for (String u : graph.getX()) {
            if (matching.get(u) == null) {
                queue.add(u);
                visited.add(u);
                componentA.add(u);
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : orientedGraph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    componentA.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Clasificare: B si C
        for (String u : graph.getX()) {
            if (!componentA.contains(u)) {
                if (matching.containsKey(u)) {
                    componentC.add(u);
                    componentC.add(matching.get(u));
                } else {
                    componentB.add(u);
                }
            }
        }

        for (String v : graph.getY()) {
            if (!componentA.contains(v) && !componentC.contains(v)) {
                if (!matching.containsValue(v)) {
                    componentB.add(v);
                }
            }
        }
    }

    /**
     * Returneaza componenta A (noduri accesibile din noduri libere prin drumuri alternante).
     */
    public Set<String> getComponentA() {
        return componentA;
    }

    /**
     * Returneaza componenta B (noduri necuplate si neaccesibile).
     */
    public Set<String> getComponentB() {
        return componentB;
    }

    /**
     * Returneaza componenta C (noduri care formeaza un matching complet).
     */
    public Set<String> getComponentC() {
        return componentC;
    }
}
