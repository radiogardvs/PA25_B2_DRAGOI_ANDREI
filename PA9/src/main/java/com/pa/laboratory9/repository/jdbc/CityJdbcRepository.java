package com.pa.laboratory9.repository.jdbc;


import com.pa.laboratory9.model.City;
import com.pa.laboratory9.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityJdbcRepository {

    private final Connection connection;

    public CityJdbcRepository(Connection connection) {
        this.connection = connection;
    }

    public City create(City city) {
        String query = "INSERT INTO cities (name, population, country_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getPopulation());
            stmt.setLong(3, city.getCountry().getId());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                city.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating city", e);
        }
        return city;
    }

    public City findById(Long id) {
        String query = "SELECT * FROM cities WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCity(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding city by id", e);
        }
        return null;
    }

    public List<City> findByName(String namePattern) {
        String query = "SELECT * FROM cities WHERE name LIKE ?";
        List<City> cities = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + namePattern + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cities.add(mapRowToCity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding cities by name", e);
        }
        return cities;
    }

    private City mapRowToCity(ResultSet rs) throws SQLException {
        City city = new City();
        city.setId(rs.getLong("id"));
        city.setName(rs.getString("name"));
        city.setPopulation(rs.getInt("population"));
        // Assuming CountryRepository is available for setting the Country.
        // This would likely involve fetching the country from the database.
        // Add logic here to fetch and set the related country.
        return city;
    }
}
