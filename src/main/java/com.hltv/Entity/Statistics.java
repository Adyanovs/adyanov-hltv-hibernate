package com.hltv.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stat")
    private Integer idStat;

    @OneToOne
    @JoinColumn(name = "player_nickname", referencedColumnName = "nickname", nullable = false)
    private Player player;

    @Column(name = "firepower")
    @Min(0)
    @Max(100)
    private int firepower;

    @Column(name = "opening")
    @Min(0)
    @Max(100)
    private int opening;

    @Column(name = "clutching")
    @Min(0)
    @Max(100)
    private int clutching;

    @Column(name = "sniper")
    @Min(0)
    @Max(100)
    private int sniper;

    @Column(name = "utility")
    @Min(0)
    @Max(100)
    private int utility;

    @Column(name = "trading")
    @Min(0)
    @Max(100)
    private int trading;

    protected Statistics() {}

    public Statistics(Player player, int firepower, int opening, int clutching, int sniper, int utility, int trading) {
        this.player = player;
        this.firepower = firepower;
        this.opening = opening;
        this.clutching = clutching;
        this.sniper = sniper;
        this.utility = utility;
        this.trading = trading;
    }

    public Integer getIdStat() {
        return idStat;
    }

    public void setIdStat(Integer idStat) {
        this.idStat = idStat;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getFirepower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getOpening() {
        return opening;
    }

    public void setOpening(int opening) {
        this.opening = opening;
    }

    public int getClutching() {
        return clutching;
    }

    public void setClutching(int clutching) {
        this.clutching = clutching;
    }

    public int getSniper() {
        return sniper;
    }

    public void setSniper(int sniper) {
        this.sniper = sniper;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public int getTrading() {
        return trading;
    }

    public void setTrading(int trading) {
        this.trading = trading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics s)) return false;
        return Objects.equals(player, s.player);
    }

    @Override
    public int hashCode() { return Objects.hashCode(player); }

    @Override
    public String toString() {
        return "Statistics{" +
                "idStat=" + idStat +
                ", player=" + player +
                ", firepower=" + firepower +
                ", opening=" + opening +
                ", clutching=" + clutching +
                ", sniper=" + sniper +
                ", utility=" + utility +
                ", trading=" + trading +
                '}';
    }
}