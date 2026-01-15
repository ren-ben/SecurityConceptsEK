# Test-Dokumentation - SecurityConceptsEK

## Übersicht
Diese Dokumentation beschreibt alle durchgeführten Testfälle für die SecurityConceptsEK Webapplikation. Die Tests decken Frontend-Funktionalität, Backend-API-Endpoints und Sicherheitsfunktionen ab.

**Getestet am:** 15. Januar 2026  
**Version:** 1.0  


## 1. Authentifizierungs-Tests

### TC-001: Login mit korrekten Credentials

**Ziel:** Verifizieren, dass ein Benutzer mit gültigen Anmeldedaten erfolgreich einloggen kann.

**Vorbedingungen:**
- Applikation läuft (docker-compose up)


**Testschritte:**
1. Browser öffnen und zu `http://localhost` navigieren
2. Login-Seite sollte angezeigt werden
3. Username eingeben
4. Passwort eingeben
5. Auf "Login" Button klicken

**Erwartetes Ergebnis:**
- Login erfolgreich
- Redirect zur Community-Übersichtsseite
- Benutzername wird in der Navigation angezeigt
- JWT-Token wird im LocalStorage gespeichert
- Keine Fehlermeldungen

**Tatsächliches Ergebnis:** ✅ **PASS**
- Erfolgreicher Login
- Redirect funktioniert korrekt
- Token wurde gespeichert

---

### TC-002: Login mit falschen Credentials

**Ziel:** Sicherstellen, dass ungültige Login-Versuche abgelehnt werden.

**Vorbedingungen:**
- Applikation läuft
- Kein Benutzer mit den Testdaten existiert

**Testschritte:**
1. Browser öffnen und zu `http://localhost` navigieren
2. Login-Seite aufrufen
3. Username eingeben: `wrong@example.com`
4. Falsches Passwort eingeben: `WrongPassword`
5. Auf "Login" Button klicken

**Erwartetes Ergebnis:**
- Login wird abgelehnt
- Fehlermeldung wird angezeigt: "Ungültige Anmeldedaten"
- Benutzer bleibt auf der Login-Seite
- Kein Token wird gespeichert

**Tatsächliches Ergebnis:** ✅ **PASS**
- Login wurde korrekt abgelehnt
- Fehlermeldung erscheint

---

### TC-003: Login mit leerem Passwort

**Ziel:** Validierung der Eingabefelder vor dem Absenden.

**Vorbedingungen:**
- Applikation läuft

**Testschritte:**
1. Login-Seite aufrufen
2. Username eingeben: `testuser@example.com`
3. Passwortfeld leer lassen
4. Auf "Login" Button klicken

**Erwartetes Ergebnis:**
- Formular-Validierung schlägt fehl
- Fehlermeldung: "Passwort ist erforderlich"
- Kein API-Request wird gesendet
- Benutzer bleibt auf Login-Seite

**Tatsächliches Ergebnis:** ✅ **PASS**
- Client-seitige Validierung verhindert Submit
- Entsprechende Warnung wird angezeigt

---

### TC-004: Session Persistence

**Ziel:** Überprüfen, dass die Session nach Page Reload erhalten bleibt.

**Vorbedingungen:**
- Benutzer ist eingeloggt (TC-001 erfolgreich)

**Testschritte:**
1. Nach erfolgreichem Login
2. Browser-Seite neu laden (F5)
3. Navigation überprüfen

**Erwartetes Ergebnis:**
- Benutzer bleibt eingeloggt
- Keine erneute Anmeldung erforderlich
- Token aus LocalStorage wird verwendet
- Benutzerdaten werden geladen

**Tatsächliches Ergebnis:** ✅ **PASS**
- Session bleibt bestehen


---

## 2. Community-Funktionen Tests

### TC-006: Community-Seite anzeigen (Authenticated)

**Ziel:** Eingeloggte Benutzer können alle verfügbaren Communities sehen.

