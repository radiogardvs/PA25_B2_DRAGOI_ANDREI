package com.pa.laboratory9.factory;

import com.pa.laboratory9.repository.jpa.CityRepository;
import com.pa.laboratory9.repository.jpa.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;

@Component
public class JpaFactory {

    private final EntityManager em;

    @Autowired
    public JpaFactory(EntityManager em) {
        this.em = em;
    }



    public ContinentRepository getContinentJpaRepository() {
        return null;
    }

    public CityRepository getCityJpaRepository() {
        return null;
    }


}
