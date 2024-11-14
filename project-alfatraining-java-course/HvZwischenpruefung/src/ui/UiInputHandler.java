package ui;

import de.rhistel.logic.ConsoleReader;
import model.Order;
import texts.AppTexts;

/**
 * Diese Klasse stellt Methoden zur Verfügung, um Benutzereingaben für Bestellungen zu verarbeiten.
 * Sie bietet Funktionen zum Abrufen einer gültigen Bestellung und zur Auswahl eines gültigen Indexes
 * für die Bestellliste.
 */
public class UiInputHandler {

    //region Konstanten
    //endregion

    //region Order-Handling

    /**
     * Fordert den Benutzer auf, eine gültige Bestellung einzugeben.
     * Diese Methode fordert nacheinander die Eingabe der Bestell-ID, des Kundennamens,
     * des Produktnamens und der Produktmenge an und gibt ein {@link Order}-Objekt zurück.
     *
     * @return Ein neues {@link Order}-Objekt, das die vom Benutzer eingegebene Bestellung repräsentiert.
     */
    public Order getValidOrderFromUi() {
        int orderId = getValidOrderId();
        String customerName = getValidCustomerName();
        String productName = getValidProductName();
        int productQuantity = getValidProductQuantity();

        return new Order(orderId, customerName, productName, productQuantity);
    }

    /**
     * Fordert den Benutzer zur Eingabe einer gültigen Bestell-ID auf.
     *
     * @return Die eingegebene Bestell-ID als ganzzahliger Wert.
     */
    private int getValidOrderId() {
        System.out.println(AppTexts.PROMPT_ORDER_ID);
        return ConsoleReader.in.readPositivInt();
    }

    /**
     * Fordert den Benutzer zur Eingabe eines gültigen Kundennamens auf.
     *
     * @return Der eingegebene Kundename als String.
     */
    private String getValidCustomerName() {
        System.out.println(AppTexts.PROMPT_CUSTOMER_NAME);
        return ConsoleReader.in.readMandatoryString();
    }

    /**
     * Fordert den Benutzer zur Eingabe eines gültigen Produktnamens auf.
     *
     * @return Der eingegebene Produktname als String.
     */
    private String getValidProductName() {
        System.out.println(AppTexts.PROMPT_PRODUCT_NAME);
        return ConsoleReader.in.readMandatoryString();
    }

    /**
     * Fordert den Benutzer zur Eingabe einer gültigen Produktmenge auf.
     *
     * @return Die eingegebene Produktmenge als ganzzahliger Wert.
     */
    private int getValidProductQuantity() {
        System.out.println(AppTexts.PROMPT_PRODUCT_QUANTITY);
        return ConsoleReader.in.readPositivInt();
    }

    //endregion

    //region Indexvalidierung

    /**
     * Fordert den Benutzer zur Eingabe eines gültigen Indexes basierend auf der Anzahl der Bestellungen.
     * Der Index muss innerhalb der gültigen Range liegen, d. h. zwischen 0 und der Größe der Bestellliste.
     *
     * @param ordersSize Die Anzahl der Bestellungen, die dem Benutzer zur Auswahl stehen.
     * @return Der gültige Index als ganzzahliger Wert.
     */
    public int getValidIndexFromUi(int ordersSize) {

        // Decl und Init
        int validIndex = -1;
        boolean isValidIndex = false;

        do {
            System.out.printf(AppTexts.PROMPT_CHOOSE_A_INDEX_FORMAT, ordersSize);
            int indexFromUi = ConsoleReader.in.readPositivInt();

            if (indexFromUi < ordersSize) {
                validIndex = indexFromUi;
                isValidIndex = true;
            } else {
                System.out.printf(AppTexts.ERROR_INVALID_INDEX_FORMAT, ordersSize);
            }
        } while (!isValidIndex);

        return validIndex;
    }

    //endregion
}