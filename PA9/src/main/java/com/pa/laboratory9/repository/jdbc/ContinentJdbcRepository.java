package com.pa.laboratory9.repository.jdbc;

import com.pa.laboratory9.model.Continent;
import com.pa.laboratory9.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContinentJdbcRepository {

    private final Connection connection;

    public ContinentJdbcRepository(Connection connection) {
        this.connection = connection;
    }

    public Continent create(Continent continent) {
        String query = "INSERT INTO continents (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, continent.getName());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                continent.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating continent", e);
        }
        return continent;
    }

    public Continent findById(Long id) {
        String query = "SELECT * FROM continents WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToContinent(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding continent by id", e);
        }
        return null;
    }

    public List<Continent> findByName(String namePattern) {
        String query = "SELECT * FROM continents WHERE name LIKE ?";
        List<Continent> continents = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + namePattern + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                continents.add(mapRowToContinent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding continents by name", e);
        }
        return continents;
    }

    private Continent mapRowToContinent(ResultSet rs) throws SQLException {
        Continent continent = new Continent();
        continent.setId(rs.getLong("id"));
        continent.setName(rs.getString("name"));
        return continent;
    }
}
