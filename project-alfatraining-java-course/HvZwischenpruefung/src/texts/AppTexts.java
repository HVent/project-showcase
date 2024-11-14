package texts;

/**
 * Die Klasse {@link AppTexts} enthält alle konstanten Textnachrichten und Formatvorlagen,
 * die in der Anwendung verwendet werden.
 *
 * Diese Klasse dient als zentrale Anlaufstelle für alle Textausgaben im Programm,
 * sodass die Nachrichten bei Bedarf einfach angepasst werden können, ohne dass
 * der Code selbst geändert werden muss.
 */
public class AppTexts {

    //region Allgemeine Nachrichten
    public static final String APPLICATION_NAME             = "Kundendatenbank fuer Bestellungen";
    public static final String AUTHOR_OF_PROGRAM            = "Heinrich Vent";
    public static final String APPLICATION_NAME_FORMAT      = "\n\n###\tHvZwischenpruefung\t###\nAuthor: %s\nProjekttitel: %s\n\n";
    public static final String WELCOME_MESSAGE              = "Willkommen zum Programm. Viel Spass!\n";
    public static final String PLEASE_SELECT_OPTION         = "Bitte waehlen Sie eine Option:\n";
    public static final String YOUR_OPTION = "\nIhre Option: ";
    public static final String MENU_OPTION_DEFAULT_FORMAT   = "[\t%d\t] %s\n";
    public static final String MENU_OPTION_EXIT_APPLICATION_FORMAT = "\n[\t%d\t] %s\n";
    public static final String DISPLAY_ORDERS = "Bestellungen anzeigen";
    public static final String CREATE_ORDER   = "Bestellung erstellen";
    public static final String DELETE_ORDER   = "Bestellung loeschen";
    public static final String EXIT_APPLICATION = "Programm beenden";
    public static final String EXIT_MESSAGE     = "\nProgramm wird beendet...";
    //endregion

    //region Nachrichten für Order-Operationen
    public static final String DISPLAY_ORDERS_MESSAGE = "\nBestellungen anzeigen...\n";
    public static final String CREATE_ORDER_MESSAGE   = "\nBestellung erstellen...\n";
    public static final String DELETE_ORDER_MESSAGE   = "\nBestellung loeschen...\n";
    public static final String DISPLAY_CURRENT_ORDER_FORMAT = "[\t%d\t] %s\n";
    //endregion

    //region Bestaetigung und Erfolgsnachrichten
    public static final String MESSAGE_ORDER_CREATED          = "\n>> Bestellung erfolgreich erstellt. <<";
    public static final String MESSAGE_ORDER_DELETED          = "\n>> Bestellung erfolgreich geloescht. <<";
    public static final String MESSAGE_CONFIRM_ORDER_DELETION =
            "Moechten Sie die Bestellung wirklich loeschen? (j/n)\n";
    //endregion

    //region Prompts
    public static final String PROMPT_ORDER_ID                          = "Bestellnummer: ";
    public static final String PROMPT_CUSTOMER_NAME                     = "Name des Kunden: ";
    public static final String PROMPT_PRODUCT_NAME                      = "Produktname: ";
    public static final String PROMPT_PRODUCT_QUANTITY                  = "Anzahl: ";
    private static final String PROMPT_VALID_INDEX_FORMAT    = "(Der Index muss >= 0 und < %d sein.)\n";
    public static final  String PROMPT_CHOOSE_A_INDEX_FORMAT =
            "\nGeben Sie einen gültigen Index ein.\n" + PROMPT_VALID_INDEX_FORMAT;
    //endregion

    //region Fehlernachrichten
    public static final String ERROR_WRITING_IN_CSV_FILE    = "Es ist ein Fehler beim Schreiben in die CSV-Datei aufgetreten.";
    public static final String ERROR_READING_CSV_FILE       = "Es ist ein Fehler beim Lesen der CSV-Datei aufgetreten.";
    public static final String ERROR_INVALID_OPTION         = "Die Eingabe ist ungültig. Bitte versuchen Sie es erneut.";
    public static final String ERROR_NO_ORDERS_AVAILABLE    = "Keine Bestellungen verfuegbar.";
    public static final String ERROR_INVALID_INDEX_FORMAT   = "\n{ Ungültige Eingabe.\n" + PROMPT_VALID_INDEX_FORMAT + " }";
    //endregion

    //region Konstruktor
    /**
     * Privater Konstruktor, um Instanziierungen zu verhindern.
     * Da diese Klasse nur Konstanten enthält, ist sie nicht instanziierbar.
     */
    private AppTexts() {}
    //endregion
}

