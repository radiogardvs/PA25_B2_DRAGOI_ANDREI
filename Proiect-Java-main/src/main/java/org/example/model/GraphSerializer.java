package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clasa utilitară pentru salvarea și încărcarea grafurilor în/din fișiere JSON.
 * Folosește biblioteca Jackson pentru serializare și oferă metode multiple de export/import.
 */
public class GraphSerializer {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Salvează un graf într-un fișier JSON.
     * @param graph Graful de salvat
     * @param filename Calea completă a fișierului de ieșire
     * @throws IOException Dacă apare o eroare la scriere
     */
    public static void saveGraph(Graph graph, String filename) throws IOException {
        Map<String, Object> data = new HashMap<>();

        List<Integer> nodes = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            nodes.add(node.getId());
        }

        List<Map<String, Object>> edges = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            Map<String, Object> edgeMap = new HashMap<>();
            edgeMap.put("from", edge.getFrom().getId());
            edgeMap.put("to", edge.getTo().getId());
            edgeMap.put("weight", edge.getWeight());
            edges.add(edgeMap);
        }

        data.put("nodes", nodes);
        data.put("edges", edges);

        mapper.writeValue(new File(filename), data);
    }

    /**
     * Încarcă un graf dintr-un fișier JSON din resources.
     * @param resourcePath Calea către fișier (relativ la resources)
     * @return Obiectul Graph încărcat
     * @throws IOException Dacă fișierul nu este găsit sau nu poate fi citit
     */
    public static Graph loadGraph(String resourcePath) throws IOException {
        InputStream inputStream = GraphSerializer.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Fișierul nu a fost găsit în resurse: " + resourcePath);
        }

        Map<String, Object> data = mapper.readValue(inputStream, new TypeReference<>() {});
        Graph graph = new Graph();
        Map<Integer, Node> nodeMap = new HashMap<>();

        List<Integer> nodes = (List<Integer>) data.get("nodes");
        for (Integer id : nodes) {
            Node node = new Node(id);
            graph.addNode(node);
            nodeMap.put(id, node);
        }

        List<Map<String, Object>> edges = (List<Map<String, Object>>) data.get("edges");
        for (Map<String, Object> edgeData : edges) {
            int from = (int) edgeData.get("from");
            int to = (int) edgeData.get("to");
            int weight = (int) edgeData.get("weight");
            graph.addEdge(new Edge(nodeMap.get(from), nodeMap.get(to), weight));
        }

        return graph;
    }

    /**
     * Salvează un graf într-un director specificat, cu un nume fix.
     * @param directory Directorul unde se va salva
     * @param filename Numele fișierului (fără cale)
     * @param graph Graful de salvat
     * @throws IOException Dacă directorul nu poate fi creat sau fișierul nu poate fi scris
     */
    public static void saveGraphTo(String directory, String filename, Graph graph) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Nu am putut crea directorul: " + directory);
            }
        }

        String fullPath = directory + File.separator + filename;
        saveGraph(graph, fullPath);
        System.out.println("Graf salvat la: " + fullPath);
    }

    /**
     * Salvează un graf într-un director cu un nume generat automat (timestamp).
     * @param graph Graful de salvat
     * @param directory Directorul destinație
     * @param prefix Prefixul numelui fișierului
     * @throws IOException Dacă apar erori la scriere
     */
    public static void saveGraphAuto(Graph graph, String directory, String prefix) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Nu am putut crea directorul: " + directory);
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = prefix + "_" + timestamp + ".json";

        saveGraph(graph, directory + File.separator + filename);
        System.out.println("Graf salvat automat: " + filename);
    }

    /**
     * Salvează mai multe grafuri într-un director.
     * @param graphs Map cu nume → graf
     * @param directory Directorul în care se vor salva grafurile
     * @throws IOException Dacă apar erori la scriere
     */
    public static void saveMultipleGraphs(Map<String, Graph> graphs, String directory) throws IOException {
        for (Map.Entry<String, Graph> entry : graphs.entrySet()) {
            String name = entry.getKey();
            Graph graph = entry.getValue();
            saveGraphTo(directory, name + ".json", graph);
        }
    }
}
