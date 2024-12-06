package de.hvent.hvmoviedatabase.gui.menu;

import de.hvent.hvmoviedatabase.gui.listview.MovieCellFactory;
import de.hvent.hvmoviedatabase.logic.db.DbManager;
import de.hvent.hvmoviedatabase.logic.holder.MovieHolder;
import de.hvent.hvmoviedatabase.model.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Der {@code Menu}-Controller verwaltet die Interaktion mit der Benutzeroberfläche
 * der Film-Datenbank. Er verarbeitet CRUD-Operationen (Erstellen, Lesen, Aktualisieren,
 * Löschen) auf den Film-Daten und zeigt die Film-Liste an.
 *
 * Die Klasse verwendet verschiedene importierte Klassen wie {@link Movie}, {@link MovieCellFactory},
 * {@link DbManager}, {@link MovieHolder}, {@link Alert}, und {@link TextField}, um eine
 * benutzerfreundliche Oberfläche für die Verwaltung von Filmen zu schaffen.
 */
public class Menu implements Initializable {

    //region Konstanten

    // Konstante Werte
    private static final int MINIMUM_VALUE_ID = 1;

    // Warnungen, Fehler und Exceptions
    private static final String WARNING_INVALID_USER_INPUT = "Ungültige Eingabe";
    private static final String ERROR_TITLE = "Fehler";
    private static final String ERROR_UNEXPECTED_TEXT = "Es ist ein unerwarteter Fehler aufgetreten: ";
    private static final String EXCEPTION_VOID_TITLE = "Der Titel darf nicht leer sein.";
    private static final String EXCEPTION_VOID_GENRE = "Das Genre darf nicht leer sein.";
    private static final String EXCEPTION_VOID_RELEASE_YEAR = "Das Erscheinungsjahr darf nicht leer sein.";
    private static final String EXCEPTION_VOID_ID = "Bitte geben Sie eine ID ein.";
    private static final String EXCEPTION_ID_BELOW_MINIMUM_START = "Bitte eine ID größer gleich ";
    private static final String EXCEPTION_ID_BELOW_MINIMUM_END = " eingeben!";
    private static final String ERROR_INVALID_ID_TEXT = "Die ID muss eine gültige Zahl sein.";
    private static final String WARNING_INVALID_INPUT_TITLE = "Ungültige Eingabe";
    private static final String ERROR_GENERAL_TEXT = "Es ist ein unerwarteter Fehler aufgetreten: ";
    private static final String EXCEPTION_ID_NOT_FOUND_END = " nicht gefunden.";

    // Information bei erfolgreicher Bearbeitung
    private static final String SUCCESS_TITLE = "Erfolg";
    private static final String MOVIE_WITH_ID_TEXT_START = "Film mit ID ";
    private static final String INFORMATION_DELETE_TEXT_END = " wurde gelöscht.";
    private static final String INFORMATION_UPDATED_SUCCESSFULLY_TEXT_END = " wurde erfolgreich aktualisiert.";
    private static final String ERROR_RELEASE_YEAR_NO_INT = "Die Eingabe für das Erscheinungsjahr\nist keine gültige Ganzzahl.";

    // Tooltips für CRUD-Buttons
    private static final String TOOLTIP_ADD_MOVIE = "Benötigte Eingabewerte:\nTitel, Erscheinungsjahr, Genre\n(Id wird automatisch generiert)";
    private static final String TOOLTIP_DELETE_MOVIE = "Benötigte Eingabewerte:\nId";
    private static final String TOOLTIP_EDIT_MOVIE = "Benötigte Eingabewerte:\nId\nOptionale Eingabewerte:\nTitel, Erscheinungsjahr, Genre";

    // Text für Programm beenden-Button
    private static final String CONFIRMATION_TITLE = "Bestätigung";
    private static final String CONFIRMATION_EXIT_TEXT = "Möchten Sie das Programm wirklich beenden?";

    //endregion

    //region Decl und Init Widgets / Attribute

    // Optionen
    // Eingabemaske
    @FXML
    private TextField idTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField releaseYearTextField;
    @FXML
    private TextField genreTextField;

    // Buttons
    @FXML
    private Button addMovieButton;

    @FXML
    private Button editMovieButton;

    @FXML
    private Button deleteMovieButton;

    //Film-Liste
    @FXML
    private ListView<Movie> movieListView;
    //endregion

