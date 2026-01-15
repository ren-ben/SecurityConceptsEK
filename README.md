# Gegenüberstellung gängiger Authentifikationssysteme

## Deployment-Anleitung
System:

    Docker (Version 20.10 oder höher)

    Docker Compose (Version 2.0 oder höher)

    Git
Repository klonen

    git clone https://github.com/ren-ben/SecurityConceptsEK.git

    cd SecurityConceptsEK
Umgebungsvariablen konfigurieren

    touch .env

Füge folgende Variablen hinzu

    # Active Directory Konfiguration
    AD_DOMAIN=tgm.ac.at
    AD_URL=ldap://dc-01.tgm.ac.at:389
    AD_USERNAME=dein-username
    AD_PASSWORD=dein-passwort
    
    # Spring Profile
    SPRING_PROFILES_ACTIVE=social

Container starten

Starte alle Services im Hintergrund:

    docker compose up -d


## Recherche

Gegenüberstellung von Authentifikationssystemen mit folgenden Anforderungen:
- Anbindung an bestehende Authentifikations-Services
- Token-Authentifizierung für Single Sign-On
- Plattformübergreifende Verwendung (Web, Mobile, Desktop)



## 1. JWT (JSON Web Tokens)

**Zusammenfassung:** Kompakter, selbst-enthaltender Token zur Übertragung von Benutzerinformationen als signiertes JSON-Objekt.

**Vorteile:**
- Stateless - keine serverseitige Session-Speicherung
- Plattformunabhängig und kompakt
- Horizontal skalierbar

**Nachteile:**
- Keine native Token-Revocation vor Ablauf
- Token-Größe bei vielen Claims problematisch

**Einsatzgebiet:** RESTful APIs, Microservices, SPAs, Mobile Apps

**Anforderungserfüllung:** ✅ Service-Integration | ✅ Token-SSO | ✅ Multi-Device

**Quelle:** RFC 7519 - https://datatracker.ietf.org/doc/html/rfc7519

---

## 2. OAuth 2.0

**Zusammenfassung:** Autorisierungs-Framework für delegierten Zugriff ohne Passwort-Weitergabe an Drittanwendungen.

**Vorteile:**
- Keine Passwort-Speicherung notwendig
- Etablierte Provider (Google, Facebook, GitHub)
- Granulare Berechtigungen durch Scopes

**Nachteile:**
- Komplexe Implementierung
- Abhängigkeit von externen Providern
- Datenschutzbedenken

**Einsatzgebiet:** Social Login, Public APIs, Enterprise SSO

**Anforderungserfüllung:** ✅ Service-Integration | ✅ Token-SSO | ✅ Multi-Device

**Quelle:** RFC 6749 - https://datatracker.ietf.org/doc/html/rfc6749

---

## 3. SAML 2.0

**Zusammenfassung:** XML-basierter Standard für Authentifizierungs- und Autorisierungsdaten, hauptsächlich in Enterprise-Umgebungen.

**Vorteile:**
- Enterprise-Standard mit starker Sicherheit
- Federation zwischen Organisationen möglich
- Detaillierter Attribute-Austausch

**Nachteile:**
- XML-Komplexität, nicht mobil-freundlich
- Große Payload-Größe
- Legacy-Technologie

**Einsatzgebiet:** Enterprise SSO, B2B-Föderationen, Bildungseinrichtungen

**Anforderungserfüllung:** ✅ Service-Integration | ⚠️ Token-SSO | ❌ Multi-Device

**Quelle:** OASIS SAML 2.0 - https://docs.oasis-open.org/security/saml/

---

## 4. OpenID Connect (OIDC)

**Zusammenfassung:** Authentifizierungs-Layer auf OAuth 2.0 Basis, kombiniert Authorization mit standardisierter Identität.

**Vorteile:**
- Moderne Alternative zu SAML
- ID Token (JWT) für Identitätsinformationen
- Discovery und standardisierte Endpoints

**Nachteile:**
- Neuerer Standard, weniger verbreitet als OAuth
- Provider-Abhängigkeit
- Zusätzliche Komplexität zu OAuth

**Einsatzgebiet:** Moderne Web/Mobile Apps, Cloud-native Anwendungen

