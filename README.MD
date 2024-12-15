FussballManager

Beschreibung
Eine Anwendung zur Verwaltung von Fußballspielern, Teams und Ligen mit REST-APIs. Diese Anwendung basiert auf Spring Boot und speichert Daten in einer In-Memory-Datenbank (H2). Die APIs ermöglichen CRUD-Operationen (Create, Read, Update, Delete) für Spieler, Teams und Ligen.

Technologien
- Programmiersprache: Java
- Frameworks: Spring Boot
- Datenbank: H2 (In-Memory)
- Build-Tool: Gradle

Voraussetzungen
- Java 21: Download hier https://jdk.java.net/
- Gradle 7.0 oder höher: Download hier https://gradle.org/releases/

Installation und Start
1. In das Projektverzeichnis wechseln:
   cd "\footballmanager 4\footballmanager"

2. Anwendung starten:
   .\gradlew bootRun

3. Standardport: Die API läuft unter http://localhost:8080

Verwendung

API-Endpunkte

Ligen
- GET /leagues: Gibt alle Ligen zurück.
- GET /leagues/{id}: Gibt die Liga mit der entsprechenden ID zurück.
- POST /leagues: Erstellt eine neue Liga.
  Body:
  {
    "leagueName": "<leagueName>"
  }
- PUT /leagues/{id}: Ändert den Namen einer Liga.
  Body:
  {
    "leagueName": "<new leagueName>"
  }
- DELETE /leagues/{id}: Löscht eine Liga.
- GET /teams/byLeagues/{leagueName}: Gibt alle Teams in der entsprechenden Liga zurück.

Beispiel für einen POST-Request:
curl -X POST http://localhost:8080/leagues -H "Content-Type: application/json" -d '{"leagueName": "Bundesliga"}'

Teams
- GET /teams: Gibt alle Teams zurück.
- GET /teams/{id}: Gibt ein Team anhand der ID zurück.
- POST /teams: Erstellt ein neues Team.
  Body:
  {
    "name": "<name>",
    "league": {
      "id": <leagueid>
    }
  }
- PUT /teams/{id}: Aktualisiert ein Team.
  Body:
  {
    "name": "<new name>",
    "league": {
      "id": <new leagueid>
    }
  }
- DELETE /teams/{id}: Löscht ein Team.

Beispiel für einen POST-Request:
curl -X POST http://localhost:8080/teams -H "Content-Type: application/json" -d '{"name": "Dortmund", "league": {"id": 1}}'

Spieler
- GET /players: Gibt alle Spieler zurück.
- GET /players/{id}: Gibt einen Spieler anhand der ID zurück.
- POST /players: Erstellt einen neuen Spieler.
  Body:
  {
    "name": "<name>",
    "position": "<position>",
    "number": <number>,
    "team": {
      "id": <teamid>
    }
  }
- PUT /players/{id}: Aktualisiert einen Spieler.
  Body:
  {
    "name": "<name>",
    "position": "<position>",
    "number": <number>,
    "team": {
      "id": <teamid>
    }
  }
- DELETE /players/{id}: Löscht einen Spieler.

Beispiel für einen POST-Request:
curl -X POST http://localhost:8080/players -H "Content-Type: application/json" -d '{"name": "Gregor Kobel", "position": "Tor", "number": 1, "team": {"id": 1}}'

Projektstruktur
- src/main/java/com/example/footballmanager: Enthält die Java-Klassen für die Anwendung:
  - Controller: REST-Endpunkte für Spieler, Teams und Ligen.
  - Repository: Datenzugriffsschichten für die Entitäten.
- src/main/resources:
  - application.properties: Konfigurationsdatei (z. B. Datenbankdetails).

Konfiguration
- Standardport: 8080
- Datenbank: H2 (In-Memory)
  - URL: http://localhost:8080/h2-console
  - Benutzername: sa
  - Passwort: (kein Passwort erforderlich)

Autoren
- Carlos Slaiwa
- Devin Altinel