**Vorbedingungen:**
- Benutzer ist eingeloggt (TC-001)
- Mehrere Communities existieren (public und private)

**Testschritte:**
1. Als eingeloggter Benutzer zu "Communities" navigieren
2. Liste überprüfen
3. Filter-Optionen testen (Public/Private/Joined)

**Erwartetes Ergebnis:**
- Alle öffentlichen Communities werden angezeigt
- Private Communities, zu denen User Zugang hat, werden angezeigt
- "Join" / "Leave" Buttons sind funktional
- Mitgliederzahl wird angezeigt
- Filter funktionieren korrekt

**Tatsächliches Ergebnis:** ✅ **PASS**
- Vollständige Community-Liste sichtbar
- Berechtigungen werden korrekt angewendet
- Interaktive Buttons funktionieren


###   TC-007: Community-Seite anzeigen (Public)

Ziel: Public mit Login anzeigen.

Vorbedingungen:
- Applikation läuft
- Benutzer ist eingeloggt


Testschritte:

- Browser öffnen zu http://localhost/public
- Zu "Public" navigieren

Erwartetes Ergebnis:

-  public wird angezeigt

Tatsächliches Ergebnis: ✅ PASS

- Public wird korrekt angezeigt



## 3. Logout und Session-Management

### TC-08: Erfolgreicher Logout

**Ziel:** Benutzer kann sich korrekt ausloggen.

**Vorbedingungen:**
- Benutzer ist eingeloggt (TC-001)
- Auf einer beliebigen authentifizierten Seite

**Testschritte:**
1. Auf Benutzerprofil-Icon in der Navigation klicken
2. "Sign Out" / "Abmelden" auswählen
3. Bestätigung abwarten

**Erwartetes Ergebnis:**
- Erfolgreiche Abmeldung
- JWT-Token wird aus LocalStorage entfernt
- Session wird serverseitig invalidiert
- Redirect zur Login-Seite oder Startseite
- Geschützte Routen sind nicht mehr zugänglich
- Bestätigungsmeldung: "Successfully logged out"

**Tatsächliches Ergebnis:** ✅ **PASS**
- Logout erfolgreich durchgeführt
- Token gelöscht
- Alle Sessions beendet
- Redirect funktioniert


---

### TC-009: Zugriff nach Logout

**Ziel:** Nach Logout können geschützte Seiten nicht mehr aufgerufen werden.

**Vorbedingungen:**
- Logout wurde durchgeführt (TC-010)

**Testschritte:**
1. Nach Logout zu `http://localhost/community` navigieren
2. Zugriff versuchen

**Erwartetes Ergebnis:**
- Zugriff wird verweigert
- Automatischer Redirect zur Login-Seite

**Tatsächliches Ergebnis:** ✅ **PASS**
- Route Guard funktioniert
- Unautorisierter Zugriff verhindert


###   TC-010: Community-Seite anzeigen (Public)

Ziel: Public ohne Login anzeigen.

Vorbedingungen:

- Applikation läuft

- Benutzer ist NICHT eingeloggt

- Mindestens eine öffentliche Community existiert

Testschritte:

- Browser öffnen zu http://localhost/public

- Zu "Public Communities" navigieren

- Liste der Communities überprüfen

Erwartetes Ergebnis:

-  public wird angezeigt

Tatsächliches Ergebnis: ✅ PASS

- Public wird korrekt angezeigt





## 4. API-Endpoint Tests

### TC-011: Public Endpoint ohne Authentication

**Ziel:** Öffentliche Endpoints sind ohne Token erreichbar.

**Testschritte:**
```bash
curl -X GET http://localhost:8080/api/public/info
```
Erwartetes Ergebnis:

    Status Code: 200 OK

    JSON Response mit öffentlichen Communities

    Keine Authorization Header erforderlich

Tatsächliches Ergebnis: ✅ PASS
