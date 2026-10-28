package ec.edu.espe.cosmoclick.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageAndReportsTest {

    @TempDir
    Path tempFolder;

    private SpaController original;

    @BeforeEach
    public void setUp() {
        original = new SpaController();
        original.registerCustomer("Ana, la Torres", 991234567, "ana@mail.com");
        original.registerProduct("Crema", "Nivea", 25.5, DateUtil.parse("2030-12-31"), 10);
        original.scheduleAppointment(1, 1, DateUtil.parse("2030-05-20"), "10:00");
        original.registerSale(1, new int[]{1}, new int[]{3}, "Tarjeta");
    }

    @Test
    public void saveAndLoad_restoresAllData() throws IOException {
        JsonStorage storage = new JsonStorage(tempFolder.toString());
        storage.save(original);

        SpaController loaded = new SpaController();
        storage.load(loaded);

        assertEquals(1, loaded.getCustomers().size());
        assertEquals("Ana, la Torres", loaded.getCustomers().get(0).getName());
        assertEquals(7, loaded.getInventory().getProductDetails(1).getQuantity());
        assertEquals(1, loaded.getAppointments().size());
        assertEquals(1, loaded.getCustomers().get(0).getAppointments().size());
        assertEquals(1, loaded.getSales().size());
        assertEquals(76.5f, loaded.getSales().get(0).calculateTotal(), 0.001);
        assertTrue(loaded.getSales().get(0).getPayment().isPaymentStatus());
    }

    @Test
    public void load_withoutFiles_keepsControllerEmpty() throws IOException {
        SpaController loaded = new SpaController();
        new JsonStorage(tempFolder.resolve("empty").toString()).load(loaded);
        assertEquals(0, loaded.getCustomers().size());
        assertEquals(0, loaded.getSales().size());
    }

    @Test
    public void exportPayments_writesRowsAndTotal() throws IOException {
        File file = new CsvExporter(tempFolder.toString()).exportPayments(original, "Todos");
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        assertTrue(text.startsWith("payment_id,sale_id,customer,method,amount,status,date"));
        assertTrue(text.contains("\"Ana, la Torres\""));
        assertTrue(text.contains("TOTAL,,,,76.5"));
    }

    @Test
    public void exportInventory_writesProducts() throws IOException {
        File file = new CsvExporter(tempFolder.toString()).exportInventory(original);
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        assertNotNull(file);
        assertTrue(text.contains("1,Crema,Nivea,25.5,7,2030-12-31,false"));
    }

    @Test
    public void exportCustomers_writesHeaderAndRows() throws IOException {
        File file = new CsvExporter(tempFolder.toString()).exportCustomers(original);
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        assertTrue(text.startsWith("id,name,phone,email\n"));
        assertTrue(text.contains("1,\"Ana, la Torres\",991234567,ana@mail.com"));
    }
}
