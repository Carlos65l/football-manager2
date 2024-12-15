package com.example.footballmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class League {

    @Id // Kennzeichnet das Feld als Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generiert automatisch die ID in aufsteigender Reihenfolge
    private Long id;

    private String leagueName;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
}
