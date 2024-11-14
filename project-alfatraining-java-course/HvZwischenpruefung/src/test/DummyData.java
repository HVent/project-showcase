package test;

import model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Die {@link DummyData}-Klasse stellt eine Sammlung von Dummy-Daten zur Verfügung,
 * die für Tests oder zur Simulation von Bestellungen in der Anwendung verwendet werden können.
 * Sie enthält eine Methode, die eine Liste von vordefinierten Bestellungen zurückgibt.
 */
public class DummyData {

    /**
     * Der private Konstruktor verhindert, dass eine Instanz der {@link DummyData}-Klasse
     * von außen erstellt wird, da diese Klasse nur statische Methoden enthält.
     */
    private DummyData() {}

    //region Getter für Liste mit Dummydaten
    /**
     * Gibt eine Liste von Dummy-Bestellungen zurück. Diese Bestellungen können in Tests oder
     * zum Beispiel für die Entwicklung und das Debugging verwendet werden.
     *
     * @return Eine Liste von {@link Order}-Objekten, die als Dummy-Daten dienen.
     */
    public static List<Order> getDummyOrders() {
        List<Order> dummyOrders = new ArrayList<>();

        dummyOrders.add(new Order(123, "Klaus", "Brot", 3));
        dummyOrders.add(new Order(345, "Jürgen", "Haus", 1));
        dummyOrders.add(new Order(789, "Hans", "Auto", 2));

        return dummyOrders;
    }
    //endregion
}

