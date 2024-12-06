package de.hvent.hvmoviedatabase;

import de.hvent.hvmoviedatabase.gui.controller.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Die Hauptklasse der Anwendung, die von der JavaFX {@link Application}-Klasse erbt.
 * Diese Klasse dient als Einstiegspunkt für die JavaFX-Anwendung und startet das GUI.
 */
public class Main extends Application {
    /**
     * Die Methode wird aufgerufen, um die JavaFX-Anwendung zu starten.
     * Sie lädt die Benutzeroberfläche und setzt das Hauptfenster (Stage).
     *
     * @param stage : {@link Stage} : Das Hauptfenster der Anwendung.
     * @throws IOException : Wenn beim Laden der GUI eine I/O-Ausnahme auftritt.
     */
    @Override
    public void start(Stage stage) throws IOException {
        GuiController.getInstance().setPrimaryStage(stage);

        // Öffnet das GUI (ladet die FXML-Datei und zeigt sie an)
        GuiController.getInstance().openGui();
    }

    /**
     * Der Einstiegspunkt der Anwendung, der die JavaFX-Anwendung startet.
     *
     * @param args : {@link String[]} : Argumente, die beim Starten der Anwendung über die Kommandozeile übergeben werden.
     */
    public static void main(String[] args) {
        launch();
    }
}