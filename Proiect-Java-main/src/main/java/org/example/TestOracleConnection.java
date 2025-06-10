package org.example;

/**
 * Clasa TestOracleConnection verifica daca aplicatia Java se poate conecta la baza de date Oracle.
 * Este utila pentru depanarea conexiunii JDBC.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestOracleConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    /**
     * Metoda principala care incearca sa stabileasca o conexiune cu baza de date Oracle.
     * Afiseaza un mesaj de succes sau de eroare in functie de rezultat.
     */
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexiune reusita la Oracle.");
        } catch (SQLException e) {
            System.err.println("Eroare conexiune: " + e.getMessage());
        }
    }
}
