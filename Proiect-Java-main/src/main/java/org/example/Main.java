package org.example;

import org.example.model.*;
import org.example.algorithms.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Clasa Main este punctul de intrare al aplicatiei.
 * Aceasta genereaza doua grafuri: unul de tip arbore si unul random.
 * Apoi ruleaza in paralel (concurent) 4 algoritmi principali:
 * - Tree Decomposition
 * - Clique Decomposition
 * - Biconnected Components Decomposition
 * - MST Decomposition + Centroid Decomposition aplicat pe primul MST
 *
 * Fiecare algoritm ruleaza intr-un task propriu folosind un ExecutorService cu 6 thread-uri.
 * Rezultatele sunt afisate in consola, sincronizat pentru a evita output amestecat.
 */


public class Main {
    public static void main(String[] args) {
        // initializare grafuri
        // graphTreeBased - folosit pentru centroid decomposition
        // graphRandom - folosit pentru restul decompozitiilor
        // se genereaza cu noduri si muchii random

        // se construieste un executor cu 6 threaduri
        // fiecare algoritm ruleaza intr-un thread separat
        // folosim Callable<Void> pentru ca taskurile nu returneaza valoare
        // doar afiseaza rezultat

        // task TreeDecomposition:
        // - ruleaza pe graful random
        // - returneaza lista de lanturi grele (heavy paths)
        // - afiseaza maxim 20 de lanturi

        // task CliqueDecomposition:
        // - ruleaza pe graful random
        // - foloseste un prag (threshold) de 0.5
        // - afiseaza primele 20 de clique-uri

        // task BiconnectedDecomposition:
        // - identifica componentele biconexe si punctele de articulatie
        // - ruleaza DFS si detecteaza back edges
        // - afiseaza primele 20 de componente si punctele de articulatie

        // task MST + Centroid:
        // - ruleaza Kruskal pentru a obtine forest (padure) de arbori MST
        // - extrage primul MST si il converteste intr-un nou graf
        // - aplica CentroidDecomposition pe acel graf
        // - afiseaza radacinile centroid si parintii fiecarui nod

        // in final, toate taskurile sunt rulate cu invokeAll
        // iar executorul este inchis cu shutdown
        List<Node> nodes = new ArrayList<>();
        Graph graphTreeBased = new Graph();
        Graph graphRandom = new Graph();
        Random random = new Random();

        int nodeCount = 100;
        double edgeProbability = 0.05;

        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node(i);
            graphTreeBased.addNode(node);
            nodes.add(node);
        }
        for (int i = 1; i < nodeCount; i++) {
            int parent = random.nextInt(i);
            int weight = random.nextInt(10) + 1;
            graphTreeBased.addEdge(new Edge(nodes.get(i), nodes.get(parent), weight));
            graphTreeBased.addEdge(new Edge(nodes.get(parent), nodes.get(i), weight));
        }

        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node(i);
            graphRandom.addNode(node);
        }
        for (int i = 0; i < nodeCount; i++) {
            for (int j = i + 1; j < nodeCount; j++) {
                if (random.nextDouble() < edgeProbability) {
                    Node u = graphRandom.getNodes().get(i);
                    Node v = graphRandom.getNodes().get(j);
                    int weight = random.nextInt(100) + 1;
                    graphRandom.addEdge(new Edge(u, v, weight));
                    graphRandom.addEdge(new Edge(v, u, weight));
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(6);

        List<Callable<Void>> tasks = List.of(
                () -> {
                    // builder pentru output text
                    StringBuilder sb = new StringBuilder();
                    sb.append("[TreeDecomposition] Start\n");
                    // creare si rulare algoritm TreeDecomposition pe graful random
                    TreeDecomposition algo = new TreeDecomposition(graphRandom);
                    algo.run();
                    // preluare rezultat lista de lanturi grele
                    List<List<Node>> result = algo.getResult();
                    sb.append("[TreeDecomposition] Finished\n");
                    // afisare numar lanturi
                    sb.append("Numar lanturi grele: ").append(result.size()).append("\n");
                    // afisare primele 20 de lanturi sau mai putine daca sunt mai putine
                    for (int i = 0; i < Math.min(20, result.size()); i++) {
                        sb.append("Lant #").append(i + 1).append(": ").append(result.get(i)).append("\n");
                    }
                    // sincronizare scriere in consola pt a evita amestecarea outputului
                    synchronized (System.out) {
                        System.out.print(sb.toString());
                    }
                    return null;
                },
                () -> {
                    // builder pentru output text
                    StringBuilder sb = new StringBuilder();
                    sb.append("[CliqueDecomposition] Start\n");
                    // creare si rulare algoritm CliqueDecomposition pe graful random cu prag 0.5
                    CliqueDecomposition algo = new CliqueDecomposition(graphRandom, 0.5);
                    algo.run();
                    // preluare rezultat lista de clique-uri
                    List<Set<Node>> result = algo.getResult();
                    sb.append("[CliqueDecomposition] Finished\n");
                    // afisare numar clique-uri
                    sb.append("Numar clique-uri: ").append(result.size()).append("\n");
                    // afisare primele 20 de clique-uri sau mai putine daca sunt mai putine
                    for (int i = 0; i < Math.min(20, result.size()); i++) {
                        sb.append("Clique #").append(i + 1).append(": ").append(result.get(i)).append("\n");
                    }
                    // sincronizare scriere in consola pt a evita amestecarea outputului
                    synchronized (System.out) {
                        System.out.print(sb.toString());
                    }
                    return null;
                },
                () -> {
                    // builder pentru output text
                    StringBuilder sb = new StringBuilder();
                    sb.append("[BiconnectedDecomposition] Start\n");
                    // creare si rulare algoritm BiconnectedDecomposition pe graful random
                    BiconnectedDecomposition biconnAlgo = new BiconnectedDecomposition(graphRandom);
                    biconnAlgo.run();
                    // preluare rezultat lista componente biconexe
                    List<List<Edge>> bicomponents = biconnAlgo.getResult();
                    sb.append("[BiconnectedDecomposition] Finished\n");
                    // afisare numar componente biconexe
                    sb.append("Numar componente biconexe: ").append(bicomponents.size()).append("\n");
                    // afisare primele 20 componente biconexe
                    for (int i = 0; i < Math.min(20, bicomponents.size()); i++) {
                        sb.append("Componenta #").append(i + 1).append(": ").append(bicomponents.get(i)).append("\n");
                    }
                    // afisare puncte de articulatie
                    sb.append("Puncte de articulatie: ").append(biconnAlgo.getArticulationPoints()).append("\n");
                    // sincronizare scriere in consola pt a evita amestecarea outputului
                    synchronized (System.out) {
                        System.out.print(sb.toString());
                    }
                    return null;
                },
                () -> {
                    // builder pentru output text
                    StringBuilder sb = new StringBuilder();

                    sb.append("[MSTDecomposition] Start\n");
                    // creare si rulare algoritm MSTDecomposition pe graful random
                    MSTDecomposition mstAlgo = new MSTDecomposition(graphTreeBased);
                    mstAlgo.run();
                    // preluare rezultat lista de arbori MST (pădure)
                    List<List<Edge>> forest = mstAlgo.getResult();
                    sb.append("[MSTDecomposition] Finished\n");

                    // afisare fiecare arbore MST si muchiile lui
                    int idx = 1;
                    for (List<Edge> tree : forest) {
                        sb.append("\nArbore MST #").append(idx++).append(" cu ").append(tree.size()).append(" muchii:\n");
                        for (Edge e : tree) {
                            sb.append(e.getFrom()).append(" --(").append(e.getWeight()).append(")--> ").append(e.getTo()).append("\n");
                        }
                    }

                    // daca exista cel putin un arbore MST se construieste graful pentru centroid decomposition
                    if (!forest.isEmpty()) {
                        // luam primul arbore MST din lista
                        List<Edge> firstMST = forest.get(0);
                        Graph mstGraph = new Graph();

                        // adaugam nodurile din graful random in noul graf MST
                        Set<Node> nodesInMST = new HashSet<>();
                        for (Edge edge : firstMST) {
                            nodesInMST.add(edge.getFrom());
                            nodesInMST.add(edge.getTo());
                        }
                        for (Node node : nodesInMST) {
                            mstGraph.addNode(node);
                        }

                        // adaugam muchiile din primul arbore MST bidirectional
                        for (Edge edge : firstMST) {
                            mstGraph.addEdge(edge);
                            ///mstGraph.addEdge(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
                        }
                        sb.append("\n[CentroidDecomposition pe MST-ul #1] Start\n");
                        CentroidDecomposition centroidAlgo = new CentroidDecomposition(mstGraph);


                        try {
                            // rulam centroid decomposition
                            centroidAlgo.run();
                            // preluam rezultatul sub forma de mapa nod->parinte centroid
                            Map<Node, Node> centroidParent = centroidAlgo.getResult();

                            sb.append("[CentroidDecomposition] Finished\n");
                            // colectam radacinile centroid (nodurile fara parinte)
                            Set<Node> centroidRoots = new HashSet<>();
                            for (Node node : centroidParent.keySet()) {
                                if (centroidParent.get(node) == null) {
                                    centroidRoots.add(node);
                                }
                            }
                            // afisam radacinile centroid
                            sb.append("Radacini centroid: ").append(centroidRoots).append("\n");
                            sb.append("Parinti centroidali per nod:\n");
                            // afisam parintii centroid per nod
                            for (Map.Entry<Node, Node> entry : centroidParent.entrySet()) {
                                sb.append("Centroid ").append(entry.getKey()).append(" → Parinte: ").append(entry.getValue()).append("\n");
                            }
                        } catch (IllegalArgumentException ex) {
                            // daca apare exceptie afisam mesajul de eroare
                            sb.append("[CentroidDecomposition] Eroare: ").append(ex.getMessage()).append("\n");
                        }
                    } else {
                        sb.append("MST-ul este gol, nu se poate aplica Centroid Decomposition.\n");
                    }

                    // sincronizam scrierea in consola
                    synchronized (System.out) {
                        System.out.print(sb.toString());
                    }

                    return null;
                }
        );

        try {
            // rulam toate taskurile concurent si asteptam sa termine toate
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            // afisam eroarea daca executia e intrerupta
            e.printStackTrace();
        } finally {
            // inchidem executorul
            executor.shutdown();
        }

        /// Test sa verific daca se salveaza cliqurile + nodurile incarcate
        System.out.println("\nSalvare clique-uri in Oracle");

        CliqueDecomposition algo = new CliqueDecomposition(graphRandom, 0.5);
        algo.run();
        List<Set<Node>> result = algo.getResult();

        List<List<Node>> cliques = new ArrayList<>();
        for (Set<Node> set : result) {
            cliques.add(new ArrayList<>(set));
        }

        GraphDAO dao = new GraphDAO();
        String descriere = dao.formatCliques(cliques);
        dao.saveGraph(graphRandom, "Clique", descriere);

        System.out.println("Clique-urile au fost salvate in Oracle");

        Graph grafSalvat = dao.loadGraph();
        System.out.println("Noduri incarcate: " + grafSalvat.getNodes().size());
    }
}

