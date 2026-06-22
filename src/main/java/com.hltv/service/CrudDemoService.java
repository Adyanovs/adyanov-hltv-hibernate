package com.hltv.service;

import com.hltv.Entity.*;
import com.hltv.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CrudDemoService {

    private final TournamentRepository tournamentRepo = new TournamentRepository();
    private final TeamRepository teamRepo = new TeamRepository();
    private final StatisticsRepository statisticsRepo = new StatisticsRepository();
    private final PlayerRepository playerRepo = new PlayerRepository();
    private final MatchRepository matchRepo = new MatchRepository();
    private final ContractRepository contractRepo = new ContractRepository();

    public void demoCreate() {
        printHeader("CREATE - Создание записей");
        Team team1 = teamRepo.save(new Team("ZXC", "logotype", "Japan", 52));
        System.out.printf("Создана команда: name=%s, logotype=%s, country=%s, rank=%d%n",
                team1.getTeamName(), team1.getLogotype(), team1.getCountry(), team1.getRank());

        Team team2 = teamRepo.save(new Team("HELLO", "logotype", "Russia", 67));
        System.out.printf("Создана команда: name=%s, logotype=%s, country=%s, rank=%d%n",
                team2.getTeamName(), team2.getLogotype(), team2.getCountry(), team2.getRank());

        Player player = playerRepo.save(new Player("mESSI", "@messi", "PLAYER", "ACTIVE"));
        System.out.printf("Создан игрок: nickname=%s, social media=%s, role=%s, status=%s%n", player.getNickname(), player.getSocialNetworkingServices(), player.getRole(), player.getStatus());

        Tournament tournament = tournamentRepo.save(new Tournament("World Cup 2007", 1500000, "VALVE", "VALVE REGULATIONS"));
        System.out.printf("Создан турнир: название=%s, prize_pool=%d, sponsors=%s, regulations=%s%n", tournament.getTournamentName(), tournament.getPrizePool(), tournament.getSponsors(), tournament.getRegulations());

        Statistics statistics = statisticsRepo.save(new Statistics(player, 99, 93, 90, 100, 93, 92));
        System.out.printf("Создана статистика: id=%d, nickname=%s, firepower=%d, opening=%d, clutching=%d, sniper=%d, utility=%d, trading=%d%n",
                statistics.getIdStat(), statistics.getPlayer().getNickname(), statistics.getFirepower(), statistics.getOpening(), statistics.getClutching(), statistics.getSniper(), statistics.getUtility(), statistics.getTrading());

        Contract contract = contractRepo.save(new Contract(team1, player, LocalDate.of(2025, 4, 6), LocalDate.of(2027, 4, 6)
        ));
        System.out.printf("Создан контракт: id=%d, team=%s, nickname=%s, %tF - %tF%n", contract.getIdContract(), contract.getTeam().getTeamName(), contract.getPlayer().getNickname(), contract.getStartDate(), contract.getFinalDate());

        Team team3 = teamRepo.save(new Team("QWE", "logotype", "Russia", 10));
        Match match = matchRepo.save(new Match(tournament, team1, team3, LocalDateTime.of(2026, 4, 12, 17, 0), 13, 10, "twitch"));
        System.out.printf("Создан матч: id=%d, tournament=%s, %s : %d - %s : %d, %tF, winner=%s, stream=%s%n",
                match.getIdMatch(), match.getTournament().getTournamentName(), match.getTeam1().getTeamName(), match.getScoreTeam1(), match.getTeam2().getTeamName(), match.getScoreTeam2(), match.getStartDate(), match.getWinnerTeam(), match.getStreamLink());
        printDivider();
    }

    public void demoRead() {
        printHeader("READ - Чтение данных");
        System.out.println("Все турниры:");
        List<Tournament> tournaments = tournamentRepo.findAll();
        System.out.printf("     %-25s %-20s %-16s %-25s%n", "Название", "Призовой фонд", "Спонсоры", "Правила");
        System.out.println("     " + "-".repeat(88));
        for (Tournament t : tournaments) {
            System.out.printf("     %-25s %-20d %-16s %-25s%n", t.getTournamentName(), t.getPrizePool(), t.getSponsors() != null ? t.getSponsors() : "-",
                    t.getRegulations() != null ? t.getRegulations() : "-");
        }
        System.out.println("\nВсе команды:");
        List<Team> teams = teamRepo.findAll();
        System.out.printf("     %-25s %-20s %-16s %-10s%n", "Название", "Логотип", "Регион", "Ранг");
        System.out.println("     " + "-".repeat(73));
        for (Team t : teams) {
            System.out.printf("     %-25s %-20s %-16s %-10d%n", t.getTeamName(), t.getLogotype() != null ? t.getLogotype() : "-",
                    t.getCountry() != null ? t.getCountry() : "-", t.getRank());
        }

        System.out.println("\nВсе игроки:");
        List<Player> players = playerRepo.findAll();
        System.out.printf("     %-10s %-20s %-16s %-10s%n", "Никнейм", "Соц. сети", "Роль", "Статус");
        System.out.println("     " + "-".repeat(58));
        for (Player p : players) {
            System.out.printf("     %-10s %-20s %-16s %-10s%n", p.getNickname(),p.getSocialNetworkingServices() != null ? p.getSocialNetworkingServices() : "-", p.getRole(), p.getStatus());
        }

        System.out.println("\nВсе контракты:");
        List<Contract> contracts = contractRepo.findAllWithDetails();
        System.out.printf("     %-5s %-20s %-16s %-16s %-16s%n", "ID", "Команда", "Игрок", "Начало", "Конец");
        System.out.println("     " + "-".repeat(77));
        for (Contract c : contracts) {
            System.out.printf("     %-5d %-20s %-16s %-16tF %-16tF%n", c.getIdContract(), c.getTeam().getTeamName(), c.getPlayer().getNickname(), c.getStartDate(), c.getFinalDate());
        }

        System.out.println("\nВсе матчи:");
        List<Match> matches = matchRepo.findAllWithDetails();
        System.out.printf("     %-5s %-20s %-10s %-20s %-10s %-15s %-15s %-15s%n",
                "ID", "Команда 1", "Очки", "Команда 2", "Очки", "Дата", "Победитель", "Трансляция");
        System.out.println("     " + "-".repeat(114));
        for (Match m : matches) {
            System.out.printf("     %-5d %-20s %-10d %-20s %-10d %-15tF %-15s %-15s%n",
                    m.getIdMatch(), m.getTeam1().getTeamName(), m.getScoreTeam1(), m.getTeam2().getTeamName(), m.getScoreTeam2(), m.getStartDate(), m.getWinnerTeam() != null ? m.getWinnerTeam() : "-", m.getStreamLink() != null ? m.getStreamLink() : "-");
        }

        System.out.println("\nПоиск игрока по никнейму 'mESSI':");
        playerRepo.findByNickname("mESSI").ifPresentOrElse(
                p -> System.out.println("     " + p),
                () -> System.out.println("     Не найден")
        );

        System.out.println("\nПоиск команды по названию 'ZXC':");
        teamRepo.findByTeamName("ZXC").ifPresentOrElse(
                t -> System.out.println("     " + t),
                () -> System.out.println("     Не найдена")
        );

        System.out.println("\nПоиск турнира по названию 'World Cup 2007':");
        tournamentRepo.findByTournamentName("World Cup 2007").ifPresentOrElse(
                t -> System.out.println("     " + t),
                () -> System.out.println("     Не найден")
        );
        printDivider();
    }

    public void demoUpdate() {
        printHeader("UPDATE - Обновление данных");
        playerRepo.findByNickname("mESSI").ifPresent(player -> {
            String oldStatus = player.getStatus();
            player.setStatus("INACTIVE");
            Player updated = playerRepo.update(player);
            System.out.printf("  Обновлён статус игрока 'mESSI': '%s' → '%s'%n", oldStatus, updated.getStatus());
            updated.setStatus(oldStatus);
            playerRepo.update(updated);
        });

        teamRepo.findByTeamName("ZXC").ifPresent(team -> {
            int oldRank = team.getRank();
            team.setRank(56);
            Team updated = teamRepo.update(team);
            System.out.printf("  Обновлён ранг команды 'ZXC': %d → %d%n", oldRank, updated.getRank());updated.setRank(oldRank);
            teamRepo.update(updated);
        });

        tournamentRepo.findByTournamentName("World Cup 2007").ifPresent(tournament -> {
            int oldPrize = tournament.getPrizePool();
            tournament.setPrizePool(2000000);
            Tournament updated = tournamentRepo.update(tournament);
            System.out.printf("  Обновлён призовой фонд турнира 'World Cup 2007': %d → %d%n", oldPrize, updated.getPrizePool());
            updated.setPrizePool(oldPrize);
            tournamentRepo.update(updated);
        });

        statisticsRepo.findByPlayerNickname("mESSI").ifPresent(stats -> {
            int oldSniper = stats.getSniper();
            stats.setSniper(100);
            Statistics updated = statisticsRepo.update(stats);
            System.out.printf("  Обновлена статистика sniper для 'mESSI': %d → %d%n", oldSniper, updated.getSniper());
            updated.setSniper(oldSniper);
            statisticsRepo.update(updated);
        });
        printDivider();
    }

    public void demoDelete() {
        printHeader("DELETE - Удаление данных");

        Player temp = playerRepo.save(new Player("Удали Меня",
                "@pupupu", "PLAYER", "ACTIVE"));
        System.out.printf("Создан временный игрок: nickname=%s%n", temp.getNickname());

        boolean deleted = playerRepo.deleteById(temp.getNickname());
        System.out.printf("  Удалён игрок '%s' (успех=%b)%n", temp.getNickname(), deleted);

        boolean notFound = playerRepo.deleteById("АДЬЯНОВ");
        System.out.printf("  Удаление несуществующего игрока (успех=%b)%n", notFound);
        printDivider();
    }

    public void demoTransaction() {
        printHeader("TRANSACTION - Создание матча с транзакцией");
        System.out.println("Создание матча: Team1=ZXC, Team2=QWE, Турнир=World Cup 2007");
        try {
            Team team1 = teamRepo.findByTeamName("ZXC").orElseThrow(() -> new RuntimeException("Team1 not found"));
            Team team2 = teamRepo.findByTeamName("QWE").orElseThrow(() -> new RuntimeException("Team2 not found"));
            Tournament tournament = tournamentRepo.findByTournamentName("World Cup 2007").orElseThrow(() -> new RuntimeException("Tournament not found"));

            Match match = matchRepo.save(new Match(tournament, team1, team2, LocalDateTime.of(2026, 5, 1, 20, 0), 16, 12, "https://twitch.tv/match"));
            System.out.printf("Матч создан! id=%d, %s %d:%d %s%n",
                    match.getIdMatch(), match.getTeam1().getTeamName(), match.getScoreTeam1(), match.getScoreTeam2(), match.getTeam2().getTeamName());

            System.out.println("\nПовторное создание матча с теми же командами...");
            try {
                matchRepo.save(new Match(tournament, team1, team2, LocalDateTime.of(2026, 5, 1, 20, 0), 16, 12, "https://twitch.tv/match"));
            } catch (Exception e) {
                System.out.printf("  Ошибка при создании дубликата: %s%n", e.getMessage());
            }

            matchRepo.deleteById(match.getIdMatch());
            System.out.printf("Матч id=%d удален%n", match.getIdMatch());

        } catch (Exception e) {
            System.out.printf("  Ошибка: %s%n", e.getMessage());
        }
        printDivider();
    }

    public void runAll() {
        demoCreate();
        demoRead();
        demoUpdate();
        demoDelete();
        demoTransaction();
    }

    public static void printHeader(String title) {
        System.out.println();
        System.out.println("╔" + "═".repeat(title.length() + 4) + "╗");
        System.out.println("║  " + title + "  ║");
        System.out.println("╚" + "═".repeat(title.length() + 4) + "╝");
    }

    public static void printDivider() {
        System.out.println("-".repeat(80));
    }
}