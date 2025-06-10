package org.example.visualizer;

import org.example.model.*;
import org.example.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;


import java.util.Set;

import java.util.Comparator;

/**
 * Clasa GraphDecompositionUI este responsabila cu interfata grafica Swing
 * pentru vizualizarea decompozitiilor aplicate pe grafuri.
 * Ofera functionalitati pentru:
 * - Selectarea tipului de decompozitie (MST, clique-uri, componente biconexe, etc.)
 * - Generarea unui graf aleator
 * - Generarea unui arbore valid pentru decompozitie pe centroidi
 * - Aplicarea decompozitiei centroidale pe cel mai mare MST dintr-un graf aleator

 * Componente principale:
 * - JFrame cu titlul "Graph Decomposition Viewer"
 * - JComboBox pentru alegerea decompozitiei
 * - JPanel pentru butoane de generare si actiuni
 * - GraphCanvas pentru afisarea grafica a rezultatului
 */
public class GraphDecompositionUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Decomposition Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            GraphCanvas canvas = new GraphCanvas();
            frame.add(canvas, BorderLayout.CENTER);

            // Selector decompozitie
            JComboBox<String> decompositionSelector = new JComboBox<>(new String[] {
                    "Minimum Spanning Tree",
                    "Clique Decomposition",
                    "Biconnected Components",
                    "Centroid Decomposition",
                    "Tree Decomposition"
            });

            // Cand utilizatorul alege o decompozitie, o aplicam
            decompositionSelector.addActionListener(e -> {
                String selected = (String) decompositionSelector.getSelectedItem();
                try {
                    canvas.setDecompositionType(selected);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Eroare decompozitie",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            frame.add(decompositionSelector, BorderLayout.NORTH);

            JPanel bottomPanel = new JPanel(new FlowLayout());

            // Buton pentru generare arbore valid (folosit in centroid decomposition)
            JButton generateTreeButton = new JButton("Genereaza arbore valid");
            generateTreeButton.addActionListener(e -> {
                Graph tree = generateRandomTree(15);
                canvas.setGraph(tree);
                decompositionSelector.setSelectedItem("Centroid Decomposition");
                try {
                    canvas.setDecompositionType("Centroid Decomposition");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Eroare decompozitie",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Buton pentru generare graf aleator conectat
            JButton generateGraphButton = new JButton("Genereaza graf aleator");
            generateGraphButton.addActionListener(e -> {
                Graph graph = generateRandomGraph(30, 0.7);
                canvas.setGraph(graph);
                try {
                    canvas.setDecompositionType("Minimum Spanning Tree");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Eroare decompozitie",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            bottomPanel.add(generateTreeButton);
            bottomPanel.add(generateGraphButton);

            // Buton pentru aplicare Centroid pe MST-ul cel mai mare dintr-un graf mare
            JButton centroidOnMSTButton = new JButton("Centroid pe MST mare");
            centroidOnMSTButton.addActionListener(e -> {
                Graph graph = generateRandomGraph(30, 0.6);
                canvas.setGraph(graph);

                try {
                    MSTDecomposition mst = new MSTDecomposition(graph);
                    mst.run();
                    List<List<Edge>> forest = mst.getResult();

                    List<Edge> largestTree = forest.stream()
                            .max(Comparator.comparingInt(List::size))
                            .orElse(new ArrayList<>());

                    Graph mstGraph = new Graph();
                    Set<Node> nodesInMST = new HashSet<>();
                    for (Edge edge : largestTree) {
                        nodesInMST.add(edge.getFrom());
                        nodesInMST.add(edge.getTo());
                    }
                    for (Node node : nodesInMST) {
                        mstGraph.addNode(node);
                    }
                    for (Edge edge : largestTree) {
                        mstGraph.addEdge(edge);
                        mstGraph.addEdge(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
                    }

                    canvas.setGraph(mstGraph);
                    canvas.setDecompositionType("Centroid Decomposition");

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Eroare MST+Centroid",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            bottomPanel.add(centroidOnMSTButton);
            frame.add(bottomPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    /**
     * Genereaza un arbore aleator valid (cu n noduri si n-1 muchii)
     */
    private static Graph generateRandomTree(int n) {
        Graph tree = new Graph();
        Random rand = new Random();

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i));
            tree.addNode(nodes.get(i));
        }

        for (int i = 1; i < n; i++) {
            int parentIndex = rand.nextInt(i);
            int weight = rand.nextInt(10) + 1;
            tree.addEdge(new Edge(nodes.get(i), nodes.get(parentIndex), weight));
            tree.addEdge(new Edge(nodes.get(parentIndex), nodes.get(i), weight));
        }

        return tree;
    }

    /**
     * Genereaza un graf aleator nedirectionat cu probabilitate p de muchie intre doua noduri
     */
    private static Graph generateRandomGraph(int n, double p) {
        Graph graph = new Graph();
        Random rand = new Random();

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i));
            graph.addNode(nodes.get(i));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rand.nextDouble() < p) {
                    int weight = rand.nextInt(10) + 1;
                    graph.addEdge(new Edge(nodes.get(i), nodes.get(j), weight));
                    graph.addEdge(new Edge(nodes.get(j), nodes.get(i), weight));
                }
            }
        }

        return graph;
    }
}

