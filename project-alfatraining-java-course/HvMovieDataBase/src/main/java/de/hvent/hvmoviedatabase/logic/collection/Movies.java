package de.hvent.hvmoviedatabase.logic.collection;

import de.hvent.hvmoviedatabase.model.Movie;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert eine Sammlung von {@link Movie}-Objekten und erweitert {@link ArrayList}.
 * Sie bietet zusätzliche Funktionalitäten zum Filtern und Suchen von Filmen basierend auf deren Attributen.
 * Die Klasse nutzt Java Streams, um fortschrittliche Filteroperationen auf die Liste von Filmen anzuwenden.
 *
 * <p>Die {@code Movies}-Klasse ist speziell auf die Verwaltung und Suche von {@code Movie}-Objekten ausgelegt.
 * Sie ermöglicht das Filtern nach bestimmten Kriterien, wie beispielsweise der ID eines Films.</p>
 */
public class Movies extends ArrayList<Movie> {

    //region Movie By Attribute Suchen
    /**
     * Sucht Filme anhand ihrer ID.
     * <p>
     * Diese Methode filtert die Liste der Filme und gibt nur diejenigen zurück, deren {@link Movie#getId()}
     * mit der angegebenen ID übereinstimmt. Die Methode nutzt Java Streams und gibt eine neue Instanz
     * von {@code Movies} zurück, die nur die gefilterten Filme enthält.
     * </p>
     *
     * @param id Die ID des Films, nach dem gesucht werden soll.
     * @return Eine neue {@link Movies}-Liste, die nur Filme mit der angegebenen ID enthält.
     * @see Movie#getId()
     */
    public Movies getMoviesById(int id) {
        return this.stream()
                .filter(movie -> movie.getId() == id)
                .collect(Collectors.toCollection(Movies::new));
    }
    //endregion

}