**Anforderungserfüllung:** ✅ Service-Integration | ✅ Token-SSO | ✅ Multi-Device

**Quelle:** OpenID Connect Core 1.0 - https://openid.net/specs/openid-connect-core-1_0.html

---

## 5. LDAP / Active Directory

**Zusammenfassung:** Verzeichnisdienst für zentrale Benutzerverwaltung in Unternehmensnetzwerken mit hierarchischer Struktur.

**Vorteile:**
- Zentrale Verwaltung aller Benutzer
- Windows-Integration und bewährte Technologie
- Gruppenverwaltung für Rollen

**Nachteile:**
- Nicht web-nativ, Netzwerkzugriff notwendig
- Komplex in Administration
- Nicht token-basiert

**Einsatzgebiet:** Intranet, Enterprise-Software, On-Premise Anwendungen

**Anforderungserfüllung:** ✅ Service-Integration | ❌ Token-SSO | ⚠️ Multi-Device

**Quelle:** RFC 4511, Microsoft AD Docs - https://learn.microsoft.com/en-us/windows-server/identity/ad-ds/

---

## 6. API Keys

**Zusammenfassung:** Statische Schlüssel zur einfachen Identifizierung von Anwendungen bei API-Aufrufen.

**Vorteile:**
- Sehr einfache Implementierung
- Stateless und leichtgewichtig
- Ideal für Service-to-Service

**Nachteile:**
- Keine User-Identität
- Schwierige Rotation und Revocation
- Keine automatische Expiration

**Einsatzgebiet:** Public APIs, Webhooks, IoT-Geräte

**Anforderungserfüllung:** ⚠️ Service-Integration | ❌ Token-SSO | ✅ Multi-Device

**Quelle:** Google API Key Best Practices - https://cloud.google.com/docs/authentication/api-keys

---

## 7. Session-based (Cookies)

**Zusammenfassung:** Traditionelle Methode mit serverseitiger Session-Speicherung und Cookie-basierter Session-ID.

**Vorteile:**
- Einfach zu verstehen und implementieren
- Serverseitige Kontrolle über Sessions
- Framework-Support überall verfügbar

**Nachteile:**
- Nicht skalierbar (Sticky Sessions)
- CSRF-anfällig, nicht für APIs geeignet
- Nicht stateless

**Einsatzgebiet:** Monolithische Web-Apps, SSR-Anwendungen

**Anforderungserfüllung:** ❌ Service-Integration | ❌ Token-SSO | ⚠️ Multi-Device

**Quelle:** OWASP Session Management - https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html


## Deployment-Konfiguration: LDAP und OAuth2 Integration
Übersicht der Authentifizierungsmethoden

Das Projekt unterstützt drei Authentifizierungsmodi:

    Active Directory (LDAP) - Unternehmensverzeichnis-Authentifizierung

    OAuth2 Social Login - Google und GitHub

    Embedded LDAP - Test-Modus ohne echtes AD
Füge in pom.xml die erforderlichen Dependencies hinzu:
Wichtig: Die Dependency-Reihenfolge kann bei LDAP-Konflikten relevant sein.
### Active Directory (LDAP) Konfiguration

Erstelle eine src/main/resources/application.properties mit den gleichen werten wie im repo.
Parameter Erklärung:

    ad.domain: Die Active Directory Domain (z.B. company.com)

    ad.url: LDAP-Server URL mit Port (Standard: 389 für LDAP, 636 für LDAPS)

### Profile-spezifische Konfiguration
Erstelle src/main/resources/application-ad.properties für Production:
Die Werte werden aus Umgebungsvariablen geladen (siehe Docker-Konfiguration).


Erstelle eine SecurityConfig wie, src/main/java/com/oliwier/securityconceptsek/config/SecurityConfig.java
Wichtige Konfigurationen:

    ActiveDirectoryLdapAuthenticationProvider: Spring Security Provider für AD-Authentifizierung

    setConvertSubErrorCodesToExceptions(true): Detaillierte Fehlerbehandlung

    setUseAuthenticationRequestCredentials(true): Nutzt Username/Passwort aus Login-Formular

