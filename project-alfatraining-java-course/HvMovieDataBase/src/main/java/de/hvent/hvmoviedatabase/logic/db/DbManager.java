package de.hvent.hvmoviedatabase.logic.db;

import de.hvent.hvmoviedatabase.logic.collection.Movies;
import de.hvent.hvmoviedatabase.model.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;

/**
 * Diese Klasse verwaltet die Datenbankverbindung und stellt CRUD-Operationen für Filme zur Verfügung.
 * Sie ist als Singleton implementiert, um sicherzustellen, dass nur eine Instanz dieser Klasse existiert.
 *
 * <p>Der {@link DbManager} ermöglicht den Zugriff auf eine MariaDB-Datenbank, um Filme zu verwalten.</p>
 *
 * <p>Die Klasse verwaltet die Verbindung zur Datenbank und nutzt das {@link DaoMovies}-Objekt,
 * um CRUD-Operationen (Create, Read, Update, Delete) für die {@code movies}-Tabelle durchzuführen.</p>
 */
public class DbManager {

    //region Konstanten
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private static final String DB_LOCAL_SERVER_IP_ADDRESS = "localhost/";
    private static final String DB_LOCAL_NAME              = "db_movies";

    private static final String DB_LOCAL_CONNECTION_URL =
            "jdbc:mariadb://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;

    private static final String DB_LOCAL_USER_NAME = "root";
    private static final String DB_LOCAL_USER_PW   = "";


    //endregion

    //region Decl. and Init Attribute
    /**
     * Die einzige Instanz von {@link DbManager} (Singleton).
     */
    private static DbManager      instance;

    /**
     * Der DAO für die Verwaltung der {@code movies}-Tabelle.
     */
    private        DaoMovies daoMovies;
    //endregion

    //region Konstruktoren

    /**
     * Standardkonstruktor für {@link DbManager}.
     * Initialisiert das {@link DaoMovies}-Objekt.
     */
    private DbManager() {
        this.daoMovies = new DaoMovies();
    }

    //endregion

    //region Get Instance

    /**
     * Gibt die einzige Instanz von {@link DbManager} zurück.
     * (Singleton-Pattern)
     *
     * @return instance : {@link DbManager} : Die einzige Instanz von {@link DbManager}.
     */
    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }

        return instance;
    }

    //endregion

    //region Database Connection

    /**
     * Stellt eine Verbindung zur Datenbank her und gibt eine {@link Connection} mit Lese- und Schreibrechten zurück.
     *
     * <p>Wenn ein Fehler auftritt, wird eine {@link Exception} geworfen.</p>
     *
     * @return rwDbConnection : {@link Connection} : Die Verbindung zur Datenbank.
     * @throws Exception wenn ein Fehler beim Herstellen der Verbindung auftritt.
     */
    private Connection getRwDbConnection() throws Exception {
        Connection rwDbConnection = null;

        try {
            //: Registrieren des JDBC driver
            Class.forName(JDBC_DRIVER);

            //2. Öffnen einer Verbindung
            rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);

        } catch (SQLNonTransientConnectionException sqlNoConnectionEx) {
            throw new Exception("Keine Datenbankverbindung");
        } catch (ClassNotFoundException classNotFoundEx) {
            throw new Exception("JDBC Treiber konnte nicht geladen werden - org.mariadb.jdbc:mariadb-java-client:3.5.1 als maven- dependency nicht eingebunden");
        }

        return rwDbConnection;
    }


    /**
     * Checkt, ob die Datenbank online ist oder nicht,
     * indem die Verbindung über {@link DbManager#getRwDbConnection()} geöffnet und direkt
     * wieder mit {@link DbManager#getRwDbConnection().close()} geschlossen wird.
     * Gibt es eine Fehlermeldung wird false zurückgegeben
     * und die Datenbank ist nicht online dann ist das die {@link SQLNonTransientConnectionException}
     * oder durch den fehlenden JDBC Treiber {@link ClassNotFoundException},
     * hier ist dann die maven-dependency org.mariadb.jdbc:mariadb-java-client:3.5.1 nicht zum
     * Projekt hinzugefügt worden.
     *
     * @return isOnline : boolean : true : Dbist Online : false nicht
     */
    public boolean isDatabaseOnline() {
        boolean isOnline = true;
        try {
            this.getRwDbConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            isOnline = false;
        }
        return isOnline;
    }
    //endregion


    //region 5. CRUD -Operationen Movie
    /**
     * Fügt einen einzelnen {@link Movie} in die Datenbanktabelle ein.
     *
     * @param movieToInsert das {@link Movie}-Objekt, das eingefügt werden soll.
     */
    public void insertMovieIntoDbTbl(Movie movieToInsert) {

        try {
            if (this.isDatabaseOnline()) {
                this.daoMovies.insertDataRecordIntoDbTbl(this.getRwDbConnection(), movieToInsert);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gibt alle Filme aus der Datenbanktabelle zurück.
     *
     * @return allMovies : {@link Movies} : Eine Liste von {@link Movie}-Objekten.
     */
    public Movies getAllMoviesFromDb() {
        Movies allMovies = new Movies();

        try {
            if (this.isDatabaseOnline()) {
                allMovies = this.daoMovies.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return allMovies;
    }

    /**
     * Aktualisiert einen bestimmten {@link Movie} in der Datenbanktabelle.
     *
     * @param MovieToUpdate das {@link Movie}-Objekt, das aktualisiert werden soll.
     */
    public void updateMovieInDbTbl(Movie MovieToUpdate) {
        try {
            if (this.isDatabaseOnline()) {
                //Neue Verbindung erstellen
                this.daoMovies.updateDataRecordIntoDbTbl(this.getRwDbConnection(), MovieToUpdate);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Löscht einen bestimmten Film aus der Datenbank anhand der ID.
     *
     * @param id die ID des Films, der gelöscht werden soll.
     */
    public void deleteMovieInDbTblById(int id) {

        try {
            if (this.isDatabaseOnline()) {
                this.daoMovies.deleteDataRecordInDbTblById(this.getRwDbConnection(), id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //endregion

}
