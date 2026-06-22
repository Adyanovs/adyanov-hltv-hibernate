package com.hltv.repository;

import com.hltv.Entity.Team;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class TeamRepository extends GenericRepository<Team, String> {

    public TeamRepository() {
        super(Team.class);
    }

    public List<Team> getAllTeams(){
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Team t ORDER BY t.rank",
                    Team.class).getResultList();
        }
    }

    public List<Team> findByCountry(String country) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Team t WHERE t.country = :country ORDER BY t.rank",
                    Team.class).setParameter("country", country).getResultList();
        }
    }

    public boolean findByRank(int rank) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            Long count = em.createQuery(
                    "SELECT COUNT(t) FROM Team t WHERE t.rank = :rank",
                    Long.class).setParameter("rank", rank).getSingleResult();
            return count > 0;
        }
    }

    public List<Team> findTopRanked(int limit) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Team t ORDER BY t.rank",
                    Team.class).setMaxResults(limit).getResultList();
        }
    }

    public Optional<Team> findWithPlayers(String teamName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Team> result = em.createQuery(
                    "SELECT t FROM Team t JOIN FETCH t.players WHERE t.teamName = :teamName",
                    Team.class).setParameter("teamName", teamName).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Team> findAllWithPlayers() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ORDER BY t.rank",
                    Team.class).getResultList();
        }
    }

    public Optional<Team> findByTeamName(String teamName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Team> result = em.createQuery(
                    "SELECT t FROM Team t WHERE t.teamName = :teamName",
                    Team.class).setParameter("teamName", teamName).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void updateRank(String teamName, int newRank) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Team team = em.find(Team.class, teamName);
            if (team != null && team.getRank() != newRank) {
                team.setRank(newRank);
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