package com.hltv.repository;

import com.hltv.Entity.Statistics;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class StatisticsRepository extends GenericRepository<Statistics, Integer> {

    public StatisticsRepository() {
        super(Statistics.class);
    }

    public Optional<Statistics> findByPlayerNickname(String nickname) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Statistics> result = em.createQuery(
                    "SELECT s FROM Statistics s WHERE s.player.nickname = :nickname",
                    Statistics.class).setParameter("nickname", nickname).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Statistics> findTopByFirepower(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.firepower DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findTopByOpening(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.opening DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findTopByClutching(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.clutching DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findTopBySniper(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.sniper DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findTopByUtility(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.utility DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findTopByTrading(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s ORDER BY s.trading DESC",
                    Statistics.class).setMaxResults(limit).getResultList();
        }
    }

    public List<Statistics> findByMinOverallRating(int minFirepower, int minOpening, int minClutching) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                            "FROM Statistics s WHERE s.firepower >= :minFirepower AND s.opening >= :minOpening AND s.clutching >= :minClutching ORDER BY s.firepower DESC",
                            Statistics.class)
                    .setParameter("minFirepower", minFirepower)
                    .setParameter("minOpening", minOpening)
                    .setParameter("minClutching", minClutching)
                    .getResultList();
        }
    }

    public List<Statistics> findAllWithPlayers() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Statistics s JOIN FETCH s.player ORDER BY s.player.nickname",
                    Statistics.class).getResultList();
        }
    }

    public Optional<Statistics> findWithPlayer(String nickname) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Statistics> result = em.createQuery(
                    "FROM Statistics s JOIN FETCH s.player WHERE s.player.nickname = :nickname",
                    Statistics.class).setParameter("nickname", nickname).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void updateStats(String nickname, int firepower, int opening, int clutching, int sniper, int utility, int trading) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Statistics stats = em.createQuery(
                    "FROM Statistics s WHERE s.player.nickname = :nickname",
                    Statistics.class).setParameter("nickname", nickname).getResultStream().findFirst().orElse(null);
            if (stats != null) {
                stats.setFirepower(firepower);
                stats.setOpening(opening);
                stats.setClutching(clutching);
                stats.setSniper(sniper);
                stats.setUtility(utility);
                stats.setTrading(trading);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    public void deleteByPlayer(String nickname) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Statistics stats = em.createQuery(
                    "FROM Statistics s WHERE s.player.nickname = :nickname",
                    Statistics.class).setParameter("nickname", nickname).getResultStream().findFirst().orElse(null);
            if (stats != null) {
                em.remove(stats);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}