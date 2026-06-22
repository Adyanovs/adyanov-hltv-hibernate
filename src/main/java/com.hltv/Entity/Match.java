package com.hltv.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_match")
    private Integer idMatch;

    @ManyToOne
    @JoinColumn(name = "tournament_name", referencedColumnName = "tournament_name", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_name_1", referencedColumnName = "team_name", nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_name_2", referencedColumnName = "team_name", nullable = false)
    private Team team2;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "score_team_1", nullable = false)
    @Min(0)
    private int scoreTeam1;

    @Column(name = "score_team_2", nullable = false)
    @Min(0)
    private int scoreTeam2;

    @Column(name = "stream_link", nullable = false, length = 500)
    private String streamLink;

    @Column(name = "winner_team")
    private String winnerTeam;

    protected Match() {}

    public Match(Tournament tournament, Team team1, Team team2, LocalDateTime startDate, int scoreTeam1, int scoreTeam2, String streamLink) {
        this.tournament = tournament;
        this.team1 = team1;
        this.team2 = team2;
        this.startDate = startDate;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.streamLink = streamLink;
        this.winnerTeam = determineWinner();
    }
    private String determineWinner() {
        if (scoreTeam1 > scoreTeam2) {
            return team1 != null ? team1.getTeamName() : null;
        } else if (scoreTeam2 > scoreTeam1) {
            return team2 != null ? team2.getTeamName() : null;
        } else {
            return null;
        }
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public String getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(String winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match m)) return false;
        return idMatch != null && Objects.equals(idMatch, m.idMatch);
    }

    @Override
    public int hashCode() { return Objects.hashCode(idMatch); }

    @Override
    public String toString() {
        return "Match{" +
                "idMatch=" + idMatch +
                ", tournament=" + tournament +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", startDate=" + startDate +
                ", scoreTeam1=" + scoreTeam1 +
                ", scoreTeam2=" + scoreTeam2 +
                ", streamLink='" + streamLink + '\'' +
                ", winnerTeam='" + winnerTeam + '\'' +
                '}';
    }
}