    //region Initialize
    /**
     * Diese Methode wird beim Starten des Controllers aufgerufen, nachdem das Root-Element
     * vollständig verarbeitet wurde. Sie sorgt dafür, dass die Film-Liste angezeigt wird.
     * Sie verwendet die importierte Klasse {@link MovieHolder}, um alle Filme zu laden und darzustellen.
     *
     * Hier werden weiterhin Tooltips für die Buttons gesetzt, um dem Benutzer zusätzliche Informationen
     * zu den jeweiligen CRUD-Operationen zu geben.
     *
     * @param url The URL, die zur Auflösung relativer Pfade für das Root-Objekt verwendet wird, oder
     *            {@code null}, wenn die URL nicht bekannt ist.
     * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden,
     *                       oder {@code null}, wenn das Root-Objekt nicht lokalisiert ist.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Tooltip addMovieTooltip = createButtonTooltip(addMovieButton, TOOLTIP_ADD_MOVIE);
        Tooltip deleteMovieTooltip = createButtonTooltip(deleteMovieButton, TOOLTIP_DELETE_MOVIE);
        Tooltip editMovieTooltip = createButtonTooltip(editMovieButton, TOOLTIP_EDIT_MOVIE);

        updateListView();
    }
    //endregion

    //region Film hinzufügen
    /**
     * Fügt einen neuen Film zur Datenbank hinzu, indem die vom Benutzer eingegebenen
     * Informationen validiert und anschließend gespeichert werden. Die Klasse {@link DbManager} wird
     * verwendet, um den Film in der Datenbank zu speichern.
     *
     * Es werden Prüfungen auf leere Felder und gültige Ganzzahlen für das Erscheinungsjahr durchgeführt.
     * Bei fehlerhaften Eingaben wird eine Fehlermeldung angezeigt.
     */
    @FXML
    private void addMovie() {
        try {
            String title = titleTextField.getText().trim();
            String releaseYear = releaseYearTextField.getText().trim();
            String genre = genreTextField.getText().trim();

            // Überprüfen, ob die String-Werte nicht leer sind (und Erscheinungsjahr int-Wert hat)
            if (title.isEmpty()) {
                throw new IllegalArgumentException(EXCEPTION_VOID_TITLE);
            }
            if (releaseYear.isEmpty()) {
                throw new IllegalArgumentException(EXCEPTION_VOID_RELEASE_YEAR);
            }

            testReleaseYearIsInt(releaseYear);

            if (genre.isEmpty()) {
                throw new IllegalArgumentException(EXCEPTION_VOID_GENRE);
            }

            // Einfügen der eingelesenen Attribute
            Movie movieToInsert = new Movie(title, releaseYear, genre);
            // Einfügen des neuen Films in die Datenbank
            DbManager.getInstance().insertMovieIntoDbTbl(movieToInsert);

            clearInputFields();

        } catch (IllegalArgumentException e) {
            // Fehler bei ungültigen Eingaben
            showAlert(Alert.AlertType.WARNING,
                    WARNING_INVALID_USER_INPUT, e.getMessage());
        } catch (Exception e) {
            // Allgemeiner Fehler
            showAlert(Alert.AlertType.ERROR, ERROR_TITLE,
                    ERROR_UNEXPECTED_TEXT + e.getMessage());
        }
        //List view aktualisieren
        updateListView();
    }
    //endregion

    //region Film löschen
    /**
     * Löscht einen Film aus der Datenbank basierend auf der vom Benutzer eingegebenen ID.
     * Vor dem Löschen wird überprüft, ob die ID gültig ist und der Film existiert.
     * Es wird die Klasse {@link DbManager} verwendet, um den Film aus der Datenbank zu entfernen.
     * Bei einem Fehler wird eine entsprechende Fehlermeldung angezeigt.
     */
    @FXML
    private void deleteMovie() {

        try {
            int id = handleInputId();

            // Schauen, ob Film mit gegebener Id vorhanden ist in Film-Liste
            Movie movieToDelete = getMovieToProcess(id);

            // Aktualisierte Liste in DB einfügen
            DbManager.getInstance().deleteMovieInDbTblById(id);

            updateListView();
            clearInputFields();

            // Bestätigungsmeldung an den Benutzer
            showAlert(Alert.AlertType.INFORMATION, SUCCESS_TITLE,
                    MOVIE_WITH_ID_TEXT_START + id + INFORMATION_DELETE_TEXT_END);

        } catch (NumberFormatException e) {
            // Fehler, wenn die ID keine gültige Zahl ist
            showAlert(Alert.AlertType.ERROR, ERROR_TITLE, ERROR_INVALID_ID_TEXT);
        } catch (IllegalArgumentException e) {
            // Fehler bei anderen ungültigen Eingaben
            showAlert(Alert.AlertType.WARNING, WARNING_INVALID_INPUT_TITLE, e.getMessage());
        } catch (Exception e) {
            // Allgemeiner Fehler
            showAlert(Alert.AlertType.ERROR, ERROR_TITLE, ERROR_GENERAL_TEXT + e.getMessage());
        }
    }
    //endregion

