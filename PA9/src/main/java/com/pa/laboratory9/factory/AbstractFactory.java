package com.pa.laboratory9.factory;

import com.pa.laboratory9.repository.jdbc.CountryJdbcRepository;
import com.pa.laboratory9.repository.jdbc.ContinentJdbcRepository;
import com.pa.laboratory9.repository.jdbc.CityJdbcRepository;
import com.pa.laboratory9.repository.jpa.CountryRepository;
import com.pa.laboratory9.repository.jpa.ContinentRepository;
import com.pa.laboratory9.repository.jpa.CityRepository;

public abstract class AbstractFactory {

    public abstract CountryJdbcRepository getCountryJdbcRepository();
    public abstract ContinentJdbcRepository getContinentJdbcRepository();
    public abstract CityJdbcRepository getCityJdbcRepository();

    public abstract CountryRepository getCountryJpaRepository();
    public abstract ContinentRepository getContinentJpaRepository();
    public abstract CityRepository getCityJpaRepository();
}
