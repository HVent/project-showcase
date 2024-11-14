package test;

import logic.filehandling.CsvFileManager;
import model.Order;
import ui.UiController;

import java.util.ArrayList;
import java.util.List;

/**
 * Die {@link TestStuff}-Klasse führt eine Vielzahl von Tests durch, um die Funktionsweise der Anwendung
 * zu überprüfen. Sie umfasst Tests zur {@link Order}-Klasse, zum Schreiben und Lesen von CSV-Dateien,
 * zur Fehlerbehandlung und zur Ausgabe von Dummy-Daten.
 *
 * Der Code ermöglicht es, Bestellungen zu simulieren, die mit der {@link CsvFileManager}-Klasse
 * gespeichert und geladen werden, und prüft dabei auch, wie die Anwendung auf fehlerhafte Daten reagiert.
 *
 * Am Ende der Tests wird der {@link UiController} gestartet, um die Benutzeroberfläche der Anwendung
 * zu initialisieren.
 */
public class TestStuff {
    public static void main(String[] args) {
        //region Teste Funktionsfähigkeit von CSV und Order-Klasse

        // Test von Order Klasse
        // Dummydaten bereitstellen
        Order order = new Order(49, "Horst", "Laptop", 13);
        List<Order> orders = DummyData.getDummyOrders();

        // Ausgabe Dummydaten ohne hinzugefügte Order
        printDummyData(orders);

        // neue Order hinzufügen
        orders.add(order);
        // Ausgabe Dummydaten nach hinzugefügter Order
        printDummyData(orders);

        // Testen von richtigem CSV Handling
        // Benoetigte Datenstrukturen erstellen
        CsvFileManager csvFileManager = CsvFileManager.getInstance();
        List<Order> ordersFromCsvFile = new ArrayList<>();
        // Testen vom Schreiben
        csvFileManager.saveOrdersToCsvFile(orders);
        // Testen vom Lesen
        testReadingOfCsvFile(csvFileManager, ordersFromCsvFile);

        // Teste auf Fehlerbehandlung
        testWrongDataTypeHandling(orders, csvFileManager, ordersFromCsvFile);

        // Testen vom toString Funktion
        System.out.println(csvFileManager.toString());

        //endregion

        // Einbeziehen des UiControllers
        UiController.getInstance().startApplication();
    }

    /**
     * Testet das Lesen von Bestellungen aus einer CSV-Datei und gibt die gelesenen Bestellungen aus.
     *
     * @param csvFileManager Der {@link CsvFileManager}, der für das Lesen von Bestellungen zuständig ist.
     * @param ordersFromCsvFile Die Liste von Bestellungen, die aus der CSV-Datei gelesen werden.
     */
    private static void testReadingOfCsvFile(CsvFileManager csvFileManager,
                                             List<Order> ordersFromCsvFile) {
        ordersFromCsvFile =  csvFileManager.readOrdersFromCsvFile();
        System.out.println(ordersFromCsvFile);
    }

    /**
     * Testet die Fehlerbehandlung, indem eine Bestellung mit falschen Daten (z. B. einem ungültigen Typ)
     * zur Liste hinzugefügt wird und überprüft wird, ob die Datei korrekt gespeichert und anschließend
     * mit den falschen Daten geladen werden kann.
     *
     * @param orders Die Liste der Bestellungen, zu der eine fehlerhafte Bestellung hinzugefügt wird.
     * @param csvFileManager Der {@link CsvFileManager}, der für das Speichern und Lesen der Bestellungen zuständig ist.
     * @param ordersFromCsvFile Die Liste von Bestellungen, die aus der CSV-Datei gelesen werden.
     */
    private static void testWrongDataTypeHandling(List<Order> orders,
                                                  CsvFileManager csvFileManager,
                                                  List<Order> ordersFromCsvFile) {
        Order wrongData = new Order(0, "Helga", "Hüfte", 1);
        orders.add(wrongData);
        csvFileManager.saveOrdersToCsvFile(orders);
        ordersFromCsvFile =  csvFileManager.readOrdersFromCsvFile();
        System.out.println(ordersFromCsvFile);
    }

    /**
     * Gibt die Dummy-Daten aus, sowohl als einfache Liste als auch in CSV-Zeilenformat.
     *
     * @param orders Die Liste der Bestellungen, die ausgegeben werden sollen.
     */
    private static void printDummyData(List<Order> orders) {
        System.out.println(orders.toString());
        for (Order order : orders) {
            System.out.println(order.getAllAttributesAsCsvLine() + "\n");
        }
    }
}
