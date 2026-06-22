package com.hltv.repository;

import com.hltv.Entity.Match;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MatchRepository extends GenericRepository<Match, Integer> {

    public MatchRepository() {
        super(Match.class);
    }

    public List<Match> findByTournamentName(String tournamentName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Match m WHERE m.tournament.tournamentName = :tournamentName ORDER BY m.startDate DESC",
                    Match.class).setParameter("tournamentName", tournamentName).getResultList();
        }
    }

    public List<Match> findByTeamName(String teamName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Match m WHERE m.team1.teamName = :teamName OR m.team2.teamName = :teamName ORDER BY m.startDate DESC",
                    Match.class).setParameter("teamName", teamName).getResultList();
        }
    }

    public Optional<Match> findByIdWithDetails(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Match> result = em.createQuery(
                    "FROM Match m JOIN FETCH m.tournament JOIN FETCH m.team1 JOIN FETCH m.team2 WHERE m.idMatch = :id",
                    Match.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public long countMatchesByTournament(String tournamentName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT COUNT(m) FROM Match m WHERE m.tournament.tournamentName = :tournamentName",
                    Long.class).setParameter("tournamentName", tournamentName).getSingleResult();
        }
    }

    public List<Match> findAllWithDetails() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                            "SELECT DISTINCT m FROM Match m " +
                                    "JOIN FETCH m.team1 " +
                                    "JOIN FETCH m.team2 " +
                                    "JOIN FETCH m.tournament " +
                                    "ORDER BY m.startDate DESC",
                            Match.class)
                    .getResultList();
        }
    }

}