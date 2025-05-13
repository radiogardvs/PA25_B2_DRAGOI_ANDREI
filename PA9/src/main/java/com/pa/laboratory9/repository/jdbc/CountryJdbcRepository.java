package com.pa.laboratory9.repository.jdbc;

import com.pa.laboratory9.model.Country;
import com.pa.laboratory9.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryJdbcRepository {

    private final Connection connection;

    public CountryJdbcRepository(Connection connection) {
        this.connection = connection;
    }

    public Country create(Country country) {
        String query = "INSERT INTO countries (name, code, population, continent_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getCode());
            stmt.setInt(3, country.getPopulation());
            stmt.setLong(4, country.getContinent().getId());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                country.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating country", e);
        }
        return country;
    }

    public Country findById(Long id) {
        String query = "SELECT * FROM countries WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCountry(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding country by id", e);
        }
        return null;
    }

    public List<Country> findByName(String namePattern) {
        String query = "SELECT * FROM countries WHERE name LIKE ?";
        List<Country> countries = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + namePattern + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                countries.add(mapRowToCountry(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding countries by name", e);
        }
        return countries;
    }

    private Country mapRowToCountry(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setId(rs.getLong("id"));
        country.setName(rs.getString("name"));
        country.setCode(rs.getString("code"));
        country.setPopulation(rs.getInt("population"));
        // Assuming ContinentRepository is available for setting the Continent.
        // Add logic here to fetch and set the related continent.
        return country;
    }
}

