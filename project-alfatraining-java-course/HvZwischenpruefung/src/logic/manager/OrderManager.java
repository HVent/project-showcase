package logic.manager;

import logic.filehandling.CsvFileManager;
import model.Order;

import java.util.List;

/**
 * Die {@link OrderManager}-Klasse verwaltet eine Sammlung von Bestellungen und ermöglicht
 * CRUD-Operationen (Erstellen, Lesen, Aktualisieren und Löschen) auf diesen Bestellungen.
 *
 * Sie stellt sicher, dass Änderungen an den Bestellungen auch in einer CSV-Datei gespeichert werden,
 * die als persistente Datenquelle dient.
 */
public class OrderManager {
    //region Decl und Init Attribute
    /**
     * Die Instanz des Singleton-OrderManagers.
     */
    private static OrderManager instance;

    /**
     * Die Liste der Bestellungen, die im System verwaltet werden.
     */
    private final List<Order> orders;
    //endregion

    //region Konstruktoren
    /**
     * Der private Konstruktor liest die Bestellungen aus einer CSV-Datei und initialisiert
     * die {@link #orders}-Liste.
     */
    private OrderManager() {
        this.orders = CsvFileManager.getInstance().readOrdersFromCsvFile();
    }
    //endregion

    //region GetInstance
    /**
     * Gibt die Instanz des {@link OrderManager} zurück. Wenn keine Instanz existiert,
     * wird eine neue erstellt.
     *
     * @return Die Instanz des {@link OrderManager}.
     */
    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }
    //endregion

    //region CRUD-Operationen
    /**
     * Gibt die Anzahl der Bestellungen im System zurück.
     *
     * @return Die Anzahl der Bestellungen.
     */
    public int getOrdersSize() {
        return this.orders.size();
    }

    /**
     * Gibt die Liste aller Bestellungen zurück.
     *
     * @return Eine Liste aller Bestellungen.
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    /**
     * Fügt eine neue Bestellung zum System hinzu und speichert die Änderungen in der CSV-Datei.
     *
     * @param order Die hinzuzufügende Bestellung.
     */
    public void addOrder(Order order) {
        this.orders.add(order);
        updateCsvFile();
    }

    /**
     * Löscht eine Bestellung aus dem System anhand des Indexes und speichert die Änderungen in der CSV-Datei.
     *
     * @param index Der Index der zu löschenden Bestellung.
     */
    public void deleteOrder(int index) {
        this.orders.remove(index);
        updateCsvFile();
    }

    /**
     * Speichert die aktuelle Liste der Bestellungen in der CSV-Datei.
     */
    private void updateCsvFile() {
        CsvFileManager.getInstance().saveOrdersToCsvFile(this.orders);
    }
    //endregion
}