    //region Film editieren
    /**
     * Bearbeitet die Informationen eines bestehenden Films basierend auf der vom Benutzer eingegebenen ID.
     * Es werden die Eingabefelder überprüft, und wenn Änderungen vorliegen, werden sie in der Datenbank gespeichert.
     * Der {@link DbManager} wird verwendet, um die Änderungen an der Datenbank zu speichern.
     */
    @FXML
    public void editMovie() {
        try {

            int id = handleInputId();

            Movie movieToUpdate = getMovieToProcess(id);

            // Die neuen Werte aus den TextFields lesen und aktualisieren
            String newTitle = titleTextField.getText().trim();
            if (!newTitle.isEmpty()) {
                movieToUpdate.setTitle(newTitle);
            }

            String newReleaseYear = releaseYearTextField.getText().trim();
            if (!newReleaseYear.isEmpty()) {
                testReleaseYearIsInt(newReleaseYear);
                movieToUpdate.setReleaseYear(newReleaseYear);
            }

            String newGenre = genreTextField.getText().trim();
            if (!newGenre.isEmpty()) {
                movieToUpdate.setGenre(newGenre);
            }

            DbManager.getInstance().updateMovieInDbTbl(movieToUpdate);

            updateListView();
            clearInputFields();
            // Bestätigungsmeldung an den Benutzer
            showAlert(Alert.AlertType.INFORMATION, SUCCESS_TITLE,
                    MOVIE_WITH_ID_TEXT_START + id + INFORMATION_UPDATED_SUCCESSFULLY_TEXT_END);

        } catch (NumberFormatException e) {
            // Fehler, wenn die ID keine gültige Zahl ist
            showAlert(Alert.AlertType.ERROR, ERROR_TITLE,
                    ERROR_INVALID_ID_TEXT);
        } catch (IllegalArgumentException e) {
            // Fehler bei ungültigen Eingaben
            showAlert(Alert.AlertType.WARNING, WARNING_INVALID_USER_INPUT, e.getMessage());
        } catch (Exception e) {
            // Allgemeiner Fehler
            showAlert(Alert.AlertType.ERROR, ERROR_TITLE,
                    ERROR_UNEXPECTED_TEXT + e.getMessage());
        }
    }
    //endregion

    //region Film-Liste aktualisieren und darstellen
    /**
     * Aktualisiert die Anzeige der Film-Liste in der Benutzeroberfläche.
     * Diese Methode wird verwendet, um die Film-Datenbank mit den neuesten Filminformationen
     * zu synchronisieren und die Liste der Filme im {@link ListView} anzuzeigen.
     * Sie ruft die {@link MovieHolder} Instanz auf, um die Film-Daten zu laden und
     * stellt sicher, dass die Ansicht mit den neuesten Daten gefüllt wird.
     * <p>
     * Die Methode verwendet die {@link MovieCellFactory}, um eine benutzerdefinierte Darstellung
     * jedes Films in der Liste zu erzeugen. Die Daten werden durch {@link FXCollections} in eine
     * {@link ObservableList} umgewandelt, damit Änderungen in der Liste sofort in der UI reflektiert werden.
     * </p>
     */
    private void updateListView() {
//        System.out.println("[DEBUG] showAllMovies");
        MovieHolder.getInstance().updateAllMovies();

        // Eine ListCell erstellen und befüllen
        this.movieListView.setCellFactory(new MovieCellFactory());
        ObservableList<Movie> movieList =
                FXCollections.observableArrayList(
                        MovieHolder.getInstance().getAllMovies());
        this.movieListView.setItems(movieList);

    }
    //endregion

    //region Hilfsfunktionen für CRUD-Operationen

    /**
     * Überprüft, ob das eingegebene Erscheinungsjahr eine gültige Ganzzahl ist.
     * Falls nicht, wird eine Ausnahme ausgelöst. Diese Methode nutzt {@link Integer#parseInt(String)}.
     *
     * @param newReleaseYear Das Erscheinungsjahr, das überprüft werden soll.
     * @throws IllegalArgumentException Wenn das Erscheinungsjahr keine gültige Ganzzahl ist.
     */
    private void testReleaseYearIsInt(String newReleaseYear) {
        if (!isInteger(newReleaseYear)) {
            // Wenn die Eingabe keine gültige Ganzzahl ist, wird eine Fehlermeldung angezeigt.
            throw new IllegalArgumentException(ERROR_RELEASE_YEAR_NO_INT);
        }
    }

