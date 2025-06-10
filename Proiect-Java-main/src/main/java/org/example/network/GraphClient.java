// GraphClient.java
package org.example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Graph;
import java.io.*;
import java.net.Socket;

/**
 * Client TCP simplu care:
 * - incarca un graf dintr-un fisier JSON din resources
 * - trimite graful catre server (in format JSON)
 * - asteapta si afiseaza raspunsul primit de la server
 */
public class GraphClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Conectat la server.");

            // BufferedWriter pentru a trimite date catre server
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // BufferedReader pentru a citi raspunsul de la server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Jackson ObjectMapper pentru (de)serializare JSON
            ObjectMapper mapper = new ObjectMapper();

            // Incarca graful din folderul resources (src/main/resources/graphs/mst_exemplu.json)
            InputStream is = GraphClient.class.getClassLoader().getResourceAsStream("graphs/mst_exemplu.json");
            if (is == null) {
                System.err.println("Nu am gasit fisierul mst_exemplu.json in resources.");
                return;
            }

            // Parseaza JSON-ul intr-un obiect Graph
            Graph graph = mapper.readValue(is, Graph.class);

            // Transforma graful inapoi in JSON (string) pentru trimitere
            String json = mapper.writeValueAsString(graph);

            System.out.println("JSON trimis");
            System.out.println(json);

            // Trimite JSON-ul catre server + newline ca delimitator
            out.write(json);
            out.newLine();
            out.flush();

            // Citeste si afiseaza fiecare linie din raspuns
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