### AD Connection Health Check
Erstelle AdStartupCheck.java zur Überprüfung der LDAP-Verbindung beim Start

### OAuth2 Social Login Konfiguration
Google OAuth2 einrichten:

    Gehe zur Google Cloud Console

    Erstelle ein neues Projekt

    Aktiviere "Google+ API"

    Navigiere zu "APIs & Services" → "Credentials"

    Erstelle "OAuth 2.0 Client ID"

        Application type: Web application

        Authorized redirect URIs: http://localhost:8080/login/oauth2/code/google

    Notiere Client ID und Client Secret


GitHub OAuth2 einrichten:

    Gehe zu GitHub Settings → Developer settings → OAuth Apps

    Klicke "New OAuth App"

    Fülle aus:

        Application name: SecurityConceptsEK

        Homepage URL: http://localhost:8080

        Authorization callback URL: http://localhost:8080/login/oauth2/code/github

    Notiere Client ID und Client Secret

### OAuth2 Properties konfigurieren
Erstelle eine File wie src/main/resources/application-social.properties:

Scope Erklärungen:

Google:

    openid: OpenID Connect Support

    profile: Zugriff auf Basis-Profildaten

    email: Zugriff auf E-Mail-Adresse

GitHub:

    read:user: Lesen öffentlicher Benutzerdaten

    user:email: Zugriff auf E-Mail-Adressen

### Custom OAuth2 User Service implementieren und OAuth2 in SecurityConfig integrieren
Erstelle eine Files wie CustomOAuth2UserService.java und SecurityConfig.java:

### Environment-Variablen für Deployment
Erstelle .env folgende Werte werden gebraucht:
```# Active Directory / LDAP Configuration
AD_DOMAIN=tgm.ac.at
AD_URL=ldap://dc-01.tgm.ac.at:389

# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=your-google-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-google-client-secret

# GitHub OAuth2 Credentials
GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRET=your-github-client-secret

# Spring Profile (ad, social, ad-test)
SPRING_PROFILES_ACTIVE=social
```
darauf hin muss eine docker-compose.yml erstellt werden.

### CORS Konfiguration für Frontend
Erstelle CorsConfig.java
Wichtig für Production:

    java .allowedOrigins("https://yourdomain.com")



## Quellenverzeichnis

[1] M. Jones, J. Bradley, and N. Sakimura, "JSON Web Token (JWT)," RFC 7519, Internet Engineering Task Force (IETF), May 2015. [Online]. Available: https://datatracker.ietf.org/doc/html/rfc7519

[2] D. Hardt, Ed., "The OAuth 2.0 Authorization Framework," RFC 6749, Internet Engineering Task Force (IETF), Oct. 2012. [Online]. Available: https://datatracker.ietf.org/doc/html/rfc6749

[3] S. Cantor, J. Kemp, R. Philpott, and E. Maler, "Assertions and Protocols for the OASIS Security Assertion Markup Language (SAML) V2.0," OASIS Standard, Mar. 2005. [Online]. Available: https://docs.oasis-open.org/security/saml/

[4] N. Sakimura, J. Bradley, M. Jones, B. de Medeiros, and C. Mortimore, "OpenID Connect Core 1.0 incorporating errata set 1," OpenID Foundation, Nov. 2014. [Online]. Available: https://openid.net/specs/openid-connect-core-1_0.html

[5] J. Sermersheim, Ed., "Lightweight Directory Access Protocol (LDAP): The Protocol," RFC 4511, Internet Engineering Task Force (IETF), Jun. 2006. [Online]. Available: https://datatracker.ietf.org/doc/html/rfc4511

[6] Microsoft Corporation, "Active Directory Domain Services Overview," Microsoft Learn. [Online]. Available: https://learn.microsoft.com/en-us/windows-server/identity/ad-ds/. [Accessed: Jan. 15, 2026].

[7] OWASP Foundation, "Authentication Cheat Sheet," OWASP Cheat Sheet Series. [Online]. Available: https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html. [Accessed: Jan. 15, 2026].

[8] VMware, Inc., "Spring Security," Spring Projects. [Online]. Available: https://spring.io/projects/spring-security. [Accessed: Jan. 15, 2026].
