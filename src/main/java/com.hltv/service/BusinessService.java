package com.hltv.service;

import com.hltv.Entity.*;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BusinessService {

    public void getTeamsWithPlayers() {
        printHeader("Команды и их игроки");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("SELECT t.teamName, t.country, t.rank, p.nickname, p.role " +
                    "FROM Team t " +
                    "JOIN Contract c ON c.team = t " +
                    "JOIN Player p ON c.player = p " +
                    "WHERE c.finalDate > CURRENT_DATE " +
                    "ORDER BY t.rank, p.nickname", Object[].class).setMaxResults(30).getResultList();
            System.out.printf("     %-25s %-15s %-6s %-20s %-12s%n", "Команда", "Страна", "Ранг", "Игрок", "Роль");
            System.out.println("     " + "─".repeat(80));
            for (Object[] row : results) {
                System.out.printf("     %-25s %-15s %-6d %-20s %-12s%n", truncate((String) row[0], 24), row[1] != null ? row[1] : "—", (Integer) row[2], truncate((String) row[3], 19), truncate((String) row[4], 11));
            }
        }
        printDivider();
    }

    public void getPlayersWithStats() {
        printHeader("Игроки и их статистика");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("SELECT p.nickname, p.role, p.status, s.firepower, " +
                    "s.opening, s.clutching, s.sniper, s.utility, s.trading " +
                    "FROM Player p " +
                    "JOIN Statistics s ON s.player = p " +
                    "WHERE p.status = 'ACTIVE' " +
                    "ORDER BY p.nickname", Object[].class).setMaxResults(20).getResultList();

            System.out.printf("     %-20s %-12s %-10s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                    "Никнейм", "Роль", "Статус", "Огневая", "Опенинг", "Клатчинг", "Снайпер", "Утил", "Трейдинг");
            System.out.println("     " + "─".repeat(102));
            for (Object[] row : results) {
                System.out.printf("     %-20s %-12s %-10s %-10d %-10d %-10d %-10d %-10d %-10d%n", truncate((String) row[0], 19), truncate((String) row[1], 11), row[2], (Integer) row[3], (Integer) row[4], (Integer) row[5], (Integer) row[6], (Integer) row[7], (Integer) row[8]);
            }
        }
        printDivider();
    }

    public void topSniperPlayers() {
        printHeader("Топ-10 снайперов");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("SELECT p.nickname, s.sniper, s.firepower, s.clutching, t.teamName " +
                    "FROM Player p " +
                    "JOIN Statistics s ON s.player = p " +
                    "JOIN Contract c ON c.player = p " +
                    "JOIN Team t ON c.team = t " +
                    "WHERE s.sniper >= 70 AND p.status = 'ACTIVE' AND c.finalDate > CURRENT_DATE " +
                    "ORDER BY s.sniper DESC, s.firepower DESC ", Object[].class).setMaxResults(10).getResultList();
            System.out.printf("     %-4s %-18s %-10s %-10s %-12s %-25s%n",
                    "#", "Никнейм", "Снайпер", "Огневая", "Клатчинг", "Команда");
            System.out.println("     " + "-".repeat(83));
            int rank = 1;
            for (Object[] row : results) {
                System.out.printf("     %-4d %-18s %-10d %-10d %-12d %-25s%n", rank++, truncate((String) row[0], 17), (Integer) row[1], (Integer) row[2], (Integer) row[3], truncate((String) row[4], 24));
            }
        }
        printDivider();
    }

    public void freePlayers() {
        printHeader("Свободные игроки");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("SELECT p.nickname, p.role, p.status, " +
                    "(s.firepower + s.opening + s.clutching + s.sniper + s.utility + s.trading) AS totalRating " +
                    "FROM Player p " +
                    "JOIN Statistics s ON s.player = p " +
                    "WHERE p NOT IN (SELECT c.player FROM Contract c WHERE c.finalDate > CURRENT_DATE) AND p.status = 'ACTIVE' " +
                    "ORDER BY totalRating DESC ", Object[].class).setMaxResults(20).getResultList();
            System.out.printf("     %-20s %-15s %-10s %-15s%n", "Никнейм", "Роль", "Статус", "Общий рейтинг");
            System.out.println("     " + "─".repeat(64));
            for (Object[] row : results) {
                System.out.printf("     %-20s %-15s %-10s %-15d%n", truncate((String) row[0], 19), truncate((String) row[1], 14), row[2], (Integer) row[3]);
            }
        }
        printDivider();
    }

    public void getRecentMatches() {
        printHeader("Последние 5 матчей");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery(" SELECT m.tournament.tournamentName, " +
                    "m.team1.teamName || ' vs ' || m.team2.teamName, " +
                    "m.scoreTeam1 || ':' || m.scoreTeam2, m.winnerTeam, m.startDate " +
                "FROM Match m " +
                "ORDER BY m.startDate DESC", Object[].class).setMaxResults(5).getResultList();
            System.out.printf("     %-20s %-30s %-10s %-20s %-20s%n",
                    "Турнир", "Матч", "Счет", "Победитель", "Дата");
            System.out.println("     " + "─".repeat(104));
            for (Object[] row : results) {
                System.out.printf("     %-20s %-30s %-10s %-20s %-20s%n", truncate((String) row[0], 19), truncate((String) row[1], 29), row[2], row[3] != null ? truncate((String) row[3], 19) : "—", row[4] != null ? row[4].toString().substring(0, 16) : "—");
            }
        }
        printDivider();
    }
    public void runAll() {
        getTeamsWithPlayers();
        getPlayersWithStats();
        topSniperPlayers();
        freePlayers();
        getRecentMatches();
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println("╔" + "═".repeat(title.length() + 4) + "╗");
        System.out.println("║  " + title + "  ║");
        System.out.println("╚" + "═".repeat(title.length() + 4) + "╝");
    }

    private void printDivider() {
        System.out.println("─".repeat(120));
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max) + "…" : s;
    }
}
