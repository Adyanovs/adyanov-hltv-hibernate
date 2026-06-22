package com.hltv.repository;

import com.hltv.Entity.Player;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class PlayerRepository extends GenericRepository<Player, String> {

    public PlayerRepository() {
        super(Player.class);
    }

    public List<Player> findByRole(String role) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Player p WHERE p.role = :role ORDER BY p.nickname",
                    Player.class).setParameter("role", role).getResultList();
        }
    }

    public List<Player> findByStatus(String status) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Player p WHERE p.status = :status ORDER BY p.nickname",
                    Player.class).setParameter("status", status).getResultList();
        }
    }

    public List<Player> findByTeamName(String teamName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT p FROM Player p JOIN Contract c ON c.player = p WHERE c.team.teamName = :teamName ORDER BY p.nickname",
                    Player.class).setParameter("teamName", teamName).getResultList();
        }
    }

    public List<Player> findAllWithStatistics() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Player p LEFT JOIN FETCH p.statistics ORDER BY p.nickname",
                    Player.class).getResultList();
        }
    }

    public Optional<Player> findByNickname(String nickname) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Player> result = em.createQuery(
                    "SELECT p FROM Player p WHERE p.nickname = :nickname",
                    Player.class).setParameter("nickname", nickname).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void updateStatus(String nickname, String status) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Player player = em.find(Player.class, nickname);
            if (player != null) {
                player.setStatus(status);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateRole(String nickname, String role) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Player player = em.find(Player.class, nickname);
            if (player != null) {
                player.setRole(role);
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