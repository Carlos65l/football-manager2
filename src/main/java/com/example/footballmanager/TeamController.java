package com.example.footballmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Kennzeichnet die Klasse als REST-Controller, der HTTP-Anfragen verarbeitet und JSON zurückgibt
@RequestMapping("/teams")  // Gibt die Basis-URL für alle Endpunkte in dieser Klasse an, z. B. "/teams"
public class TeamController {

    @Autowired  // Spring sorgt dafür, dass die TeamRepository automatisch injiziert wird
    private TeamRepository teamRepository;

    // GET /teams - Gibt alle Teams zurück
    @GetMapping
    public ResponseEntity<?> getAllTeams() { 
        try {
            List<Team> teams = teamRepository.findAll(); // Ruft alle Teams aus der Datenbank ab
            if (teams.isEmpty()) { // Prüft, ob keine Teams gefunden wurden
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keine Teams verfügbar");
            }
            return ResponseEntity.ok(teams); // Gibt alle Teams mit Status 200 (OK) zurück
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen der Teams: " + e.getMessage());
        }
    }

    // GET /teams/{id} - Gibt ein Team anhand der ID zurück
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        try {
            Team team = teamRepository.findById(id).orElse(null); // Sucht das Team mit der angegebenen ID
            if (team == null) { // Wenn das Team nicht gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Team mit ID " + id + " wurde nicht gefunden");
            }
            return ResponseEntity.ok(team); // Gibt das Team mit Status 200 (OK) zurück
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen des Teams mit ID " + id + ": " + e.getMessage());
        }
    }

    // POST /teams - Erstellt ein neues Team
    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        try {
            Team savedTeam = teamRepository.save(team); // Speichert das neue Team in der Datenbank
            return ResponseEntity.status(HttpStatus.CREATED).body("Team erfolgreich erstellt: " + savedTeam);
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Erstellen des Teams: " + e.getMessage());
        }
    }

    // PUT /teams/{id} - Aktualisiert ein bestehendes Team
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
        try {
            Team existingTeam = teamRepository.findById(id).orElse(null); // Sucht das Team mit der angegebenen ID

            if (existingTeam == null) { // Wenn das Team nicht gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Team mit ID " + id + " wurde nicht gefunden");
            }

            // Aktualisiert die Felder des gefundenen Teams
            existingTeam.setName(updatedTeam.getName());
            existingTeam.setLeague(updatedTeam.getLeague());

            Team savedTeam = teamRepository.save(existingTeam); // Speichert die Änderungen

            return ResponseEntity.ok("Team erfolgreich aktualisiert: " + savedTeam);
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Aktualisieren des Teams mit ID " + id + ": " + e.getMessage());
        }
    }

    // DELETE /teams/{id} - Löscht ein Team anhand der ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            Team team = teamRepository.findById(id).orElse(null); // Sucht das Team mit der angegebenen ID

            if (team == null) { // Wenn das Team nicht gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Team mit ID " + id + " wurde nicht gefunden");
            }

            teamRepository.delete(team); // Löscht das Team aus der Datenbank
            return ResponseEntity.ok("Team erfolgreich gelöscht mit ID: " + id);
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Löschen des Teams mit ID " + id + ": " + e.getMessage());
        }
    }

    // GET /teams/byLeague/{leagueName} - Gibt alle Teams einer bestimmten Liga zurück
    @GetMapping("/byLeague/{leagueName}")
    public ResponseEntity<?> getTeamsByLeague(@PathVariable String leagueName) {
        try {
            // Alle Teams aus der Datenbank laden
            List<Team> teams = teamRepository.findAll();
    
            // Teams nach Liga filtern
            List<Team> filteredTeams = teams.stream()
                    .filter(team -> team.getLeague() != null && leagueName.equalsIgnoreCase(team.getLeague().getLeagueName()))
                    .toList();
    
            // Fehlerbehandlung, falls keine Teams in der angegebenen Liga gefunden wurden
            if (filteredTeams.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Keine Teams gefunden in der Liga: " + leagueName);
            }
    
            return ResponseEntity.ok(filteredTeams); // Gibt die gefilterten Teams zurück
        } catch (Exception e) { // Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen der Teams für die Liga: " + leagueName + " - " + e.getMessage());
        }
    }
}
