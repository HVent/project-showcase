package model;

/**
 * Die Klasse {@link Order} repräsentiert eine Bestellung mit den wichtigsten Attributen:
 * Bestell-ID, Kundenname, Produktname und Produktmenge.
 *
 * Diese Klasse verwendet das Java-Record-Feature, um eine unveränderliche und kompakte
 * Darstellung einer Bestellung zu ermöglichen.
 */
public record Order(int orderId,
                    String customerName,
                    String productName,
                    int productQuantity) {

    //region Konstanten
    /**
     * Das Trennzeichen für CSV-Dateien.
     */
    public static final String SPLIT_REGEX          = ";";

    /**
     * Index für die Bestell-ID in einer CSV-Zeile.
     */
    public static final int INDEX_ORDER_ID          = 0;

    /**
     * Index für den Kundennamen in einer CSV-Zeile.
     */
    public static final int INDEX_CUSTOMER_NAME     = 1;

    /**
     * Index für den Produktnamen in einer CSV-Zeile.
     */
    public static final int INDEX_PRODUCT_NAME      = 2;

    /**
     * Index für die Produktmenge in einer CSV-Zeile.
     */
    public static final int INDEX_PRODUCT_QUANTITY  = 3;
    //endregion

    //region Getter
    /**
     * Gibt alle Attribute der Bestellung als eine formatierte CSV-Zeile zurück.
     *
     * @return Eine CSV-Zeile, die die Bestell-ID, den Kundenname, den Produktnamen
     *         und die Produktmenge enthält, getrennt durch ein Semikolon.
     */
    public String getAllAttributesAsCsvLine() {
        return orderId + SPLIT_REGEX +
                customerName + SPLIT_REGEX +
                productName + SPLIT_REGEX +
                productQuantity + "\n";
    }
    //endregion
}
