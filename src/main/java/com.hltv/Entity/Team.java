package com.hltv.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import javax.management.MXBean;
import java.util.Objects;

@Entity
public class Team {
    @Id
    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;
    @Column(name = "logotype")
    private String logotype;

    @Column(name = "country")
    private String country;

    @Column(name = "rank", nullable = false)
    @Min(1)
    @Max(200)
    private Integer rank;

    protected Team() {}

    public Team(String teamName, String logotype, String country, Integer rank) {
        this.teamName = teamName;
        this.logotype = logotype;
        this.country = country;
        this.rank = rank;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogotype() {
        return logotype;
    }

    public void setLogotype(String logotype) {
        this.logotype = logotype;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team t)) return false;
        return Objects.equals(teamName, t.teamName);
    }

    @Override
    public int hashCode() { return Objects.hashCode(teamName); }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", logotype='" + logotype + '\'' +
                ", country='" + country + '\'' +
                ", rank=" + rank +
                '}';
    }

}