package ui;

import commands.AppCommands;
import de.rhistel.logic.ConsoleReader;
import logic.manager.OrderManager;
import model.Order;
import texts.AppTexts;

/**
 * Der {@link UiController} verwaltet die Benutzeroberfläche und die Interaktion mit dem Benutzer
 * im Rahmen der Bestellverwaltung. Er steuert die Darstellung des Hauptmenüs, das Erstellen,
 * Anzeigen und Löschen von Bestellungen.
 *
 * Diese Klasse implementiert das Singleton-Designmuster, um sicherzustellen, dass nur eine
 * Instanz des Controllers existiert.
 */
public class UiController {

    //region Konstanten
    //endregion

    //region Decl und Init Attribute
    private static UiController instance;
    private final UiInputHandler uiInputHandler;
    //endregion

    //region Konstruktoren
    /**
     * Privater Konstruktor, der eine Instanz von {@link UiInputHandler} erstellt.
     * Eine Instanziierung von außerhalb der Klasse wird so verhindert.
     */
    private UiController() {
        this.uiInputHandler = new UiInputHandler();
    }
    //endregion

    //region GetInstance
    /**
     * Gibt die einzige Instanz des {@link UiController} zurück.
     * Wenn die Instanz noch nicht existiert, wird sie erstellt.
     *
     * @return Die Instanz des {@link UiController}.
     */
    public static synchronized UiController getInstance() {
        if (instance == null) {
            instance = new UiController();
        }
        return instance;
    }
    //endregion

    //region Start Application
    /**
     * Startet die Anwendung, indem das Hauptmenü aufgerufen wird.
     */
    public void startApplication() {
        handleMainMenu();
    }

    /**
     * Verarbeitet die Benutzereingaben im Hauptmenü und führt die entsprechenden
     * Operationen wie Anzeigen, Erstellen und Löschen von Bestellungen aus.
     */
    private void handleMainMenu() {
        boolean exitApplication = false;

        while (!exitApplication) {
            displayMainMenu();

            System.out.println(AppTexts.YOUR_OPTION);
            int userOption = ConsoleReader.in.readPositivInt();

            switch (userOption) {
                case AppCommands.DISPLAY_ORDERS -> displayOrders();
                case AppCommands.CREATE_ORDER -> createOrder();
                case AppCommands.DELETE_ORDER -> deleteOrder();
                case AppCommands.EXIT_APPLICATION -> {
                    exitApplication = true;
                    System.out.println(AppTexts.EXIT_MESSAGE);
                }
                default -> System.out.println(AppTexts.ERROR_INVALID_OPTION);
            }
        }
    }

    /**
     * Zeigt das Hauptmenü der Anwendung an, in dem der Benutzer eine Option auswählen kann.
     */
    private void displayMainMenu() {
        System.out.printf(AppTexts.APPLICATION_NAME_FORMAT,
                AppTexts.AUTHOR_OF_PROGRAM,
                AppTexts.APPLICATION_NAME);
        System.out.println(AppTexts.WELCOME_MESSAGE);
        System.out.println(AppTexts.PLEASE_SELECT_OPTION);

        System.out.printf(AppTexts.MENU_OPTION_DEFAULT_FORMAT,
                AppCommands.DISPLAY_ORDERS,
                AppTexts.DISPLAY_ORDERS);
        System.out.printf(AppTexts.MENU_OPTION_DEFAULT_FORMAT,
                AppCommands.CREATE_ORDER,
                AppTexts.CREATE_ORDER);
        System.out.printf(AppTexts.MENU_OPTION_DEFAULT_FORMAT,
                AppCommands.DELETE_ORDER,
                AppTexts.DELETE_ORDER);
        System.out.printf(AppTexts.MENU_OPTION_EXIT_APPLICATION_FORMAT,
                AppCommands.EXIT_APPLICATION,
                AppTexts.EXIT_APPLICATION);
    }
    //endregion

    //region Orders CRUD-Operationen aufrufen
    /**
     * Zeigt alle Bestellungen an, die derzeit im System vorhanden sind.
     * Wenn keine Bestellungen vorhanden sind, wird eine Fehlermeldung angezeigt.
     */
    private void displayOrders() {
        System.out.println(AppTexts.DISPLAY_ORDERS_MESSAGE);

        if (OrderManager.getInstance().getOrders().isEmpty()) {
            System.out.println(AppTexts.ERROR_NO_ORDERS_AVAILABLE);
        } else {
            for (int index = 0; index < OrderManager.getInstance().getOrdersSize(); index++) {
                Order currentOrder = OrderManager.getInstance().getOrders().get(index);
                System.out.printf(AppTexts.DISPLAY_CURRENT_ORDER_FORMAT, index, currentOrder);
            }
        }
    }

    /**
     * Fordert den Benutzer auf, eine neue Bestellung zu erstellen und fügt sie dann
     * der Bestellverwaltung hinzu. Zeigt nach erfolgreichem Erstellen die Liste der Bestellungen an.
     */
    private void createOrder() {
        System.out.println(AppTexts.CREATE_ORDER_MESSAGE);

        Order orderFromUi = this.uiInputHandler.getValidOrderFromUi();

        if (orderFromUi != null) {
            OrderManager.getInstance().addOrder(orderFromUi);
            System.out.println(AppTexts.MESSAGE_ORDER_CREATED);
            displayOrders();
        }
    }

    /**
     * Ermöglicht dem Benutzer das Löschen einer Bestellung. Der Benutzer muss die Bestellung
     * aus der Liste auswählen und die Löschung bestätigen.
     */
    private void deleteOrder() {
        displayOrders();
        System.out.println(AppTexts.DELETE_ORDER_MESSAGE);

        int indexOfOrderToDelete = this.uiInputHandler.getValidIndexFromUi(
                OrderManager.getInstance().getOrdersSize());

        System.out.println(AppTexts.MESSAGE_CONFIRM_ORDER_DELETION);
        boolean isConfirmed = ConsoleReader.in.readMandatoryAnswerToBinaryQuestion();

        if (isConfirmed) {
            OrderManager.getInstance().deleteOrder(indexOfOrderToDelete);
            System.out.println(AppTexts.MESSAGE_ORDER_DELETED);
            displayOrders();
        }
    }
    //endregion
}

