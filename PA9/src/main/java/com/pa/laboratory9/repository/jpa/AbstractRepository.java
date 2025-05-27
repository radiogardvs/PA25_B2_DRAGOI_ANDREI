package com.pa.laboratory9.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.pa.laboratory9.util.LoggerUtil;

public abstract class AbstractRepository<T, ID> {
    @PersistenceContext
    protected EntityManager em;
    protected final Class<T> entityClass;
    protected final Logger logger = LoggerUtil.getLogger();

    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        executeInTransaction(em -> em.persist(entity), "create");
    }

    public T findById(ID id) {
        return measureExecution(() -> em.find(entityClass, id), "findById");
    }

    public List<T> findByName(String name) {
        return measureExecution(() ->
                em.createNamedQuery(entityClass.getSimpleName() + ".findByName", entityClass)
                        .setParameter("name", name)
                        .getResultList(), "findByName");
    }

    protected void executeInTransaction(Consumer<EntityManager> action, String label) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            long start = System.nanoTime();
            action.accept(em);
            tx.commit();
            long end = System.nanoTime();
            logger.info(() -> label + " executed in " + (end - start) / 1_000_000 + " ms.");
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            logger.severe(() -> label + " failed: " + e.getMessage());
            throw e;
        }
    }

    protected <R> R measureExecution(Supplier<R> action, String label) {
        long start = System.nanoTime();
        try {
            R result = action.get();  long end = System.nanoTime();
            logger.info(() -> label + " executed in " + (end - start) / 1_000_000 + " ms.");
            return result;
        } catch (RuntimeException e) {
            logger.severe(() -> label + " failed: " + e.getMessage());
            throw e;
        }
    }
}
