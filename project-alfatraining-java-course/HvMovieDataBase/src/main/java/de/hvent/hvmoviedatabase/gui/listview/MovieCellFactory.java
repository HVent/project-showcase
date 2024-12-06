package de.hvent.hvmoviedatabase.gui.listview;

import de.hvent.hvmoviedatabase.model.Movie;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Handelt mit einer eigenen
 * Zelle den Aufbau und die Anzeige
 * der ListViewItems welche {@link de.hvent.hvmoviedatabase.model.Movie} enthalten
 */
public class MovieCellFactory implements Callback<ListView<Movie>, ListCell<Movie>> {

    @Override
    public ListCell<Movie> call(ListView<Movie> movieListView) {
        return new MovieListViewCell();
    }
}
