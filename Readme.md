## Betrieb
+ Es muss die Umgebungsvariable INVENTORYTRACKER_BASE_URL mit der URL, unter der die Anwendung später deployt wird gesetzt werden

### Benutzername und Passwort
Die Anwendung unterstützt nur einen einzigen globalen Nutzer. Der Nutzername muss über die Umgebungsvariable SPRING_SECURITY_USER_NAME (default: user) und das Passwort über die Umgebungsvariable SPRING_SECURITY_USER_PASSWORD (default: password) gesetzt werden.

### Speicherort für Bilder
Alle hochgeladenen Bilder werden standardmäßig im home Verzeichnis des Nutzers unter /inventory-manager-img/ gespeichert.