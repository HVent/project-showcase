package de.hvent.hvmoviedatabase.logic.holder;

import de.hvent.hvmoviedatabase.logic.collection.Movies;
import de.hvent.hvmoviedatabase.logic.db.DbManager;
import de.hvent.hvmoviedatabase.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Diese Klasse ist ein Singleton und verwaltet alle {@link Movie}-Objekte in einer {@link ObservableList}.
 * Sie stellt sicher, dass die Liste von Filmen thread-sicher und immer auf dem neuesten Stand ist.
 *
 * <p>Die {@link MovieHolder}-Klasse dient als zentrale Stelle für die Verwaltung der Filmobjekte.
 * Sie holt die Liste der Filme aus der Datenbank und stellt sie als {@link ObservableList} zur Verfügung,
 * die für die Verwendung in JavaFX-Anwendungen optimiert ist.</p>
 */
public class MovieHolder {

    //region Decl und Init Attribute
    /**
     * Die einzige Instanz von {@link MovieHolder} (Singleton).
     */
    private static MovieHolder instance;

    private ObservableList<Movie> allMovies;
    //endregion

    //region Konstruktoren
    /**
     * Der private Konstruktor für {@link MovieHolder}.
     * Ruft beim Erstellen der Instanz die Methode {@link MovieHolder#updateAllMovies()} auf,
     * um die Film-Liste aus der Datenbank zu laden.
     */
    private MovieHolder() { updateAllMovies(); }
    //endregion

    //region Getter und Setter
    /**
     * Gibt die einzige Instanz von {@link MovieHolder} zurück.
     * (Singleton-Pattern)
     *
     * @return instance : {@link MovieHolder} : Die einzige Instanz von {@link MovieHolder}.
     */
    public static synchronized MovieHolder getInstance() {
        if (instance == null) {
            instance = new MovieHolder();
        }
        return instance;
    }

    /**
     * Lädt alle Filme aus der Datenbank und aktualisiert die {@link ObservableList} der Filme.
     * Diese Methode wird an verschiedenen Stellen im Programmablauf aufgerufen, um sicherzustellen, dass die Liste der Filme aktuell ist.
     */
    public void updateAllMovies() {
        Movies allMovies = DbManager.getInstance().getAllMoviesFromDb();
        initializeAllMovies(allMovies);
    }

    /**
     * Initialisiert die {@link ObservableList} der Filme mit den angegebenen Filmen.
     *
     * @param allMovies die {@link Movies}-Liste, die die Filme enthält.
     */
    private void initializeAllMovies(Movies allMovies) {
        this.allMovies = FXCollections.observableList(allMovies);
    }

    /**
     * Gibt die {@link ObservableList} der Filme zurück.
     *
     * @return allMovies : {@link ObservableList} : Eine Liste aller {@link Movie}-Objekte.
     */
    public ObservableList<Movie> getAllMovies() { return allMovies; }
    //endregion
}
