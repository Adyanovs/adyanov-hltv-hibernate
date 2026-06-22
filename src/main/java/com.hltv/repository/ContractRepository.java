package com.hltv.repository;

import com.hltv.Entity.Contract;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ContractRepository extends GenericRepository<Contract, Integer> {

    public ContractRepository() {
        super(Contract.class);
    }

    public List<Contract> findByTeamName(String teamName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Contract c WHERE c.team.teamName = :teamName ORDER BY c.player.nickname",
                    Contract.class).setParameter("teamName", teamName).getResultList();
        }
    }

    public Optional<Contract> findByPlayerNickname(String nickname) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Contract> result = em.createQuery(
                    "FROM Contract c WHERE c.player.nickname = :nickname",
                    Contract.class).setParameter("nickname", nickname).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void deleteContract(String nickname) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<Contract> contracts = em.createQuery(
                    "FROM Contract c WHERE c.player.nickname = :nickname",
                    Contract.class).setParameter("nickname", nickname).getResultList();
            for (Contract contract : contracts) {
                em.remove(contract);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void extendContract(String nickname, LocalDate newFinalDate) {
        EntityManager em = HibernateUtil.createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<Contract> contracts = em.createQuery(
                    "FROM Contract c WHERE c.player.nickname = :nickname",
                    Contract.class).setParameter("nickname", nickname).getResultList();
            for (Contract contract : contracts) {
                if (contract.getFinalDate().isBefore(newFinalDate)) {
                    contract.setFinalDate(newFinalDate);
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Contract> findAllWithDetails() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT c FROM Contract c " +
                            "JOIN FETCH c.team " +
                            "JOIN FETCH c.player " +
                            "ORDER BY c.team.teamName",
                    Contract.class).getResultList();
        }
    }
}