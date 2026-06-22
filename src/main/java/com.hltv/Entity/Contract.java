package com.hltv.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import java.util.Objects;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contract")
    private Integer idContract;

    @ManyToOne
    @JoinColumn(name = "team_name", referencedColumnName = "team_name", nullable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "player_nickname", referencedColumnName = "nickname", nullable = false, unique = true)
    private Player player;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "final_date", nullable = false)
    private LocalDate finalDate;

    protected Contract() {}

    public Contract(Team team, Player player, LocalDate startDate, LocalDate finalDate) {
        this.team = team;
        this.player = player;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract c)) return false;
        return Objects.equals(player, c.player) && Objects.equals(team, c.team);
    }

    @Override
    public int hashCode() { return Objects.hash(team, player); }

    @Override
    public String toString() {
        return "Contract{" +
                "idContract=" + idContract +
                ", team=" + team +
                ", player=" + player +
                ", startDate=" + startDate +
                ", finalDate=" + finalDate +
                '}';
    }
}