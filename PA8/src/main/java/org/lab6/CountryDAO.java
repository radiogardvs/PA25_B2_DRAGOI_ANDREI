package org.lab6;

import java.sql.*;

public class CountryDAO {

    public void addCountry(String name, String code, int continentId) {
        String query = "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, code);
            stmt.setInt(3, continentId);
            stmt.executeUpdate();
            System.out.println("Tara adaugata: " + name);
        } catch (SQLException e) {
            System.out.println("Eroare la adaugarea tarii: " + e.getMessage());
        }
    }

    public Country getCountryById(int id) {
        Country country = null;
        String query = "SELECT * FROM countries WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               // country = new Country(rs.getInt("id"), rs.getString("name"), rs.getString("code"), rs.getInt("continent_id"));
                System.out.println("Tara gasita: " + country.getName());
            } else {
                System.out.println("Nu s-a gasit nici o tara cu id-ul: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la obtinerea tarii: " + e.getMessage());
        }
        return country;
    }
}
