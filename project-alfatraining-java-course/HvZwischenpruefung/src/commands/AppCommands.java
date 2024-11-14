package commands;

/**
 * Die {@link AppCommands}-Klasse enthält eine Sammlung von Konstanten, die verschiedene
 * Befehle oder Optionen im Programm repräsentieren. Diese Befehle werden in der Benutzeroberfläche
 * verwendet, um die Auswahl des Benutzers zu verarbeiten und die entsprechenden Operationen auszuführen.
 *
 * Alle Werte in dieser Klasse sind Konstanten und werden typischerweise in Switch-Statements
 * oder bedingten Anweisungen genutzt, um das Verhalten der Anwendung zu steuern.
 */
public class AppCommands {

    public static final int DISPLAY_ORDERS = 1;
    public static final int CREATE_ORDER = 2;
    public static final int DELETE_ORDER = 3;
    public static final int EXIT_APPLICATION = 0;

    /**
     * Der private Konstruktor verhindert die Instanziierung der {@link AppCommands}-Klasse,
     * da sie nur Konstanten enthalten soll und nicht instanziiert werden muss.
     */
    private AppCommands() {}
}

