package com.pa.laboratory9.repository.jpa;

import com.pa.laboratory9.model.City;
import jakarta.persistence.EntityManager;

public class CityRepository extends AbstractRepository<City, Long> {
    public CityRepository(EntityManager em) {
        super(City.class);
    }
}
