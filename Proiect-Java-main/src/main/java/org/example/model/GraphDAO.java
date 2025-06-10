package org.example.model;

import java.sql.*;
import java.util.*;

/**
 * Clasa GraphDAO gestioneaza salvarea si incarcarea grafurilor in/din baza de date Oracle.
 */
public class GraphDAO {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    /**
     * Salveaza un graf in tabelele GRAF_NODURI si GRAF_MUCHII si apeleaza procedura SALVEAZA_REZULTAT.
     *
     * @param graph graful de salvat
     * @param tipDecompozitie tipul de decompozitie (ex: "Clique")
     * @param rezultatDescris descrierea rezultatului
     */
    public void saveGraph(Graph graph, String tipDecompozitie, String rezultatDescris) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Statement clearStmt = conn.createStatement();
            clearStmt.executeUpdate("DELETE FROM GRAF_MUCHII");
            clearStmt.executeUpdate("DELETE FROM GRAF_NODURI");

            PreparedStatement insertNode = conn.prepareStatement(
                    "INSERT INTO GRAF_NODURI (id, eticheta) VALUES (?, ?)"
            );
            for (Node node : graph.getNodes()) {
                insertNode.setInt(1, node.getId());
                insertNode.setString(2, String.valueOf(node.getId()));
                insertNode.executeUpdate();
            }

            PreparedStatement insertEdge = conn.prepareStatement(
                    "INSERT INTO GRAF_MUCHII (id, nod1, nod2, cost) VALUES (?, ?, ?, ?)"
            );
            int edgeId = 1;
            Set<String> inserate = new HashSet<>();

            for (Edge edge : graph.getEdges()) {
                int from = edge.getFrom().getId();
                int to = edge.getTo().getId();
                String key = from < to ? from + "-" + to : to + "-" + from;

                if (inserate.contains(key)) continue;
                inserate.add(key);

                insertEdge.setInt(1, edgeId++);
                insertEdge.setInt(2, from);
                insertEdge.setInt(3, to);
                insertEdge.setDouble(4, edge.getWeight());
                insertEdge.executeUpdate();
            }

            CallableStatement cs = conn.prepareCall("{ call SALVEAZA_REZULTAT(?, ?) }");
            cs.setString(1, tipDecompozitie);
            cs.setString(2, rezultatDescris);
            cs.execute();

            System.out.println("Graful si rezultatul au fost salvate in Oracle.");

        } catch (SQLException e) {
            System.err.println("Eroare salvare grafic: " + e.getMessage());
        }
    }

    /**
     * Incarca graful din tabelele GRAF_NODURI si GRAF_MUCHII.
     *
     * @return obiect Graph reconstruit din baza de date
     */
    public Graph loadGraph() {
        Graph graph = new Graph();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Statement stmtNodes = conn.createStatement();
            ResultSet rsNodes = stmtNodes.executeQuery("SELECT id FROM GRAF_NODURI");

            Map<Integer, Node> nodeMap = new HashMap<>();
            while (rsNodes.next()) {
                int id = rsNodes.getInt("id");
                Node node = new Node(id);
                graph.addNode(node);
                nodeMap.put(id, node);
            }

            Statement stmtEdges = conn.createStatement();
            ResultSet rsEdges = stmtEdges.executeQuery("SELECT nod1, nod2, cost FROM GRAF_MUCHII");

            while (rsEdges.next()) {
                int fromId = rsEdges.getInt("nod1");
                int toId = rsEdges.getInt("nod2");
                Node from = nodeMap.get(fromId);
                Node to = nodeMap.get(toId);

                if (from != null && to != null) {
                    int cost = rsEdges.getInt("cost");
                    graph.addEdge(new Edge(from, to, cost));
                }
            }

            System.out.println("Graful a fost incarcat din Oracle.");

        } catch (SQLException e) {
            System.err.println("Eroare la incarcarea grafului: " + e.getMessage());
        }

        return graph;
    }

    /**
     * Formateaza o lista de clique-uri intr-un sir textual afisabil.
     *
     * @param cliques lista de clique-uri (liste de noduri)
     * @return sir textual formatat
     */
    public String formatCliques(List<List<Node>> cliques) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (List<Node> clique : cliques) {
            sb.append("Clique ").append(index++).append(": ");
            for (int i = 0; i < clique.size(); i++) {
                sb.append(clique.get(i).getId());
                if (i < clique.size() - 1) sb.append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
