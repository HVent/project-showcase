package de.hvent.hvmoviedatabase.logic.db;

import de.hvent.hvmoviedatabase.logic.collection.Movies;
import de.hvent.hvmoviedatabase.model.Movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Diese Klasse ist ein Data Access Object (DAO) für die Verwaltung von {@link Movie}-Objekten in der Datenbank.
 * Sie führt CRUD-Operationen (Create, Read, Update, Delete) auf der Tabelle {@code movies} aus.
 *
 * <p>Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Abrufen und Löschen von Filmen aus der Datenbank.</p>
 */
public class DaoMovies extends SqlKeyWords {
    //region Konstanten
    protected static final String COLUMN_LABEL_ID = "Id";
    protected static final String COLUMN_NAME_ID =
            BACK_TICK + COLUMN_LABEL_ID + BACK_TICK;

    protected static final String COLUMN_LABEL_TITLE = "title";
    protected static final String COLUMN_NAME_TITLE =
            BACK_TICK + COLUMN_LABEL_TITLE + BACK_TICK;

    protected static final String COLUMN_LABEL_RELEASE_YEAR= "releaseYear";
    protected static final String COLUMN_NAME_RELEASE_YEAR =
            BACK_TICK + COLUMN_LABEL_RELEASE_YEAR + BACK_TICK;

    protected static final String COLUMN_LABEL_GENRE = "genre";
    protected static final String COLUMN_NAME_GENRE =
            BACK_TICK + COLUMN_LABEL_GENRE + BACK_TICK;

    private static final String TABLE_NAME_MOVIES = "movies";
    //endregion

    //region Decl. and Init Attribute
    //endregion

   //region Konstruktor
    /**
     * Konstruktor für die {@link DaoMovies}-Klasse.
     * Dieser Konstruktor ist standardmäßig ohne Funktionalität, kann aber für zukünftige Erweiterungen genutzt werden.
     */
    public DaoMovies() {}
    //endregion

    //region Insert

