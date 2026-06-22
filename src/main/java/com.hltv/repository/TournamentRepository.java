package com.hltv.repository;

import com.hltv.Entity.Tournament;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class TournamentRepository extends GenericRepository<Tournament, String> {

    public TournamentRepository() {
        super(Tournament.class);
    }

    public Optional<Tournament> findWithMatches(String tournamentName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Tournament> result = em.createQuery(
                    "SELECT t FROM Tournament t LEFT JOIN FETCH t.matches WHERE t.tournamentName = :tournamentName",
                    Tournament.class).setParameter("tournamentName", tournamentName).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Tournament> findAllWithMatches() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.matches ORDER BY t.tournamentName",
                    Tournament.class).getResultList();
        }
    }

    public Optional<Tournament> findByTournamentName(String tournamentName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Tournament> result = em.createQuery(
                    "SELECT t FROM Tournament t WHERE t.tournamentName = :tournamentName",
                    Tournament.class).setParameter("tournamentName", tournamentName).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void updatePrizePool(String tournamentName, int newPrizePool) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tournament tournament = em.find(Tournament.class, tournamentName);
            if (tournament != null && newPrizePool >= 0) {
                tournament.setPrizePool(newPrizePool);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateSponsors(String tournamentName, String sponsors) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tournament tournament = em.find(Tournament.class, tournamentName);
            if (tournament != null) {
                tournament.setSponsors(sponsors);
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