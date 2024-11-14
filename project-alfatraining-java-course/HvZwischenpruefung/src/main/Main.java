package main;

import ui.UiController;

/**
 * Die Hauptklasse {@link Main} dient als Einstiegspunkt für das Programm.
 * Sie startet die Benutzeroberfläche, indem sie den {@link UiController} aufruft.
 *
 * Diese Klasse enthält die `main`-Methode, die beim Starten des Programms ausgeführt wird.
 */
public class Main {

    /**
     * Die `main`-Methode ist der Einstiegspunkt für das Programm.
     * Sie startet die Anwendung, indem sie den {@link UiController} aufruft.
     *
     * @param args Die Kommandozeilenargumente (werden in dieser Anwendung nicht verwendet).
     */
    public static void main(String[] args) {
        UiController.getInstance().startApplication();
    }
}
