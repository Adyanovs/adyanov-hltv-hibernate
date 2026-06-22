package com.hltv.util;

import com.hltv.Entity.*;
import com.hltv.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private DataSeeder() {}

    public static void seed() {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Long playersCount = em.createQuery("SELECT COUNT(p) FROM Player p", Long.class)
                    .getSingleResult();
            if (playersCount > 0) {
                tx.commit();
                log.info("Начальные данные уже есть, заполнение пропущено");
                return;
            }

            Team falcons = new Team("Team Falcons", "falcons.png", "International", 5);
            Team spirit = new Team("Team Spirit", "spirit.png", "Russia", 6);
            Team furia = new Team("FURIA", "furia.png", "Brazil", 3);
            Team mouz = new Team("MOUZ", "mouz.png", "Europe", 4);
            Team g2 = new Team("G2", "g2.png", "Europe", 7);
            Team aurora = new Team("Aurora", "aurora.png", "Russia", 10);
            Team mongolz = new Team("The MongolZ", "mongolz.png", "Mongolia", 8);
            Team parivision = new Team("PARIVISION", "parivision.png", "Kazakhstan", 12);
            Team ninez = new Team("9z", "9z.png", "South America", 11);
            Team heroic = new Team("HEROIC", "heroic.png", "Europe", 14);
            Team monte = new Team("Monte", "monte.png", "Ukraine", 15);
            Team gentleMates = new Team("Gentle Mates", "gentlemates.png", "France", 20);
            Team navi = new Team("NAVI", "navi.png", "Ukraine", 2);
            Team vitality = new Team("Team Vitality", "vitality.png", "France", 1);
            Team faze = new Team("FaZe Clan", "faze.png", "Europe", 9);
            Team astralis = new Team("Astralis", "astralis.png", "Denmark", 13);
            Team gamerLegion = new Team("GamerLegion", "gamerlegion.png", "Europe", 18);
            Team eternalFire = new Team("Eternal Fire", "eternalfire.png", "Turkey", 16);
            Team rutMiit = new Team("RUT MIIT", "miit.png", "Russia", 89);
            Team mgu = new Team("MGU", "mgu.png", "Russia", 90);

            List.of(falcons, spirit, furia, mouz, g2, aurora, mongolz, parivision, ninez,
                    heroic, monte, gentleMates, navi, vitality, faze, astralis,
                    gamerLegion, eternalFire, rutMiit, mgu).forEach(em::persist);

            // RUT MIIT
            Player adyanov = new Player("adyanov", "@sanal", "PLAYER", "ACTIVE");
            Player timofeev = new Player("timofeev", "@vlad", "PLAYER", "ACTIVE");
            Player chesnokov = new Player("4esnokov", "@vadim", "PLAYER", "ACTIVE");
            Player salnikov = new Player("saln1kov", "@max", "PLAYER", "ACTIVE");
            Player tennikov = new Player("tennikoV", "@igor", "PLAYER", "ACTIVE");

            // Falcons
            Player niko = new Player("NiKo", "@niko", "PLAYER", "ACTIVE");
            Player monesy = new Player("m0NESY", "@monesy", "PLAYER", "ACTIVE");
            Player kyousuke = new Player("kyousuke", "@kyousuke", "PLAYER", "ACTIVE");
            Player teses = new Player("TeSeS", "@teses", "PLAYER", "ACTIVE");
            Player karrigan = new Player("karrigan", "@karrigan", "PLAYER", "ACTIVE");

            // Spirit
            Player donk = new Player("donk", "@donk", "PLAYER", "ACTIVE");
            Player sh1ro = new Player("sh1ro", "@sh1ro", "PLAYER", "ACTIVE");
            Player magixx = new Player("magixx", "@magixx", "PLAYER", "ACTIVE");
            Player zont1x = new Player("zont1x", "@zont1x", "PLAYER", "ACTIVE");
            Player tn1r = new Player("tN1R", "@tn1r", "PLAYER", "ACTIVE");

            // FURIA
            Player kscerato = new Player("KSCERATO", "@kscerato", "PLAYER", "ACTIVE");
            Player yuurih = new Player("yuurih", "@yuurih", "PLAYER", "ACTIVE");
            Player fallen = new Player("FalleN", "@fallen", "PLAYER", "ACTIVE");
            Player yekindar = new Player("YEKINDAR", "@yekindar", "PLAYER", "ACTIVE");
            Player molodoy = new Player("molodoy", "@molodoy", "PLAYER", "ACTIVE");

            // MOUZ
            Player xertion = new Player("xertioN", "@xertion", "PLAYER", "ACTIVE");
            Player torzsi = new Player("torzsi", "@torzsi", "PLAYER", "ACTIVE");
            Player xelex = new Player("xelex", "@xelex", "PLAYER", "ACTIVE");
            Player spinx = new Player("Spinx", "@spinx", "PLAYER", "ACTIVE");
            Player jl = new Player("jL", "@jl", "PLAYER", "ACTIVE");

            // G2
            Player hunter = new Player("huNter-", "@hunter", "PLAYER", "ACTIVE");
            Player matys = new Player("MATYS", "@matys", "PLAYER", "ACTIVE");
            Player sunpayus = new Player("SunPayus", "@sanpayus", "PLAYER", "ACTIVE");
            Player nertz = new Player("NertZ", "@nertz", "PLAYER", "ACTIVE");
            Player heavygod = new Player("HeavyGod", "@heavygod", "PLAYER", "ACTIVE");

            // The MongolZ
            Player techno = new Player("Techno", "@techno4k", "PLAYER", "ACTIVE");
            Player cobrazera = new Player("cobrazera", "@cobrazera", "PLAYER", "ACTIVE");
            Player mzinho = new Player("mzinho", "@mzinho", "PLAYER", "ACTIVE");
            Player nineTen = new Player("910", "@910", "PLAYER", "ACTIVE");
            Player blitz = new Player("bLitz", "@blitz", "PLAYER", "ACTIVE");

            // 9z
            Player luchov = new Player("luchov", "@luchov", "PLAYER", "ACTIVE");
            Player max = new Player("max", "@max", "PLAYER", "ACTIVE");
            Player dgt = new Player("dgt", "@dgt", "PLAYER", "ACTIVE");
            Player meyern = new Player("meyern", "@meyern", "PLAYER", "ACTIVE");
            Player huasopeek = new Player("HUASOPEEK", "@HUASOPEEK", "PLAYER", "ACTIVE");

            // NAVI
            Player aleksib = new Player("Aleksib", "@aleksib", "PLAYER", "ACTIVE");
            Player im = new Player("iM", "@im", "PLAYER", "ACTIVE");
            Player b1t = new Player("b1t", "@b1t", "PLAYER", "ACTIVE");
            Player wonderful = new Player("w0nderful", "@wonderful", "PLAYER", "ACTIVE");
            Player makazze = new Player("makazze", "@makazze", "PLAYER", "ACTIVE");

            // Vitality
            Player zywoo = new Player("ZywOo", "@zywoo", "PLAYER", "ACTIVE");
            Player apex = new Player("apEX", "@apex", "PLAYER", "ACTIVE");
            Player flamez = new Player("flameZ", "@flamez", "PLAYER", "ACTIVE");
            Player mezii = new Player("mezii", "@mezii", "PLAYER", "ACTIVE");
            Player ropz = new Player("ropz", "@ropz", "PLAYER", "ACTIVE");

            List.of(adyanov, timofeev, chesnokov, salnikov, tennikov,
                            niko, monesy, kyousuke, teses, karrigan,
                            donk, sh1ro, magixx, zont1x, tn1r,
                            kscerato, yuurih, fallen, yekindar, molodoy,
                            xertion, torzsi, xelex, spinx, jl,
                            hunter, matys, sunpayus, nertz, heavygod,
                            techno, cobrazera, mzinho, nineTen, blitz,
                            luchov, max, dgt, meyern, huasopeek,
                            aleksib, im, b1t, wonderful, makazze,
                            zywoo, apex, flamez, mezii, ropz)
                    .forEach(em::persist);

            Statistics stats1 = new Statistics(monesy, 96, 92, 88, 98, 86, 90);
            Statistics stats2 = new Statistics(niko, 94, 90, 86, 88, 84, 92);
            Statistics stats3 = new Statistics(donk, 97, 94, 87, 86, 86, 90);
            Statistics stats4 = new Statistics(sh1ro, 88, 82, 92, 96, 84, 82);
            Statistics stats5 = new Statistics(zywoo, 98, 88, 89, 97, 88, 86);
            Statistics stats6 = new Statistics(jl, 90, 86, 84, 80, 82, 88);
            Statistics stats7 = new Statistics(kscerato, 90, 84, 86, 82, 80, 84);
            Statistics stats8 = new Statistics(ropz, 90, 84, 82, 90, 80, 82);
            Statistics stats9 = new Statistics(yuurih, 86, 84, 78, 82, 80, 84);
            Statistics stats10 = new Statistics(molodoy, 90, 90, 80, 90, 82, 88);
            Statistics stats11 = new Statistics(timofeev, 99, 90, 85, 93, 86, 85);
            Statistics stats12 = new Statistics(chesnokov, 90, 67, 80, 99, 97, 88);
            Statistics stats13 = new Statistics(tennikov, 89, 87, 90, 90, 93, 12);
            Statistics stats14 = new Statistics(salnikov, 90, 90, 90, 90, 90, 90);

            List.of(stats1, stats2, stats3, stats4, stats5, stats6, stats7, stats8,
                    stats9, stats10, stats11, stats12, stats13, stats14).forEach(em::persist);

            Tournament pglAstana = new Tournament("PGL Astana 2026", 1600000, "PGL, Esportal", "VALVE REGULATIONS");
            Tournament iemAtlanta = new Tournament("IEM Atlanta 2026", 1000000, "Intel, ESL", "VALVE REGULATIONS");
            Tournament iemRio = new Tournament("IEM Rio 2026", 1000000, "Intel, ESL", "VALVE REGULATIONS");
            Tournament blastRivals = new Tournament("BLAST Rivals 2026 S1", 500000, "BLAST", "VALVE REGULATIONS");
            Tournament eslProLeague = new Tournament("ESL Pro League Season 21", 850000, "ESL, AMD", "VALVE REGULATIONS");
            Tournament moscowCup = new Tournament("Moscow Cup 2026", 999999, "Russia, Putin", "Putin REGULATIONS");

            List.of(pglAstana, iemAtlanta, iemRio, blastRivals, eslProLeague, moscowCup)
                    .forEach(em::persist);

            em.flush();

            Match match1 = new Match(pglAstana, mouz, vitality, LocalDateTime.of(2026, 5, 12, 14, 0), 13, 11, "twitch");
            Match match2 = new Match(pglAstana, navi, mongolz, LocalDateTime.of(2026, 5, 12, 11, 0), 13, 9, "twitch");
            Match match3 = new Match(pglAstana, spirit, furia, LocalDateTime.of(2026, 5, 11, 13, 0), 7, 13, "twitch");
            Match match4 = new Match(pglAstana, falcons, g2, LocalDateTime.of(2026, 5, 11, 16, 0), 13, 5, "twitch");
            Match match5 = new Match(pglAstana, mongolz, falcons, LocalDateTime.of(2026, 5, 12, 16, 0), 6, 13, "twitch");
            Match match6 = new Match(iemAtlanta, faze, vitality, LocalDateTime.of(2026, 5, 13, 15, 30), 19, 17, "twitch");
            Match match7 = new Match(iemAtlanta, navi, vitality, LocalDateTime.of(2026, 5, 13, 23, 0), 16, 13, "twitch");
            Match match8 = new Match(iemRio, vitality, spirit, LocalDateTime.of(2026, 4, 20, 18, 0), 13, 11, "twitch");
            Match match9 = new Match(eslProLeague, vitality, falcons, LocalDateTime.of(2026, 3, 15, 17, 0), 3, 13, "twitch");

            List.of(match1, match2, match3, match4, match5, match6, match7, match8, match9)
                    .forEach(em::persist);

            em.flush();

            // Falcons
            Contract c1 = new Contract(falcons, niko, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c2 = new Contract(falcons, monesy, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c3 = new Contract(falcons, kyousuke, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c4 = new Contract(falcons, karrigan, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c5 = new Contract(falcons, teses, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            // MOUZ
            Contract c6 = new Contract(mouz, jl, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c7 = new Contract(mouz, xertion, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c8 = new Contract(mouz, xelex, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c9 = new Contract(mouz, spinx, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c10 = new Contract(mouz, torzsi, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            // Spirit
            Contract c11 = new Contract(spirit, donk, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c12 = new Contract(spirit, tn1r, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c13 = new Contract(spirit, zont1x, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c14 = new Contract(spirit, magixx, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c15 = new Contract(spirit, sh1ro, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            // FURIA
            Contract c16 = new Contract(furia, molodoy, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c17 = new Contract(furia, yuurih, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c18 = new Contract(furia, yekindar, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c19 = new Contract(furia, kscerato, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c20 = new Contract(furia, fallen, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            // NAVI
            Contract c21 = new Contract(navi, aleksib, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c22 = new Contract(navi, b1t, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c23 = new Contract(navi, wonderful, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c24 = new Contract(navi, makazze, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c25 = new Contract(navi, im, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            // Vitality
            Contract c26 = new Contract(vitality, zywoo, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c27 = new Contract(vitality, ropz, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c28 = new Contract(vitality, mezii, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c29 = new Contract(vitality, flamez, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));
            Contract c30 = new Contract(vitality, apex, LocalDate.of(2025, 1, 1), LocalDate.of(2027, 12, 31));

            List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10,
                            c11, c12, c13, c14, c15, c16, c17, c18, c19, c20,
                            c21, c22, c23, c24, c25, c26, c27, c28, c29, c30)
                    .forEach(em::persist);

            tx.commit();
            log.info("Начальные данные для HLTV добавлены");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            log.error("Ошибка при заполнении данных: {}", e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }
}