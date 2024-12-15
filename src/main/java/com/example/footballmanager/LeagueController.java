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

@RestController // Markiert diese Klasse als REST-Controller, der JSON-Antworten zurückgibt
@RequestMapping("/leagues") // Basis-URL für alle Endpunkte dieser Klasse: "/leagues"
public class LeagueController {

    @Autowired // Automatische Injection des LeagueRepository durch Spring
    private LeagueRepository leagueRepository;

    // GET /leagues - Gibt alle Ligen zurück
    @GetMapping
    public ResponseEntity<?> getAllLeagues() {
        try {
            List<League> leagues = leagueRepository.findAll(); // Ruft alle Ligen aus der Datenbank ab
            if (leagues.isEmpty()) { // Überprüft, ob die Liste leer ist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keine Ligen verfügbar");
            }
            return ResponseEntity.ok(leagues); // Gibt die Liste der Ligen mit Status 200 (OK) zurück
        } catch (Exception e) { // Fängt Fehler ab, die während der Datenbankabfrage auftreten können
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen der Ligen: " + e.getMessage());
        }
    }

    // GET /leagues/{id} - Gibt eine Liga anhand der ID zurück
    @GetMapping("/{id}")
    public ResponseEntity<?> getLeagueById(@PathVariable Long id) {
        try {
            League league = leagueRepository.findById(id).orElse(null); // Sucht die Liga mit der angegebenen ID
            if (league == null) { // Wenn keine Liga gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Liga mit ID " + id + " wurde nicht gefunden");
            }
            return ResponseEntity.ok(league); // Gibt die gefundene Liga zurück
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen der Liga mit ID " + id + ": " + e.getMessage());
        }
    }

    // POST /leagues - Erstellt eine neue Liga
    @PostMapping
    public ResponseEntity<?> createLeague(@RequestBody League league) {
        try {
            League savedLeague = leagueRepository.save(league); // Speichert die neue Liga in der Datenbank
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Liga erfolgreich erstellt: " + savedLeague);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Erstellen der Liga: " + e.getMessage());
        }
    }

    // PUT /leagues/{id} - Aktualisiert eine bestehende Liga
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLeague(@PathVariable Long id, @RequestBody League updatedLeague) {
        try {
            League existingLeague = leagueRepository.findById(id).orElse(null); // Sucht die Liga mit der angegebenen ID
            if (existingLeague == null) { // Wenn keine Liga gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Liga mit ID " + id + " wurde nicht gefunden");
            }

            existingLeague.setLeagueName(updatedLeague.getLeagueName()); // Setzt den neuen Namen für die Liga
            League savedLeague = leagueRepository.save(existingLeague); // Speichert die aktualisierte Liga

            return ResponseEntity.ok("Liga erfolgreich aktualisiert: " + savedLeague);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Aktualisieren der Liga mit ID " + id + ": " + e.getMessage());
        }
    }

    // DELETE /leagues/{id} - Löscht eine Liga anhand der ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLeague(@PathVariable Long id) {
        try {
            League league = leagueRepository.findById(id).orElse(null); // Sucht die Liga mit der angegebenen ID
            if (league == null) { // Wenn keine Liga gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Liga mit ID " + id + " wurde nicht gefunden");
            }

            leagueRepository.delete(league); // Löscht die Liga aus der Datenbank
            return ResponseEntity.ok("Liga erfolgreich gelöscht mit ID: " + id);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Löschen der Liga mit ID " + id + ": " + e.getMessage());
        }
    }
}
