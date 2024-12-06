Film-Datenbank JavaFX
---------------------

Übersicht
----------

Diese JavaFX-Anwendung stellt eine einfache Film-Datenbank dar, in der
Nutzer Filme hinzufügen, bearbeiten, löschen und anzeigen können. Die
Anwendung nutzt eine MariaDB-Datenbank, um die Filme zu speichern und
zu verwalten.

Das Projekt stellt die Abschlussarbeit im 8-wöchigen Kurs Java-Entwickler 
von alfatraining Bildungszentrum GmbH dar. Der Bearbeitungszeitraum be-
trägt 2 Wochen, vom 25.11.2024 - 06.12.2024

Voraussetzungen
---------------

Bevor du mit der Nutzung der Anwendung beginnst, stelle sicher, dass
folgende Voraussetzungen erfüllt sind:

1. **Java Development Kit (JDK)**:
   - Die Anwendung wurde mit JDK 11 oder höher entwickelt. Du
     benötigst ein JDK, um das Projekt auszuführen oder zu bauen.
   - Lade das JDK von https://adoptopenjdk.net/ herunter, falls es noch
     nicht installiert ist.

2. **MariaDB**:
   - MariaDB ist die verwendete Datenbanktechnologie. Du benötigst eine
     lokale MariaDB-Datenbank, um die Film-Daten zu speichern.
   - Falls MariaDB noch nicht installiert ist, lade es von der
     offiziellen Website https://mariadb.org/ herunter und folge den
     Installationsanweisungen für dein Betriebssystem.

3. **JavaFX**:
   - Das Projekt nutzt JavaFX für die Benutzeroberfläche. Stelle sicher,
     dass du die JavaFX-Bibliotheken in deinem Projekt eingebunden hast.
   - Du kannst JavaFX von https://openjfx.io/ herunterladen, falls es noch
     nicht eingebunden ist.

Einrichten des Projekts
------------------------

1. **Datenbank einrichten**

   Bevor du die Anwendung startest, musst du die MariaDB-Datenbank mit
   der entsprechenden Struktur und den Beispieldaten einrichten.

   a) **Erstelle die Datenbank und Tabelle**

   1. Lade die SQL-Datei (`db_movies.sql`) herunter und speichere sie
      auf deinem Computer.
   2. Öffne phpMyAdmin oder ein anderes MariaDB-Client-Tool (wie
      DBeaver oder HeidiSQL).
   3. Erstelle eine neue Datenbank mit dem Namen `db_movies`.
   4. Führe das SQL-Skript aus, um die Tabelle `movies` zu erstellen und
      mit Beispieldaten zu füllen.

   Der Inhalt der SQL-Datei sorgt dafür, dass:
      - Eine Datenbank `db_movies` erstellt wird.
      - Eine Tabelle `movies` erstellt wird, die vier Spalten enthält:
        `id`, `title`, `releaseYear`, und `genre`.
      - Einige Beispiel-Daten in die Tabelle eingefügt werden.

   b) **Verbindung zur Datenbank**

   Stelle sicher, dass die Datenbankverbindung in der Anwendung korrekt
   konfiguriert ist. Die Verbindungseinstellungen befinden sich in der
   Klasse `DbManager`:

- **DB_LOCAL_SERVER_IP_ADDRESS**: Stelle sicher, dass die IP-Adresse
  und der Port korrekt sind (standardmäßig `localhost/`).
- **DB_LOCAL_NAME**: Der Name der Datenbank muss `db_movies` sein, wie
  in der SQL-Datei.
- **DB_LOCAL_USER_NAME** und **DB_LOCAL_USER_PW**: Setze den
  Datenbankbenutzernamen und das Passwort, die du bei der
  Installation von MariaDB festgelegt hast.

2. **Projekt einrichten**

1. Klone oder lade das Projekt von deinem Repository herunter.
2. Importiere das Projekt in deiner bevorzugten Java-IDE (z.B.
   IntelliJ IDEA, Eclipse).
3. Stelle sicher, dass alle Abhängigkeiten (z.B. JavaFX und MariaDB
   JDBC-Treiber) korrekt in deinem Projekt eingebunden sind. Du kannst
   die Abhängigkeiten manuell hinzufügen oder eine `pom.xml` (für
   Maven) oder `build.gradle` (für Gradle) verwenden.

**Für Maven**: Beispielabhängigkeiten für `pom.xml`:

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>23.0.1</version>
    </dependency>

    <!-- MariaDB JDBC -->
    <dependency>
        <groupId>org.mariadb.jdbc</groupId>
        <artifactId>mariadb-java-client</artifactId>
        <version>3.5.1</version>
    </dependency>
</dependencies>

Angaben zum Author
------------------------
- **LinkedIn**: [Heinrich Vent](https://www.linkedin.com/in/heinrich-vent-231a88266/)
- **Email**: ventheinrich@gmail.com
