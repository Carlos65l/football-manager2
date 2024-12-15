package com.example.footballmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Team {

    @Id // Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch generiert
    private Long id;

    private String name;

    @ManyToOne //viele teams zu einer liga 
    private League league;  // Liga direkt verknüpft

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
