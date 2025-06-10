package org.example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.algorithms.MSTDecomposition;
import org.example.model.Edge;
import org.example.model.Graph;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Server TCP care asculta pe un port si proceseaza grafuri primite de la clienti:
 * - primeste un graf in format JSON
 * - aplica algoritmul MST (Minimum Spanning Tree Decomposition)
 * - trimite inapoi forestul (lista de arbori) rezultat
 */
public class GraphServer {
    public static void main(String[] args) {
        int port = 5000;
        ObjectMapper mapper = new ObjectMapper();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serverul a pornit pe portul " + port);

            // loop infinit: accepta clienti si proceseaza grafuri
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client conectat: " + clientSocket.getInetAddress());

                    // Input + Output
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // Citeste JSON-ul trimis de client linie cu linie
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        sb.append(line);
                    }

                    // Parseaza obiectul Graph din JSON
                    Graph graph = mapper.readValue(sb.toString(), Graph.class);
                    System.out.println("Graf primit de la client.");

                    // Aplica MSTDecomposition pe graf
                    MSTDecomposition mst = new MSTDecomposition(graph);
                    mst.run();
                    List<List<Edge>> forest = mst.getResult();

                    // Trimite rezultatul inapoi la client (arbori minimali)
                    out.write("=== Rezultat MST ===\n");
                    int idx = 1;
                    for (List<Edge> tree : forest) {
                        out.write("Arbore MST #" + idx++ + " (" + tree.size() + " muchii):\n");
                        for (Edge e : tree) {
                            out.write(e.getFrom() + " --(" + e.getWeight() + ")--> " + e.getTo() + "\n");
                        }
                        out.write("---\n");
                    }
                    out.flush();

                } catch (IOException e) {
                    System.err.println("Eroare client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Eroare server: " + e.getMessage());
        }
    }
}
