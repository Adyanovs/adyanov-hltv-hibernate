package com.hltv.Entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Player {
    @Id
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "social_networking_services")
    private String socialNetworkingServices;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "status", nullable = false)
    private String status;

    protected Player() {}

    public Player(String nickname, String socialNetworkingServices, String role, String status) {
        this.nickname = nickname;
        this.socialNetworkingServices = socialNetworkingServices;
        this.role = role;
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSocialNetworkingServices() {
        return socialNetworkingServices;
    }

    public void setSocialNetworkingServices(String socialNetworkingServices) {
        this.socialNetworkingServices = socialNetworkingServices;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player p)) return false;
        return Objects.equals(nickname, p.nickname);
    }

    @Override
    public int hashCode() { return Objects.hashCode(nickname); }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", socialNetworkingServices='" + socialNetworkingServices + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}