    /**
     * Überprüft, ob der übergebene String eine gültige Ganzzahl darstellt. Diese Methode nutzt
     * {@link Integer#parseInt(String)}.
     *
     * @param input Der String, der überprüft werden soll.
     * @return true, wenn der String eine gültige Ganzzahl ist, andernfalls false.
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input); // Versucht, den String in eine Ganzzahl zu konvertieren
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validiert die vom Benutzer eingegebene ID. Es wird überprüft, ob die ID eine gültige Zahl ist und ob
     * sie größer oder gleich dem Mindest-Wert für ID {@link #MINIMUM_VALUE_ID} ist.
     *
     * @return Die validierte ID.
     * @throws IllegalArgumentException Wenn die ID ungültig ist.
     */
    private int handleInputId() {
        String idText = idTextField.getText().trim();
        if (idText.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_VOID_ID);
        }

        // ID als Ganzzahl umwandeln
        int id = Integer.parseInt(idText);

        if (id < MINIMUM_VALUE_ID) {
            throw new IllegalArgumentException(
                    EXCEPTION_ID_BELOW_MINIMUM_START + MINIMUM_VALUE_ID + EXCEPTION_ID_BELOW_MINIMUM_END);
        }
        return id;
    }

    /**
     * Sucht einen Film mit der gegebenen ID in der Film-Liste, die durch {@link MovieHolder} verwaltet wird.
     *
     * @param id Die ID des gesuchten Films.
     * @return Das Film-Objekt mit der angegebenen ID.
     * @throws IllegalArgumentException Wenn kein Film mit dieser ID gefunden wird.
     */
    private Movie getMovieToProcess(int id) {
        Movie movieToProcess = null;
        List<Movie> movieList = MovieHolder.getInstance().getAllMovies();

        for (Movie movie : movieList) {
            if (movie.getId() == id) {
                movieToProcess = movie;
                break;
            }
        }

        // Wenn der Film nicht gefunden wurde
        if (movieToProcess == null) {
            throw new IllegalArgumentException(MOVIE_WITH_ID_TEXT_START + id + EXCEPTION_ID_NOT_FOUND_END);
        }

        return movieToProcess;
    }

    /**
     * Zeigt einen {@link Alert} an, der eine Nachricht an den Benutzer übermittelt.
     *
     * @param alertType Der Typ des Alarms, z.B. {@link Alert.AlertType#ERROR}.
     * @param title Der Titel des Alarms.
     * @param message Die Nachricht des Alarms.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Leert alle Eingabefelder, die durch {@link TextField} dargestellt werden.
     */
    private void clearInputFields() {
        titleTextField.clear();
        releaseYearTextField.clear();
        genreTextField.clear();
        idTextField.clear();
    }

    /**
     * Erstellt und installiert einen Tooltip für einen Button. Der Tooltip wird dem Button hinzugefügt
     * und zeigt dem Benutzer Informationen darüber, welche Eingabewerte erforderlich sind, um eine bestimmte Aktion auszuführen.
     *
     * @param button Der Button, dem der Tooltip hinzugefügt werden soll.
     * @param tooltipText Der Text, der im Tooltip angezeigt werden soll.
     * @return Der erstellte Tooltip.
     */
    private static Tooltip createButtonTooltip(Button button, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(button, tooltip);
        tooltip.setShowDelay(Duration.seconds(0.05));

        return tooltip;
    }
    //endregion

    //region Programm schließen
    /**
     * Behandelt das Beenden der Anwendung, indem der Benutzer um Bestätigung gebeten wird.
     * Wenn der Benutzer zustimmt, wird {@link Platform#exit()} aufgerufen, um die Anwendung zu beenden.
     *
     */
    @FXML
    public void exitProgram() {
        // Bestätigungsdialog anzeigen, bevor das Programm beendet wird
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_TITLE);
        alert.setHeaderText(null);
        alert.setContentText(CONFIRMATION_EXIT_TEXT);

        // Wenn der Benutzer auf "OK" klickt, wird das Programm beendet
        if (alert.showAndWait().get() == ButtonType.OK) {
            // Schließt die Anwendung
            Platform.exit();
        }
    }
    //endregion
}
