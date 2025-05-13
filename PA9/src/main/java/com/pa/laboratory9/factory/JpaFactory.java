package com.pa.laboratory9.factory;

import com.pa.laboratory9.repository.jdbc.CityJdbcRepository;
import com.pa.laboratory9.repository.jdbc.ContinentJdbcRepository;
import com.pa.laboratory9.repository.jdbc.CountryJdbcRepository;
import com.pa.laboratory9.repository.jpa.CountryRepository;
import com.pa.laboratory9.repository.jpa.ContinentRepository;
import com.pa.laboratory9.repository.jpa.CityRepository;
import com.pa.laboratory9.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class JpaFactory extends AbstractFactory {
    private final EntityManager em = JpaUtil.getEntityManager();

    @Override
    public CountryRepository getCountryJpaRepository() {
        return new CountryRepository(em);
    }

    @Override
    public ContinentRepository getContinentJpaRepository() {
        return new ContinentRepository(em);
    }

    @Override
    public CityRepository getCityJpaRepository() {
        return new CityRepository(em);
    }

    @Override
    public CountryJdbcRepository getCountryJdbcRepository() {
        throw new UnsupportedOperationException("JDBC not supported in JpaFactory");
    }

    @Override
    public ContinentJdbcRepository getContinentJdbcRepository() {
        throw new UnsupportedOperationException("JDBC not supported in JpaFactory");
    }

    @Override
    public CityJdbcRepository getCityJdbcRepository() {
        throw new UnsupportedOperationException("JDBC not supported in JpaFactory");
    }
}
