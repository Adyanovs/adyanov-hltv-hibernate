import com.hltv.Entity.*;
import com.hltv.repository.*;
import com.hltv.service.BusinessService;
import com.hltv.service.CrudDemoService;
import com.hltv.util.DataSeeder;
import com.hltv.util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PlayerRepository playerRepo = new PlayerRepository();
    private static final TeamRepository teamRepo = new TeamRepository();
    private static final TournamentRepository tournamentRepo = new TournamentRepository();
    private static final StatisticsRepository statisticsRepo = new StatisticsRepository();
    private static final ContractRepository contractRepo = new ContractRepository();
    private static final MatchRepository matchRepo = new MatchRepository();
    private static final BusinessService businessService = new BusinessService();
    private static final CrudDemoService crudDemoService = new CrudDemoService();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy"); //интересная штука, запомнить
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static void main(String[] args) {
        DataSeeder.seed();

        System.out.println("==================================================");
        System.out.println("       ДОБРО ПОЖАЛОВАТЬ В HLTV MANAGEMENT");
        System.out.println("==================================================");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showPlayersMenu();
                case "2" -> showTeamsMenu();
                case "3" -> showTournamentsMenu();
                case "4" -> showMatchesMenu();
                case "5" -> showContractsMenu();
                case "6" -> showStatisticsMenu();
                case "7" -> runCustomBusinessQueries();
                case "8" -> crudDemoService.runAll();
                case "0" -> {
                    System.out.println("\nДо свидания!");
                    HibernateUtil.close();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("ГЛАВНОЕ МЕНЮ");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Игроки");
        System.out.println("2. Команды");
        System.out.println("3. Турниры");
        System.out.println("4. Матчи");
        System.out.println("5. Контракты");
        System.out.println("6. Статистика");
        System.out.println("7. Бизнес-запросы");
        System.out.println("8. Демонстрация CRUD");
        System.out.println("0. Выход");
        System.out.print("\nВаш выбор: ");
    }

    // ==================== ИГРОКИ ====================
    private static void showPlayersMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ ИГРОКАМИ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Список всех игроков");
            System.out.println("2. Найти игрока по никнейму");
            System.out.println("3. Найти по роли");
            System.out.println("4. Найти по статусу");
            System.out.println("5. Найти по команде");
            System.out.println("6. Добавить игрока");
            System.out.println("7. Обновить игрока");
            System.out.println("8. Удалить игрока");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printPlayers(playerRepo.findAll());
                case "2" -> findPlayerByNickname();
                case "3" -> findPlayersByRole();
                case "4" -> findPlayersByStatus();
                case "5" -> findPlayersByTeam();
                case "6" -> addPlayer();
                case "7" -> updatePlayer();
                case "8" -> deletePlayer();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findPlayerByNickname() {
        System.out.print("Введите никнейм: ");
        String nickname = scanner.nextLine().trim();
        playerRepo.findByNickname(nickname).ifPresentOrElse(
                p -> System.out.println("Найден: " + p),
                () -> System.out.println("Игрок не найден")
        );
    }

    private static void findPlayersByRole() {
        System.out.print("Введите роль: ");
        String role = scanner.nextLine().trim();
        printPlayers(playerRepo.findByRole(role));
    }

    private static void findPlayersByStatus() {
        System.out.print("Введите статус (ACTIVE/INACTIVE): ");
        String status = scanner.nextLine().trim().toUpperCase();
        printPlayers(playerRepo.findByStatus(status));
    }

    private static void findPlayersByTeam() {
        System.out.print("Введите название команды: ");
        String teamName = scanner.nextLine().trim();
        printPlayers(playerRepo.findByTeamName(teamName));
    }

    private static void addPlayer() {
        System.out.println("Добавление игрока:");
        System.out.print("Никнейм: ");
        String nickname = scanner.nextLine().trim();
        if (playerRepo.existsById(nickname)) {
            System.out.println("Игрок с таким никнеймом уже существует!");
            return;
        }
        System.out.print("Соц. сети (или '-' для пропуска): ");
        String social = scanner.nextLine().trim();
        if (social.equals("-")) social = null;
        System.out.print("Роль: ");
        String role = scanner.nextLine().trim();
        System.out.print("Статус (ACTIVE/INACTIVE): ");
        String status = scanner.nextLine().trim().toUpperCase();

        Player player = new Player(nickname, social, role, status);
        playerRepo.save(player);
        System.out.println("Игрок добавлен!");
    }

    private static void updatePlayer() {
        System.out.print("Введите никнейм игрока для обновления: ");
        String nickname = scanner.nextLine().trim();
        Optional<Player> opt = playerRepo.findByNickname(nickname);
        if (opt.isEmpty()) {
            System.out.println("Игрок не найден");
            return;
        }
        Player p = opt.get();
        System.out.println("Текущие данные: " + p);
        System.out.print("Новый статус (ACTIVE/INACTIVE, Enter - пропустить): ");
        String status = scanner.nextLine().trim();
        if (!status.isEmpty()) p.setStatus(status.toUpperCase());
        System.out.print("Новая роль (Enter - пропустить): ");
        String role = scanner.nextLine().trim();
        if (!role.isEmpty()) p.setRole(role);
        System.out.print("Новые соц. сети (Enter - пропустить): ");
        String social = scanner.nextLine().trim();
        if (!social.isEmpty()) p.setSocialNetworkingServices(social.equals("-") ? null : social);

        playerRepo.update(p);
        System.out.println("Игрок обновлён!");
    }

    private static void deletePlayer() {
        System.out.print("Введите никнейм игрока для удаления: ");
        String nickname = scanner.nextLine().trim();
        Optional<Contract> contractOpt = contractRepo.findByPlayerNickname(nickname);
        if (contractOpt.isPresent()) {
            System.out.print("У игрока есть контракт. Удалить контракт тоже? (д/н): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
                contractRepo.deleteContract(nickname);
                System.out.println("Контракт удалён");
            } else {
                System.out.println("Удаление отменено (игрок состоит в контракте)");
                return;
            }
        }
        Optional<Statistics> stats = statisticsRepo.findByPlayerNickname(nickname);
        if (stats.isPresent()) {
            System.out.print("У игрока есть статистика. Удалить статистику тоже? (д/н): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
                statisticsRepo.deleteByPlayer(nickname);
                System.out.println("Статистика удалена");
            } else {
                System.out.println("Удаление отменено (у игрока есть статистика)");
                return;
            }
        }

        // 3. Удаляем игрока
        boolean deleted = playerRepo.deleteById(nickname);
        System.out.println(deleted ? "Игрок удалён" : "Ошибка при удалении");
    }

    private static void printPlayers(List<Player> players) {
        if (players.isEmpty()) {
            System.out.println("Игроки не найдены");
            return;
        }
        System.out.println("Найдено игроков: " + players.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s | %-20s | %-15s | %-10s%n", "Никнейм", "Соц. сети", "Роль", "Статус");
        System.out.println("--------------------------------------------------");
        for (Player p : players) {
            System.out.printf("%-20s | %-20s | %-15s | %-10s%n", p.getNickname(),
                    p.getSocialNetworkingServices() != null ? p.getSocialNetworkingServices() : "—",
                    p.getRole(), p.getStatus());
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== КОМАНДЫ ====================
    private static void showTeamsMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ КОМАНДАМИ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Список всех команд");
            System.out.println("2. Найти команду по названию");
            System.out.println("3. Найти по стране");
            System.out.println("4. Топ-N команд по рангу");
            System.out.println("5. Добавить команду");
            System.out.println("6. Обновить команду");
            System.out.println("7. Удалить команду");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printTeams(teamRepo.getAllTeams());
                case "2" -> findTeamByName();
                case "3" -> findTeamsByCountry();
                case "4" -> findTopTeams();
                case "5" -> addTeam();
                case "6" -> updateTeam();
                case "7" -> deleteTeam();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findTeamByName() {
        System.out.print("Введите название команды: ");
        String name = scanner.nextLine().trim();
        teamRepo.findByTeamName(name).ifPresentOrElse(
                t -> System.out.println("Найдена: " + t),
                () -> System.out.println("Команда не найдена")
        );
    }

    private static void findTeamsByCountry() {
        System.out.print("Введите страну: ");
        String country = scanner.nextLine().trim();
        printTeams(teamRepo.findByCountry(country));
    }

    private static void findTopTeams() {
        System.out.print("Количество команд: ");
        int limit = Integer.parseInt(scanner.nextLine().trim());
        printTeams(teamRepo.findTopRanked(limit));
    }

    private static void addTeam() {
        System.out.println("Добавление команды:");
        System.out.print("Название: ");
        String name = scanner.nextLine().trim();
        if (teamRepo.existsById(name)) {
            System.out.println("Команда с таким названием уже существует");
            return;
        }
        System.out.print("Логотип (или '-'): ");
        String logo = scanner.nextLine().trim();
        if (logo.equals("-")) logo = null;
        System.out.print("Страна: ");
        String country = scanner.nextLine().trim();
        System.out.print("Ранг: ");
        int rank = Integer.parseInt(scanner.nextLine().trim());
        if(teamRepo.findByRank(rank)){
            System.out.println("команда с таким рангом уже существует");
            return;
        }

        Team team = new Team(name, logo, country, rank);
        teamRepo.save(team);
        System.out.println("Команда добавлена!");
    }

    private static void updateTeam() {
        System.out.print("Введите название команды: ");
        String name = scanner.nextLine().trim();
        Optional<Team> opt = teamRepo.findByTeamName(name);
        if (opt.isEmpty()) {
            System.out.println("Команда не найдена");
            return;
        }
        Team t = opt.get();
        System.out.println("Текущие данные: " + t);
        System.out.print("Новый ранг (Enter - пропустить): ");
        String rankStr = scanner.nextLine().trim();
        if (!rankStr.isEmpty()) teamRepo.updateRank(t.getTeamName(),Integer.parseInt(rankStr));
        System.out.print("Новая страна (Enter - пропустить): ");
        String country = scanner.nextLine().trim();
        if (!country.isEmpty()) t.setCountry(country);

        teamRepo.update(t);
        System.out.println("Команда обновлена!");
    }

    private static void deleteTeam() {
        System.out.print("Введите название команды: ");
        String name = scanner.nextLine().trim();
        if (!teamRepo.existsById(name)) {
            System.out.println("Команда не найдена");
            return;
        }
        List<Contract> contracts = contractRepo.findByTeamName(name);
        if (!contracts.isEmpty()) {
            System.out.print("У команды есть " + contracts.size() + " контрактов. Удалить всё? (д/н): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
                for (Contract c : contracts) {
                    contractRepo.deleteById(c.getIdContract());
                }
                System.out.println("Контракты удалены");
            } else {
                System.out.println("Удаление отменено");
                return;
            }
        }
        List<Match> matches = matchRepo.findByTeamName(name);
        if (!matches.isEmpty()) {
            System.out.print("У команды есть " + matches.size() + " матчей. Удалить всё? (д/н): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
                for (Match m : matches) {
                    matchRepo.deleteById(m.getIdMatch());
                }
                System.out.println("Матчи удалены");
            } else {
                System.out.println("Удаление отменено (есть матчи)");
                return;
            }
        }
        boolean deleted = teamRepo.deleteById(name);
        System.out.println(deleted ? "Команда удалена" : "Ошибка при удалении");
    }

    private static void printTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            System.out.println("Команды не найдены");
            return;
        }
        System.out.println("Найдено команд: " + teams.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-25s | %-15s | %-20s | %-10s%n", "Название", "Страна", "Логотип", "Ранг");
        System.out.println("--------------------------------------------------");
        for (Team t : teams) {
            System.out.printf("%-25s | %-15s | %-20s | %-10d%n", t.getTeamName(), t.getCountry() != null ? t.getCountry() : "-",
                    t.getLogotype() != null ? t.getLogotype() : "—", t.getRank());
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== ТУРНИРЫ ====================
    private static void showTournamentsMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ ТУРНИРАМИ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Список всех турниров");
            System.out.println("2. Найти турнир по названию");
            System.out.println("3. Добавить турнир");
            System.out.println("4. Обновить турнир");
            System.out.println("5. Удалить турнир");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printTournaments(tournamentRepo.findAll());
                case "2" -> findTournamentByName();
                case "3" -> addTournament();
                case "4" -> updateTournament();
                case "5" -> deleteTournament();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findTournamentByName() {
        System.out.print("Введите название турнира: ");
        String name = scanner.nextLine().trim();
        tournamentRepo.findByTournamentName(name).ifPresentOrElse(
                t -> System.out.println("Найден: " + t),
                () -> System.out.println("Турнир не найден")
        );
    }

    private static void addTournament() {
        System.out.println("Добавление турнира:");
        System.out.print("Название: ");
        String name = scanner.nextLine().trim();
        if (tournamentRepo.existsById(name)) {
            System.out.println("Турнир с таким названием уже существует");
            return;
        }
        System.out.print("Призовой фонд: ");
        int prize = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Спонсоры (или '-'): ");
        String sponsors = scanner.nextLine().trim();
        if (sponsors.equals("-")) sponsors = null;
        System.out.print("Регламент (или '-'): ");
        String regulations = scanner.nextLine().trim();
        if (regulations.equals("-")) regulations = null;

        Tournament tournament = new Tournament(name, prize, sponsors, regulations);
        tournamentRepo.save(tournament);
        System.out.println("Турнир добавлен");
    }

    private static void updateTournament() {
        System.out.print("Введите название турнира: ");
        String name = scanner.nextLine().trim();
        Optional<Tournament> opt = tournamentRepo.findByTournamentName(name);
        if (opt.isEmpty()) {
            System.out.println("Турнир не найден");
            return;
        }
        Tournament t = opt.get();
        System.out.println("Текущие данные: " + t);
        System.out.print("Новый призовой фонд (Enter - пропустить): ");
        String prizeStr = scanner.nextLine().trim();
        if (!prizeStr.isEmpty()) t.setPrizePool(Integer.parseInt(prizeStr));
        System.out.print("Новые спонсоры (Enter - пропустить): ");
        String sponsors = scanner.nextLine().trim();
        if (!sponsors.isEmpty()) t.setSponsors(sponsors.equals("-") ? null : sponsors);

        tournamentRepo.update(t);
        System.out.println("Турнир обновлён");
    }

    private static void deleteTournament() {
        System.out.print("Введите название турнира: ");
        String name = scanner.nextLine().trim();
        long matchCount = matchRepo.countMatchesByTournament(name);
        if (matchCount > 0) {
            System.out.print("У турнира есть " + matchCount + " матчей. Удалить всё? (д/н): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
                List<Match> matches = matchRepo.findByTournamentName(name);
                for (Match m : matches) {
                    matchRepo.deleteById(m.getIdMatch());
                }
                System.out.println("Матчи удалены");
            } else {
                System.out.println("Удаление отменено");
                return;
            }
        }
        boolean deleted = tournamentRepo.deleteById(name);
        System.out.println(deleted ? "Турнир удалён" : "Ошибка при удалении");
    }

    private static void printTournaments(List<Tournament> tournaments) {
        if (tournaments.isEmpty()) {
            System.out.println("Турниры не найдены");
            return;
        }
        System.out.println("Найдено турниров: " + tournaments.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-30s | %-15s | %-20s | %-30s%n", "Название", "Призовой фонд", "Спонсоры", "Регламент");
        System.out.println("--------------------------------------------------");
        for (Tournament t : tournaments) {
            System.out.printf("%-30s | %-15d | %-20s | %-30s%n", t.getTournamentName(), t.getPrizePool(), t.getSponsors() != null ? t.getSponsors() : "—", t.getRegulations() != null ? t.getRegulations() : "—");
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== МАТЧИ ====================
    private static void showMatchesMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ МАТЧАМИ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Список всех матчей");
            System.out.println("2. Найти матч по ID");
            System.out.println("3. Найти по турниру");
            System.out.println("4. Найти по команде");
            System.out.println("5. Добавить матч");
            System.out.println("6. Удалить матч");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printMatches(matchRepo.findAll());
                case "2" -> findMatchById();
                case "3" -> findMatchesByTournament();
                case "4" -> findMatchesByTeam();
                case "5" -> addMatch();
                case "6" -> deleteMatch();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findMatchById() {
        System.out.print("Введите ID матча: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        matchRepo.findByIdWithDetails(id).ifPresentOrElse(
                m -> System.out.println("Найден: " + m),
                () -> System.out.println("Матч не найден")
        );
    }

    private static void findMatchesByTournament() {
        System.out.print("Введите название турнира: ");
        String name = scanner.nextLine().trim();
        printMatches(matchRepo.findByTournamentName(name));
    }

    private static void findMatchesByTeam() {
        System.out.print("Введите название команды: ");
        String name = scanner.nextLine().trim();
        printMatches(matchRepo.findByTeamName(name));
    }

    private static void addMatch() {
        System.out.println("Добавление матча:");
        System.out.print("Название турнира: ");
        String tournamentName = scanner.nextLine().trim();
        Optional<Tournament> tournamentOpt = tournamentRepo.findByTournamentName(tournamentName);
        if (tournamentOpt.isEmpty()) {
            System.out.println("Турнир не найден");
            return;
        }

        System.out.print("Команда 1: ");
        String team1Name = scanner.nextLine().trim();
        Optional<Team> team1Opt = teamRepo.findByTeamName(team1Name);
        if (team1Opt.isEmpty()) {
            System.out.println("Команда 1 не найдена");
            return;
        }

        System.out.print("Команда 2: ");
        String team2Name = scanner.nextLine().trim();
        Optional<Team> team2Opt = teamRepo.findByTeamName(team2Name);
        if (team2Opt.isEmpty()) {
            System.out.println("Команда 2 не найдена");
            return;
        }

        System.out.print("Дата и время (dd.MM.yyyy HH:mm): ");
        LocalDateTime date = LocalDateTime.parse(scanner.nextLine().trim(), DATETIME_FORMATTER);
        System.out.print("Счёт команды 1: ");
        int score1 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Счёт команды 2: ");
        int score2 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ссылка на стрим (или '-'): ");
        String stream = scanner.nextLine().trim();
        if (stream.equals("-")) stream = null;

        Match match = new Match(tournamentOpt.get(), team1Opt.get(), team2Opt.get(),
                date, score1, score2, stream);
        matchRepo.save(match);
        System.out.println("Матч добавлен");
    }

    private static void deleteMatch() {
        System.out.print("Введите ID матча: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        boolean deleted = matchRepo.deleteById(id);
        System.out.println(deleted ? "Матч удалён" : "Ошибка при удалении");
    }

    private static void printMatches(List<Match> matches) {
        if (matches.isEmpty()) {
            System.out.println("Матчи не найдены");
            return;
        }
        System.out.println("Найдено матчей: " + matches.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-20s | %-10s | %-20s | %-20s%n",
                "ID", "Команда 1", "Команда 2", "Счёт", "Дата", "Победитель");
        System.out.println("--------------------------------------------------");
        for (Match m : matches) {
            System.out.printf("%-5d | %-20s | %-20s | %-10s | %-20s | %-20s%n", m.getIdMatch(), m.getTeam1().getTeamName(), m.getTeam2().getTeamName(), m.getScoreTeam1() + ":" + m.getScoreTeam2(), m.getStartDate().format(DATETIME_FORMATTER), m.getWinnerTeam() != null ? m.getWinnerTeam() : "—");
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== КОНТРАКТЫ ====================
    private static void showContractsMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ КОНТРАКТАМИ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Список всех контрактов");
            System.out.println("2. Найти по игроку");
            System.out.println("3. Найти по команде");
            System.out.println("4. Добавить контракт");
            System.out.println("5. Продлить контракт");
            System.out.println("6. Расторгнуть контракт");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printContracts(contractRepo.findAll());
                case "2" -> findContractByPlayer();
                case "3" -> findContractsByTeam();
                case "4" -> addContract();
                case "5" -> extendContract();
                case "6" -> deleteContract();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findContractByPlayer() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        contractRepo.findByPlayerNickname(nickname).ifPresentOrElse(
                c -> System.out.println("Найден: " + c),
                () -> System.out.println("Контракт не найден")
        );
    }

    private static void findContractsByTeam() {
        System.out.print("Введите название команды: ");
        String name = scanner.nextLine().trim();
        printContracts(contractRepo.findByTeamName(name));
    }

    private static void addContract() {
        System.out.println("Добавление контракта:");
        System.out.print("Никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        Optional<Player> playerOpt = playerRepo.findByNickname(nickname);
        if (playerOpt.isEmpty()) {
            System.out.println("Игрок не найден");
            return;
        }
        Optional<Contract> contractOpt = contractRepo.findByPlayerNickname(nickname);
        if(contractOpt.isPresent()) {
            System.out.println("У игрока уже есть контракт");
            return;
        }

        System.out.print("Название команды: ");
        String teamName = scanner.nextLine().trim();
        Optional<Team> teamOpt = teamRepo.findByTeamName(teamName);
        if (teamOpt.isEmpty()) {
            System.out.println("Команда не найдена");
            return;
        }

        System.out.print("Дата начала (dd.MM.yyyy): ");
        LocalDate start = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
        System.out.print("Дата окончания (dd.MM.yyyy): ");
        LocalDate end = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);

        Contract contract = new Contract(teamOpt.get(), playerOpt.get(), start, end);
        contractRepo.save(contract);
        System.out.println("Контракт добавлен");
    }

    private static void extendContract() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        Optional<Contract> contractOpt = contractRepo.findByPlayerNickname(nickname);
        if(contractOpt.isEmpty()) {
            System.out.println("Контракта с таким игроком не существует");
            return;
        }
        System.out.print("Новая дата окончания (dd.MM.yyyy): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
        contractRepo.extendContract(nickname, date);
        System.out.println("Контракт продлён");
    }

    private static void deleteContract() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        Optional<Contract> contractOpt = contractRepo.findByPlayerNickname(nickname);
        if(contractOpt.isEmpty()) {
            System.out.println("Контракта с таким игроком не существует");
            return;
        }
        contractRepo.deleteContract(nickname);
        System.out.println("Контракт расторгнут");
    }

    private static void printContracts(List<Contract> contracts) {
        if (contracts.isEmpty()) {
            System.out.println("Контракты не найдены");
            return;
        }
        System.out.println("Найдено контрактов: " + contracts.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-20s | %-12s | %-12s%n",
                "ID", "Команда", "Игрок", "Начало", "Окончание");
        System.out.println("--------------------------------------------------");
        for (Contract c : contracts) {
            System.out.printf("%-5d | %-20s | %-20s | %-12s | %-12s%n",
                    c.getIdContract(),
                    c.getTeam().getTeamName(),
                    c.getPlayer().getNickname(),
                    c.getStartDate().format(DATE_FORMATTER),
                    c.getFinalDate().format(DATE_FORMATTER));
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== СТАТИСТИКА ====================
    private static void showStatisticsMenu() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("УПРАВЛЕНИЕ СТАТИСТИКОЙ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Статистика всех игроков");
            System.out.println("2. Статистика игрока");
            System.out.println("3. Топ по огневой мощи");
            System.out.println("4. Топ по снайперу");
            System.out.println("5. Топ по клатчингу");
            System.out.println("6. Добавить статистику");
            System.out.println("7. Обновить статистику");
            System.out.println("8. Удалить статистику");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printStatistics(statisticsRepo.findAllWithPlayers());
                case "2" -> findStatisticsByPlayer();
                case "3" -> printStatistics(statisticsRepo.findTopByFirepower(10));
                case "4" -> printStatistics(statisticsRepo.findTopBySniper(10));
                case "5" -> printStatistics(statisticsRepo.findTopByClutching(10));
                case "6" -> addStatistics();
                case "7" -> updateStatistics();
                case "8" -> deleteStatistics();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void findStatisticsByPlayer() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        statisticsRepo.findWithPlayer(nickname).ifPresentOrElse(
                s -> System.out.println("Найдена: " + s),
                () -> System.out.println("Статистика не найдена")
        );
    }

    private static void addStatistics() {
        System.out.println("Добавление статистики:");
        System.out.print("Никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        Optional<Player> playerOpt = playerRepo.findByNickname(nickname);
        if (playerOpt.isEmpty()) {
            System.out.println("Игрок не найден");
            return;
        }
        if (statisticsRepo.findByPlayerNickname(nickname).isPresent()) {
            System.out.println("Статистика для этого игрока уже существует!");
            return;
        }

        System.out.print("Огневая мощь (0-100): ");
        int firepower = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Опенинг (0-100): ");
        int opening = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Клатчинг (0-100): ");
        int clutching = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Снайпер (0-100): ");
        int sniper = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ютилити (0-100): ");
        int utility = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Трейдинг (0-100): ");
        int trading = Integer.parseInt(scanner.nextLine().trim());

        Statistics stats = new Statistics(playerOpt.get(), firepower, opening, clutching, sniper, utility, trading);
        statisticsRepo.save(stats);
        System.out.println("Статистика добавлена");
    }

    private static void updateStatistics() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        Optional<Statistics> opt = statisticsRepo.findWithPlayer(nickname);
        if (opt.isEmpty()) {
            System.out.println("Статистика не найдена");
            return;
        }
        Statistics s = opt.get();
        System.out.println("Текущие данные: " + s);
        System.out.print("Новая огневая мощь (Enter - пропустить): ");
        String val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setFirepower(Integer.parseInt(val));
        System.out.print("Новый опенинг (Enter - пропустить): ");
        val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setOpening(Integer.parseInt(val));
        System.out.print("Новый клатчинг (Enter - пропустить): ");
        val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setClutching(Integer.parseInt(val));
        System.out.print("Новый снайпер (Enter - пропустить): ");
        val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setSniper(Integer.parseInt(val));
        System.out.print("Новый ютилити (Enter - пропустить): ");
        val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setUtility(Integer.parseInt(val));
        System.out.print("Новый трейдинг (Enter - пропустить): ");
        val = scanner.nextLine().trim();
        if (!val.isEmpty()) s.setTrading(Integer.parseInt(val));

        statisticsRepo.update(s);
        System.out.println("Статистика обновлена");
    }

    private static void deleteStatistics() {
        System.out.print("Введите никнейм игрока: ");
        String nickname = scanner.nextLine().trim();
        statisticsRepo.deleteByPlayer(nickname);
        System.out.println("Статистика удалена");
    }

    private static void printStatistics(List<Statistics> statsList) {
        if (statsList.isEmpty()) {
            System.out.println("Статистика не найдена");
            return;
        }
        System.out.println("Найдено записей: " + statsList.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s%n",
                "Игрок", "Огневая", "Опенинг", "Клатч", "Снайпер", "Ютил", "Трейд");
        System.out.println("--------------------------------------------------");
        for (Statistics s : statsList) {
            System.out.printf("%-20s | %-8d | %-8d | %-8d | %-8d | %-8d | %-8d%n",
                    s.getPlayer().getNickname(),
                    s.getFirepower(), s.getOpening(), s.getClutching(),
                    s.getSniper(), s.getUtility(), s.getTrading());
        }
        System.out.println("--------------------------------------------------");
    }

    // ==================== БИЗНЕС-ЗАПРОСЫ ====================
    private static void runCustomBusinessQueries() {
        while (true) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("БИЗНЕС-ЗАПРОСЫ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Команды и их игроки");
            System.out.println("2. Игроки со статистикой");
            System.out.println("3. Топ-10 снайперов");
            System.out.println("4. Свободные игроки");
            System.out.println("5. Последние 5 матчей");
            System.out.println("6. Все бизнес-запросы");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> businessService.getTeamsWithPlayers();
                case "2" -> businessService.getPlayersWithStats();
                case "3" -> businessService.topSniperPlayers();
                case "4" -> businessService.freePlayers();
                case "5" -> businessService.getRecentMatches();
                case "6" -> businessService.runAll();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }
}