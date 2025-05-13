package org.lab6;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class  CountryRepository {
    private EntityManager em = EntityManagerSingleton.getEntityManagerFactory().createEntityManager();

    public void create(Country country) {
        em.getTransaction().begin();
        em.persist(country);
        em.getTransaction().commit();
    }

    public Country findById(Long id) {
        return em.find(Country.class, id);
    }

    public List<Country> findByName(String name) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.name LIKE :name", Country.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Country> findByCode(String code) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.code LIKE :code", Country.class);
        query.setParameter("code", "%" + code + "%");
        return query.getResultList();
    }
}
