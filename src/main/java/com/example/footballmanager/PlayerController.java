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
@RequestMapping("/players")  // Gibt die Basis-URL für alle Endpunkte in dieser Klasse an, z. B. "/players"
public class PlayerController {

    @Autowired  // Spring injiziert automatisch die PlayerRepository-Instanz
    private PlayerRepository playerRepository;

    // GET /players - Gibt alle Spieler zurück
    @GetMapping
    public ResponseEntity<?> getAllPlayers() {
        try {
            List<Player> players = playerRepository.findAll(); // Ruft alle Spieler aus der Datenbank ab
            if (players.isEmpty()) { // Prüft, ob die Liste leer ist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keine Spieler verfügbar");
            }
            return ResponseEntity.ok(players); // Gibt die Liste der Spieler mit Status 200 (OK) zurück
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen der Spieler: " + e.getMessage());
        }
    }

    // GET /players/{id} - Gibt einen Spieler anhand der ID zurück
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable Long id) {
        try {
            Player player = playerRepository.findById(id).orElse(null); // Sucht den Spieler mit der angegebenen ID
            if (player == null) { // Wenn kein Spieler gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Spieler mit ID " + id + " wurde nicht gefunden");
            }
            return ResponseEntity.ok(player); // Gibt den gefundenen Spieler zurück
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Abrufen des Spielers mit ID " + id + ": " + e.getMessage());
        }
    }

    // POST /players - Erstellt einen neuen Spieler
    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            Player savedPlayer = playerRepository.save(player); // Speichert den neuen Spieler in der Datenbank
            return ResponseEntity.status(HttpStatus.CREATED).body("Spieler erfolgreich erstellt: " + savedPlayer);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Erstellen des Spielers: " + e.getMessage());
        }
    }

    // PUT /players/{id} - Aktualisiert einen bestehenden Spieler
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer) {
        try {
            Player existingPlayer = playerRepository.findById(id).orElse(null); // Sucht den Spieler mit der angegebenen ID

            if (existingPlayer == null) { // Wenn kein Spieler gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Spieler mit ID " + id + " wurde nicht gefunden");
            }

            // Aktualisiert die Felder des gefundenen Spielers
            existingPlayer.setName(updatedPlayer.getName()); 
            existingPlayer.setPosition(updatedPlayer.getPosition());
            existingPlayer.setNumber(updatedPlayer.getNumber());

            Player savedPlayer = playerRepository.save(existingPlayer); // Speichert die Änderungen

            return ResponseEntity.ok("Spieler erfolgreich aktualisiert: " + savedPlayer);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Aktualisieren des Spielers mit ID " + id + ": " + e.getMessage());
        }
    }

    // DELETE /players/{id} - Löscht einen Spieler anhand der ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        try {
            Player player = playerRepository.findById(id).orElse(null); // Sucht den Spieler mit der angegebenen ID

            if (player == null) { // Wenn kein Spieler gefunden wird
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Spieler mit ID " + id + " wurde nicht gefunden");
            }

            playerRepository.delete(player); // Löscht den Spieler aus der Datenbank
            return ResponseEntity.ok("Spieler erfolgreich gelöscht mit ID: " + id);
        } catch (Exception e) { // Fängt Fehler ab
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler beim Löschen des Spielers mit ID " + id + ": " + e.getMessage());
        }
    }
}
