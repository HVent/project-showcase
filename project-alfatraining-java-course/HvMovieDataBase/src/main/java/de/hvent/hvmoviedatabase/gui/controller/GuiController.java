package de.hvent.hvmoviedatabase.gui.controller;

import de.hvent.hvmoviedatabase.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Der {@code GuiController} ist für das Laden und Anzeigen der grafischen Benutzeroberfläche (GUI)
 * verantwortlich. Diese Klasse verwaltet die Hauptfensteranzeige und stellt sicher, dass das FXML
 * für die Menüansicht korrekt geladen wird. Sie verwendet Klassen wie {@link FXMLLoader},
 * {@link Scene}, und {@link Stage} zur Erstellung und Darstellung der GUI.
 */
public class GuiController {

    //region Konstanten
    private static final int DEFAULT_SCENE_WIDTH = 900;
    private static final int DEFAULT_SCENE_HEIGHT = 500;

    private static final String MENU_FXML = "menu.fxml";
    private static final String MENU_TITLE = "HvMovieDataBase";
    //endregion

    //region Decl und Init Widgets und Attribute
    private static GuiController instance;
    private Stage primaryStage;

    //endregion

    //region Konstruktoren
    /**
     * Konstruktor des {@code GuiController}. Er wird in der Regel nicht direkt verwendet,
     * da die Instanz der Klasse über die {@link #getInstance()}-Methode bereitgestellt wird.
     */
    private GuiController() {
    }
    //endregion

    //region Getter und Setter
    /**
     * Gibt die Singleton-Instanz des {@code GuiController} zurück.
     * <p>
     * Diese Methode stellt sicher, dass nur eine Instanz des {@code GuiController} in der Anwendung
     * existiert. Sie wird verwendet, um die Instanz global zugänglich zu machen.
     * </p>
     * @return Die Singleton-Instanz des {@code GuiController}.
     */
    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    /**
     * Setzt das primäre Fenster (Stage) der Anwendung.
     * @param stage Das primäre {@link Stage} der Anwendung.
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    //endregion

    //region Open Gui(s)
    /**
     * Lädt und öffnet das Menü-Fenster.
     * <p>
     * Diese Methode verwendet {@link FXMLLoader}, um die FXML-Datei für das Menü
     * zu laden und eine neue {@link Scene} zu erstellen. Die Szene wird dann in
     * das {@link Stage} der Anwendung gesetzt, um die Benutzeroberfläche darzustellen.
     * </p>
     */
    public void openGui() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(MENU_FXML));
            Scene scene = new Scene(fxmlLoader.load(), DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

            this.primaryStage.setWidth(865);
            this.primaryStage.setHeight(575);
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle(MENU_TITLE);
            this.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //endregion
    }
}