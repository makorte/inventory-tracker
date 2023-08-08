Der inventory-tracker ist ein Tool, das die Erfassung von Artikeln (Items) in einer Datenbank und die Generation von QR-Codes, um anschließend Details zu den erfassten Artikeln einzusehen, ermöglicht.

# Funktionen
+ Anlegen eines Artikels inkl. Upload von Bildern
+ Anzeige von Artikeln in einer Übersicht inkl. Freitext-Suche
+ Abrufen von Details zu einem angelegten Artikel
+ Download eines QR-Codes für jeden Artikel, der den Nutzer beim Scannen zu der Detail Ansicht des entsprechenden Artikels weiterleitet
+ Bearbeiten und Löschen von Artikeln
+ Login mit einem globalen Account

# Architektur und Technologien
+ Java
+ Spring Framework, Spring Boot
+ Spring MVC, Thymeleaf, JavaScript, Materialize CSS Framework
+ PostgreSQL
+ Spring Data JPA
+ Spring Security
+ Google ZXing zur Generierung von QR-Codes

## Betrieb
Es muss die Umgebungsvariable INVENTORYTRACKER_BASE_URL (default: http://localhost) mit der URL, unter der die Anwendung später deployt wird gesetzt werden.
Anschlißenend kann die Datenbank über die docker compose und anschließend die Java Anwendung gestartet werden.

### Benutzername und Passwort
Die Anwendung unterstützt nur einen einzigen globalen Nutzer. Der Nutzername muss über die Umgebungsvariable SPRING_SECURITY_USER_NAME (default: user) und das Passwort über die Umgebungsvariable SPRING_SECURITY_USER_PASSWORD (default: password) gesetzt werden.

### Speicherort für Bilder
Alle hochgeladenen Bilder werden standardmäßig im home Verzeichnis des Nutzers unter /inventory-manager-img/ gespeichert.