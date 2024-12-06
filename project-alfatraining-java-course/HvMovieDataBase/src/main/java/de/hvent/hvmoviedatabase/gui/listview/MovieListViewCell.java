package de.hvent.hvmoviedatabase.gui.listview;

import de.hvent.hvmoviedatabase.model.Movie;
import javafx.scene.control.ListCell;

/**
 * Repräsentiert eine Zelle die {@link Movie} enthält,
 * welche aufgebaut wird und die Aktualisierung
 * des Inhalts vornimmt.
 */
public class MovieListViewCell extends ListCell<Movie> {

    /**
     * Zeigt entweder nichts an oder den {@link Movie} der mitgeliefert wird.
     * Dieser kommt von der {@link javafx.scene.control.ListView} selbst.
     *
     * @param movieToShow : {@link Movie} : Movie der angezeigt werden soll
     * @param empty            : boolean : gibt an ob die Zelle / ListViewItem leer sein soll.
     */
    @Override
    protected void updateItem(Movie movieToShow, boolean empty) {
        super.updateItem(movieToShow, empty);

        if (empty || movieToShow == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(movieToShow.getListCellInformation());
        }
    }
}

