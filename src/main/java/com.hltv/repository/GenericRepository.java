package com.hltv.repository;

import com.hltv.Entity.Player;
import com.hltv.Entity.Team;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class GenericRepository<T, ID> {

    private final Class<T> entityClass;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        }
    }

    public T findById(ID id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.find(entityClass, id);
        }
    }

    public T save(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public T update(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean deleteById(ID id) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public long count() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                    .getSingleResult();
        }
    }

    public boolean existsById(ID id) {
        return findById(id) != null;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}