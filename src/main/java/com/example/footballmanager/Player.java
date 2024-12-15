package com.example.footballmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity  
public class Player {

    @Id // id Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID wird automatisch generiert
    private Long id;

    private String name;  // Name des Spielers
    private String position;  // Position des Spielers 
    private int number;  // Trikotnummer des Spielers

    @ManyToOne     // Viele Spieler können zu einem Team gehören. 
    @JoinColumn(name = "team_id")  // "team_id" ist der Fremdschlüssel in der Spieler-Tabelle, der auf das Team verweist.
    private Team team;


    // Getter und Setter für jedes Feld. 

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

    public String getPosition() {
        return position;  
    }

    public void setPosition(String position) {
        this.position = position;  
    }

    public int getNumber() {
        return number;  
    }

    public void setNumber(int number) {
        this.number = number;  
    }

    public Team getTeam() {
        return team;  
    }

    public void setTeam(Team team) {
        this.team = team; 
    }
}
