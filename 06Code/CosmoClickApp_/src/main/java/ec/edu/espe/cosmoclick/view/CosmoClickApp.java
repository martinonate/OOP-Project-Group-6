package ec.edu.espe.cosmoclick.view;

import ec.edu.espe.cosmoclick.controller.CsvExporter;
import ec.edu.espe.cosmoclick.controller.JsonStorage;
import ec.edu.espe.cosmoclick.controller.SpaController;
import java.io.IOException;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class CosmoClickApp {

    public static void main(String[] args) {
        SpaController controller = new SpaController();
        JsonStorage storage = new JsonStorage("data");
        CsvExporter exporter = new CsvExporter("reports");

        try {
            storage.load(controller);
        } catch (IOException e) {
            System.out.println("No se pudieron cargar los datos guardados: " + e.getMessage());
        }
        
        controller.loadDefaultProducts();

        new ConsoleMenu(controller, storage, exporter).run();
    }
}
