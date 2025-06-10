package org.example.visualizer;

import org.example.model.*;
import org.example.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Clasa GraphCanvas este responsabila pentru desenarea grafica a unui graf si a decompozitiilor sale.
 * Permite vizualizarea diferitelor tipuri de decompozitii precum:
 * - Arbore partial de acoperire (Minimum Spanning Tree)
 * - Componente biconexe
 * - Decompozitie in clique-uri
 * - Decompozitie in arbori
 * - Decompozitie pe centroidi
 * Nodurile sunt dispuse in cerc, iar muchiile sunt colorate diferit in functie de componenta.
 */
public class GraphCanvas extends JPanel {
    private String decompositionType = "None";
    private Graph graph;
    private List<Edge> currentEdges = new ArrayList<>();
    private List<List<Edge>> componentEdges = new ArrayList<>();
    private List<List<Node>> currentPaths = new ArrayList<>();
    private Map<Node, Node> centroidParent = new HashMap<>();
    private List<Set<Node>> currentCliques = new ArrayList<>();
    private Set<Node> articulationPoints = new HashSet<>();

    public GraphCanvas() {
        generateRandomGraph();
    }

    /**
     * Seteaza tipul de decompozitie ce va fi aplicat pe graf.
     * @param type Tipul (ex: "Minimum Spanning Tree", "Clique Decomposition")
     */
    public void setDecompositionType(String type) {
        this.decompositionType = type;
        runDecomposition();
    }

    /**
     * Seteaza un nou graf ce va fi desenat.
     * @param graph Obiectul Graph de utilizat
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    /**
     * Genereaza un graf aleator pentru testare.
     */
    public void generateRandomGraph() {
        graph = new Graph();
        Random rand = new Random();
        int nodeCount = 20;

        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Node(i));
        }

        for (int i = 0; i < nodeCount; i++) {
            for (int j = i + 1; j < nodeCount; j++) {
                if (rand.nextDouble() < 0.2) {
                    int weight = rand.nextInt(10) + 1;
                    graph.addEdge(new Edge(graph.getNodes().get(i), graph.getNodes().get(j), weight));
                    graph.addEdge(new Edge(graph.getNodes().get(j), graph.getNodes().get(i), weight));
                }
            }
        }
    }

    /**
     * Aplica decompozitia selectata si salveaza datele pentru desen.
     */
    private void runDecomposition() {
        currentEdges.clear();
        componentEdges.clear();
        currentPaths.clear();
        centroidParent.clear();
        currentCliques.clear();
        articulationPoints.clear();

        switch (decompositionType) {
            case "Minimum Spanning Tree" -> {
                MSTDecomposition mst = new MSTDecomposition(graph);
                mst.run();
                List<List<Edge>> forest = mst.getResult();
                forest.forEach(currentEdges::addAll);
            }
            case "Biconnected Components" -> {
                BiconnectedDecomposition bcd = new BiconnectedDecomposition(graph);
                bcd.run();
                componentEdges.addAll(bcd.getResult());
                articulationPoints.addAll(bcd.getArticulationPoints());
            }
            case "Clique Decomposition" -> {
                CliqueDecomposition cd = new CliqueDecomposition(graph, 1.0);
                cd.run();
                currentCliques = cd.getResult();
            }
            case "Tree Decomposition" -> {
                TreeDecomposition td = new TreeDecomposition(graph);
                td.run();
                currentPaths = td.getResult();
                for (List<Node> path : currentPaths) {
                    for (int i = 0; i < path.size() - 1; i++) {
                        currentEdges.add(new Edge(path.get(i), path.get(i + 1), 1));
                    }
                }
            }
            case "Centroid Decomposition" -> {
                CentroidDecomposition cd = new CentroidDecomposition(graph);
                cd.run();
                centroidParent = cd.getResult();
                for (Map.Entry<Node, Node> entry : centroidParent.entrySet()) {
                    if (entry.getValue() != null) {
                        currentEdges.add(new Edge(entry.getKey(), entry.getValue(), 1));
                    }
                }
            }
        }

        repaint();
    }

    /**
     * Deseneaza panoul grafic in functie de noduri, muchii si decompozitii.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) return;

        int width = getWidth();
        int height = getHeight();
        int radius = 300;
        int centerX = width / 2;
        int centerY = height / 2;

        Map<Node, Point> positions = new HashMap<>();
        List<Node> nodes = graph.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            double angle = 2 * Math.PI * i / nodes.size();
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            positions.put(nodes.get(i), new Point(x, y));
        }

        g.setFont(new Font("Arial", Font.PLAIN, 10));

        if (decompositionType.equals("Clique Decomposition")) {
            Random rand = new Random();
            for (Set<Node> clique : currentCliques) {
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                for (Node u : clique) {
                    for (Node v : clique) {
                        if (!u.equals(v)) {
                            Point p1 = positions.get(u);
                            Point p2 = positions.get(v);
                            g.drawLine(p1.x, p1.y, p2.x, p2.y);
                        }
                    }
                }
            }
        } else if (decompositionType.equals("Biconnected Components") && !componentEdges.isEmpty()) {
            Random rand = new Random();
            for (List<Edge> comp : componentEdges) {
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                for (Edge e : comp) {
                    Point p1 = positions.get(e.getFrom());
                    Point p2 = positions.get(e.getTo());
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        } else if (!currentEdges.isEmpty()) {
            if (!currentPaths.isEmpty()) {
                Random rand = new Random();
                int edgeIndex = 0;
                for (List<Node> path : currentPaths) {
                    g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    for (int i = 0; i < path.size() - 1; i++) {
                        Edge e = currentEdges.get(edgeIndex++);
                        Point p1 = positions.get(e.getFrom());
                        Point p2 = positions.get(e.getTo());
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            } else {
                g.setColor(Color.GRAY);
                for (Edge e : currentEdges) {
                    Point p1 = positions.get(e.getFrom());
                    Point p2 = positions.get(e.getTo());
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    g.drawString(String.valueOf(e.getWeight()), (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
                }
            }
        } else if (!centroidParent.isEmpty()) {
            g.setColor(Color.MAGENTA);
            for (Map.Entry<Node, Node> entry : centroidParent.entrySet()) {
                if (entry.getValue() != null) {
                    Point p1 = positions.get(entry.getKey());
                    Point p2 = positions.get(entry.getValue());
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        for (Node n : nodes) {
            Point p = positions.get(n);
            g.setColor(articulationPoints.contains(n) ? Color.RED : Color.BLUE);
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString("N" + n.getId(), p.x - 8, p.y + 5);
        }
    }
}
