package com.hltv.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Tournament {
    @Id
    @Column(name = "tournament_name", length = 100)
    private String tournamentName;

    @Column(name = "prize_pool", nullable = false)
    @Min(0)
    private Integer prizePool;

    @Column(name = "sponsors", columnDefinition = "TEXT")
    private String sponsors;

    @Column(name = "regulations", length = 1000)
    private String regulations;

    protected Tournament() {}

    public Tournament(String tournamentName, Integer prizePool, String sponsors, String regulations) {
        this.tournamentName = tournamentName;
        this.prizePool = prizePool;
        this.sponsors = sponsors;
        this.regulations = regulations;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Integer getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Integer prizePool) {
        this.prizePool = prizePool;
    }

    public String getSponsors() {
        return sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }

    public String getRegulations() {
        return regulations;
    }

    public void setRegulations(String regulations) {
        this.regulations = regulations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tournament t)) return false;
        return Objects.equals(tournamentName, t.tournamentName);
    }

    @Override
    public int hashCode() { return Objects.hashCode(tournamentName); }

    @Override
    public String toString() {
        return "Tournament{" +
                "tournamentName='" + tournamentName + '\'' +
                ", prizePool=" + prizePool +
                ", sponsors='" + sponsors + '\'' +
                ", regulations='" + regulations + '\'' +
                '}';
    }
}