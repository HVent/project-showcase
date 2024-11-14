package logic.factories;

import model.Order;

/**
 * Die {@link OrderFactory}-Klasse enthält eine Methode, die eine Bestellung aus einer CSV-Zeile erzeugt.
 * Diese Klasse stellt eine Factory-Methodenimplementierung dar, die für das Erzeugen von {@link Order}-Objekten
 * aus einer CSV-Zeile verantwortlich ist.
 *
 * Die Klasse verwendet das Singleton-Designmuster, um sicherzustellen, dass nur eine Instanz dieser Klasse existiert.
 */
public class OrderFactory {
    //region Konstruktor
    /**
     * Der private Konstruktor stellt sicher, dass keine Instanz dieser Klasse von außen erstellt werden kann.
     */
    private OrderFactory() {}
    //endregion

    //region Orders von CSV-Zeile in ein neues Objekt einlesen
    /**
     * Wandelt eine CSV-Zeile in ein {@link Order}-Objekt um.
     * Diese Methode erwartet eine CSV-Zeile, die Bestellinformationen enthält, und erstellt ein neues
     * {@link Order}-Objekt mit den extrahierten Attributen.
     *
     * @param csvLine Eine CSV-Zeile, die die Bestellinformationen enthält.
     * @return Ein {@link Order}-Objekt, das mit den in der CSV-Zeile enthaltenen Daten erstellt wurde.
     */
    public static synchronized Order generateOrderFromCsvLine(String csvLine) {
        String[] allAttributesAsStrings = csvLine.split(Order.SPLIT_REGEX);

        String orderIdAsString          = allAttributesAsStrings[Order.INDEX_ORDER_ID];
        String customerName             = allAttributesAsStrings[Order.INDEX_CUSTOMER_NAME];
        String productName              = allAttributesAsStrings[Order.INDEX_PRODUCT_NAME];
        String productQuantityAsString  = allAttributesAsStrings[Order.INDEX_PRODUCT_QUANTITY];

        int orderId                     = Integer.parseInt(orderIdAsString);
        int productQuantity             = Integer.parseInt(productQuantityAsString);

        return new Order(orderId,
                customerName,
                productName,
                productQuantity);
    }
    //endregion
}

