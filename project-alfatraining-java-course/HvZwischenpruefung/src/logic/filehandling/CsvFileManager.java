package logic.filehandling;

import logic.factories.OrderFactory;
import model.Order;
import texts.AppTexts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Die {@link CsvFileManager}-Klasse ist für das Lesen und Schreiben von Bestellungen in
 * eine CSV-Datei verantwortlich. Sie ermöglicht das Speichern und Laden von Bestellungen
 * aus einer Datei, wobei das CSV-Format verwendet wird.
 *
 * Diese Klasse nutzt das Singleton-Designmuster, um sicherzustellen, dass nur eine Instanz
 * der Klasse existiert und auf sie jederzeit zugegriffen werden kann.
 */
public class CsvFileManager {
    //region Konstanten
    /**
     * Der Pfad zur CSV-Datei, in der die Bestellungen gespeichert werden.
     */
    private static final String FILE_PATH = "src/resources/orders.csv";
    //endregion

    //region Decl und Init Attribute
    /**
     * Die Instanz des Singleton-CSV-FileManagers.
     */
    private static CsvFileManager instance;
    //endregion

    //region Konstruktoren
    /**
     * Der private Konstruktor stellt sicher, dass keine Instanz der Klasse
     * von außen erstellt werden kann.
     */
    private CsvFileManager() {
    }
    //endregion

    //region GetInstance
    /**
     * Gibt die einzige Instanz des {@link CsvFileManager} zurück.
     * Diese Methode stellt sicher, dass nur eine Instanz der Klasse existiert
     * (Singleton-Muster).
     *
     * @return Die Instanz des {@link CsvFileManager}.
     */
    public static synchronized CsvFileManager getInstance() {
        if (instance == null) {
            instance = new CsvFileManager();
        }
        return instance;
    }
    //endregion

    //region Save orders to CSV-file
    /**
     * Speichert eine Liste von Bestellungen in einer CSV-Datei.
     * Jede Bestellung wird in eine Zeile der CSV-Datei geschrieben.
     *
     * @param orders Die Liste der Bestellungen, die gespeichert werden soll.
     */
    public void saveOrdersToCsvFile(List<Order> orders) {
        try (FileWriter writer = new FileWriter(FILE_PATH, StandardCharsets.UTF_8)) {
            for (Order order : orders) {
                writer.write(order.getAllAttributesAsCsvLine());
            }
        } catch (Exception e) {
            System.err.println(AppTexts.ERROR_WRITING_IN_CSV_FILE + e.getMessage());
        }
    }
    //endregion

    //region Read orders from CSV-file
    /**
     * Liest alle Bestellungen aus einer CSV-Datei und gibt eine Liste der Bestellungen zurück.
     * Wenn die Datei existiert, wird sie Zeile für Zeile gelesen und die Bestellungen
     * werden in eine Liste umgewandelt.
     *
     * @return Eine Liste der Bestellungen, die aus der CSV-Datei gelesen wurden.
     */
    public List<Order> readOrdersFromCsvFile() {
        List<Order> orders = new ArrayList<>();
        File ordersFile = new File(FILE_PATH);

        if (ordersFile.exists()) {
            try (
                    FileReader reader = new FileReader(FILE_PATH, StandardCharsets.UTF_8);
                    BufferedReader in = new BufferedReader(reader)
            ) {
                String csvLine;
                boolean eof = false;

                while (!eof) {
                    csvLine = in.readLine();

                    if (csvLine == null) {
                        eof = true;
                    } else {
                        Order orderFromCsvFile =
                                OrderFactory.generateOrderFromCsvLine(csvLine);
                        orders.add(orderFromCsvFile);
                    }
                }
            } catch (Exception e) {
                System.err.println(AppTexts.ERROR_READING_CSV_FILE + e.getMessage());
            }
        }
        return orders;
    }
    //endregion
}
