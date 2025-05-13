package com.pa.laboratory9.repository.jpa;

import com.pa.laboratory9.model.Continent;
import jakarta.persistence.EntityManager;

public class ContinentRepository extends AbstractRepository<Continent, Long> {
    public ContinentRepository(EntityManager em) {
        super(Continent.class);
    }
}
