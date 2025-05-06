package org.lab6;

import java.sql.*;

public class ContinentDAO {

    public void addContinent(String name) {
        String query = "INSERT INTO continents (name) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("Continent adaugat: " + name);
        } catch (SQLException e) {
            System.out.println("Eroare la adaugarea continentului: " + e.getMessage());
        }
    }

    public Continent getContinentByName(String name) {
        Continent continent = null;
        String query = "SELECT * FROM continents WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                continent = new Continent(rs.getInt("id"), rs.getString("name"));
                System.out.println("Continent gasit: " + continent.getName());
            } else {
                System.out.println("Nu s-a gasit niciun continent cu numele: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la obtinerea continentului: " + e.getMessage());
        }
        return continent;
    }
}