    /**
     * Fügt einen einzelnen {@link Movie} in die {@code movies}-Datenbanktabelle ein.
     *
     * @param dbRwConnection         die {@link Connection} zum Zugriff auf die Datenbank
     * @param movieToInsertIntoDbTable das {@link Movie}-Objekt, das eingefügt werden soll
     */
    public void insertDataRecordIntoDbTbl(Connection dbRwConnection, Movie movieToInsertIntoDbTable) {

        if (movieToInsertIntoDbTable instanceof Movie movieToInsert) {

            Statement insertStatementToExecute = null;

            try {
                //1. Db Connection ist bereits von DbManager generiert

                //2. Statementobjekt zum tatsächlichen Ausführen des unten als String generierten SQL-Statements
                insertStatementToExecute = dbRwConnection.createStatement();

                //3. SQL-String angepasst auf die Tabelle generieren
                String insertStatementText = createInsertStatementText(movieToInsert);

                //DEBUG
                System.out.println("[DEBUG] " + insertStatementText);

                //4. SQL-String ein Statement-Objekt zum Ausführen geben
                insertStatementToExecute.execute(insertStatementText);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (insertStatementToExecute != null) {
                    try {
                        //5. Schliessen des Statements
                        insertStatementToExecute.close();
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }

                if (dbRwConnection != null) {
                    try {
                        //6. Schliessen der Verbindung
                        dbRwConnection.close();
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * Erzeugt den SQL-Insert-Statement-Text für ein {@link Movie}-Objekt.
     *
     * @param movieToInsert das {@link Movie}-Objekt, für das der SQL-Text erzeugt werden soll
     * @return der SQL-Insert-Statement-Text
     */
    private static String createInsertStatementText(Movie movieToInsert) {
        return INSERT_TBL + TABLE_NAME_MOVIES + CHAR_OPEN_PARENTHESIS
                + COLUMN_NAME_ID + COMMA
                + COLUMN_NAME_TITLE + COMMA
                + COLUMN_NAME_RELEASE_YEAR + COMMA
                + COLUMN_NAME_GENRE
                + CHAR_CLOSE_PARENTHESIS
                + VALUES_OPERATOR + CHAR_OPEN_PARENTHESIS
                + SINGLE_QUOTE + movieToInsert.getId() + SINGLE_QUOTE + COMMA
                + SINGLE_QUOTE + movieToInsert.getTitle() + SINGLE_QUOTE + COMMA
                + SINGLE_QUOTE + movieToInsert.getReleaseYear() + SINGLE_QUOTE + COMMA
                + SINGLE_QUOTE + movieToInsert.getGenre() + SINGLE_QUOTE
                + CHAR_CLOSE_PARENTHESIS_SEMICOLON;
    }

    //endregion

    //region Update

    /**
     * Aktualisiert einen einzelnen Datensatz in der {@code movies}-Datenbanktabelle.
     *
     * @param dbRwConnection         die {@link Connection} zum Zugriff auf die Datenbank
     * @param movieToUpdateInDbTable das {@link Movie}-Objekt, das aktualisiert werden soll
     */
    public void updateDataRecordIntoDbTbl(Connection dbRwConnection, Movie movieToUpdateInDbTable) {

        if (movieToUpdateInDbTable instanceof Movie) {

            Movie movieToUpdate = movieToUpdateInDbTable;

            Statement updateStatementToExecute = null;

            try {
                //1. Db Connection ist bereits vom DbManager geöffnet worden

                //2. Statement-Objekt generieren lassen
                updateStatementToExecute = dbRwConnection.createStatement();

                String updateStatementText = createUpdateStatementText(movieToUpdate);

                //DEBUG
                System.out.println("[DEBUG] updateDataRecordIntoDbTbl() " + updateStatementText);

                //4. SQL - String an Statement objekt zum Ausführen geben
                updateStatementToExecute.executeUpdate(updateStatementText);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (updateStatementToExecute != null) {
                    //5. Schliessen der des Statements
                    try {
                        updateStatementToExecute.close();
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }

                if (dbRwConnection != null) {
                    //6. Schliessen der Verbindung
                    try {
                        dbRwConnection.close();
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * Erzeugt den SQL-Update-Statement-Text für ein {@link Movie}-Objekt.
     *
     * @param movieToUpdate das {@link Movie}-Objekt, für das der SQL-Text erzeugt werden soll
     * @return der SQL-Update-Statement-Text
     */
    private static String createUpdateStatementText(Movie movieToUpdate) {
        return UPDATE_TBL + TABLE_NAME_MOVIES
                + SET
                + COLUMN_NAME_TITLE + EQUALS
                + SINGLE_QUOTE + movieToUpdate.getTitle() + SINGLE_QUOTE + COMMA
                + COLUMN_NAME_RELEASE_YEAR + EQUALS
                + SINGLE_QUOTE + movieToUpdate.getReleaseYear() + SINGLE_QUOTE + COMMA
                + COLUMN_NAME_GENRE + EQUALS
                + SINGLE_QUOTE + movieToUpdate.getGenre() + SINGLE_QUOTE
                + WHERE_CONDITION + COLUMN_NAME_ID + EQUALS + movieToUpdate.getId() + CHAR_SEMICOLON;
    }
//endregion


//region Read

    /**
     * Gibt alle Datensätze aus der {@code movies}-Datenbanktabelle zurück und liefert sie als Liste von {@link Movie}-Objekten.
     *
     * @param dbRwConnection die {@link Connection} zum Zugriff auf die Datenbank
     * @return eine Liste von {@link Movie}-Objekten, die alle Datensätze der Tabelle repräsentieren
     */
    public Movies getAllDataRecordsFromDbTbl(Connection dbRwConnection) {

        //Decl. and Init
        Movies movies = new Movies();

        Statement dbStatementToExecute = null;

        try {
            //1. Rw Db Connection ist bereits vom DbManger geöffnet und Integriert

            //2. Generieren des Statements
            dbStatementToExecute = dbRwConnection.createStatement();

            //3. Query generieren und absetzen und Ergebnismenge merken
            String getAllDataRecordsStatement = SELECT_ALL_DATA_FROM + TABLE_NAME_MOVIES;

            ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(getAllDataRecordsStatement);

            //4. ResultSet == Ergebnismenge durchlaufen bis kein Datensätze mehr da sind
            while (resultSetFromExecutedQuery.next()) {

                //5. Aus der Ergebnismenge einen User beschafften
                Movie movieFromDbTable = this.getModelFromResultSet(resultSetFromExecutedQuery);

                //6. Model-Objekt zur passenden Liste adden
                movies.add(movieFromDbTable);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (dbStatementToExecute != null) {
                //5. Schliessen der des Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbRwConnection != null) {
                //6. Schliessen der Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }

        return movies;
    }


//endregion


//region Delete

    /**
     * Löscht einen bestimmten Datensatz aus der {@code movies}-Datenbanktabelle anhand der ID.
     *
     * @param dbRwConnection die {@link Connection} zum Zugriff auf die Datenbank (Read / Write)
     * @param id die ID des Datensatzes, der gelöscht werden soll
     */
    public void deleteDataRecordInDbTblById(Connection dbRwConnection, int id) {
        Statement dbStatementToExecute = null;

        try {
            //1 Db Connection bereits vom DbManager geöffnet

            //2. Generieren des Statements
            dbStatementToExecute = dbRwConnection.createStatement();


            //"DELETE FROM `kanban_Movies` WHERE `id` = " + id;
            String deleteStatement = SqlKeyWords.DELETE_FROM_TBL + TABLE_NAME_MOVIES + WHERE_CONDITION
                    + COLUMN_NAME_ID
                    + SqlKeyWords.EQUALS + id;

            //DEBUG
            System.out.println("[DEBUG] deleteDataRecordInDbTblById() " + deleteStatement);

            dbStatementToExecute.executeUpdate(deleteStatement);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (dbStatementToExecute != null) {
                //5. Schliessen der des Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbRwConnection != null) {
                //6. Schliessen der Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }
    }
//endregion

//region Model aus ResultSet Formen

    /**
     * Wandelt die Ergebnismenge eines SQL-ResultSets in ein {@link Movie}-Objekt um.
     *
     * @param currentResultSet das {@link ResultSet}, das die Ergebnismenge der SQL-Abfrage enthält
     * @return das {@link Movie}-Objekt, das die Daten des aktuellen ResultSets repräsentiert
     * @throws Exception wenn ein Fehler beim Extrahieren der Daten aus dem ResultSet auftritt
     */
    protected Movie getModelFromResultSet(ResultSet currentResultSet) throws Exception {
        //Spaltenindizes auslesen
        final int columnIndexId           = currentResultSet.findColumn(COLUMN_LABEL_ID);
        final int columnIndexTitle         = currentResultSet.findColumn(COLUMN_LABEL_TITLE);
        final int columnIndexReleaseYear = currentResultSet.findColumn(COLUMN_LABEL_RELEASE_YEAR);
        final int columnIndexGenre  = currentResultSet.findColumn(COLUMN_LABEL_GENRE);

        //Daten aus Ergebnismenge extrahieren
        int    id           = currentResultSet.getInt(columnIndexId);
        String title         = currentResultSet.getString(columnIndexTitle);
        String releaseYear = currentResultSet.getString(columnIndexReleaseYear);
        String genre = currentResultSet.getString(columnIndexGenre);

        return new Movie(id, title, releaseYear, genre);
    }
    //endregion
}
