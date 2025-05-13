package org.lab6;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ContinentRepository {
    private EntityManager em = EntityManagerSingleton.getEntityManagerFactory().createEntityManager();

    public void create(Continent continent) {
        em.getTransaction().begin();
        em.persist(continent);
        em.getTransaction().commit();
    }

    public Continent findById(Long id) {
        return em.find(Continent.class, id);
    }

    public List<Continent> findByName(String name) {
        TypedQuery<Continent> query = em.createQuery("SELECT c FROM Continent c WHERE c.name LIKE :name", Continent.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Continent> findByCode(String code) {
        TypedQuery<Continent> query = em.createQuery("SELECT c FROM Continent c WHERE c.code LIKE :code", Continent.class);
        query.setParameter("code", "%" + code + "%");
        return query.getResultList();
    }
}
