package com.pa.laboratory9.repository.jpa;

import com.pa.laboratory9.model.Country;
import jakarta.persistence.EntityManager;

public class CountryRepository extends AbstractRepository<Country, Long> {
    public CountryRepository(EntityManager em) {
        super(Country.class);
    }
}
