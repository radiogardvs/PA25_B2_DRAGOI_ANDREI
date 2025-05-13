package com.pa.laboratory9.factory;

import com.pa.laboratory9.repository.jdbc.CountryJdbcRepository;
import com.pa.laboratory9.repository.jdbc.ContinentJdbcRepository;
import com.pa.laboratory9.repository.jdbc.CityJdbcRepository;
import com.pa.laboratory9.repository.jpa.CityRepository;
import com.pa.laboratory9.repository.jpa.ContinentRepository;
import com.pa.laboratory9.repository.jpa.CountryRepository;
import com.pa.laboratory9.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcFactory extends AbstractFactory {
    private final Connection connection;

    // Constructorul creează conexiunea JDBC
    public JdbcFactory() {
        try {
            this.connection = JdbcUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish JDBC connection", e);
        }
    }

    @Override
    public CountryJdbcRepository getCountryJdbcRepository() {
        return new CountryJdbcRepository(connection);
    }

    @Override
    public ContinentJdbcRepository getContinentJdbcRepository() {
        return new ContinentJdbcRepository(connection);
    }

    @Override
    public CityJdbcRepository getCityJdbcRepository() {
        return new CityJdbcRepository(connection);
    }

    @Override
    public CountryRepository getCountryJpaRepository() {
        throw new UnsupportedOperationException("JPA not supported in JdbcFactory");
    }

    @Override
    public ContinentRepository getContinentJpaRepository() {
        throw new UnsupportedOperationException("JPA not supported in JdbcFactory");
    }

    @Override
    public CityRepository getCityJpaRepository() {
        throw new UnsupportedOperationException("JPA not supported in JdbcFactory");
    }
}
