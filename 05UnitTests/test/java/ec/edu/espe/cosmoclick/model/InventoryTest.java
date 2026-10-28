package ec.edu.espe.cosmoclick.model;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class InventoryTest {
    
    public InventoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addProduct method, of class Inventory.
     */
    
    // FALLA: el método no valida null, así que lanza NullPointerException
    // en updateStock(product) en vez de IllegalArgumentException.
    @Test
    public void testAddProductNull() {
        System.out.println("addProduct con producto null");
        Product product = null;
        Inventory instance = new Inventory();
        assertThrows(IllegalArgumentException.class, () -> {
        instance.addProduct(product);
    });
    }
   
    /**
     * Test of updateQuantity method, of class Inventory.
     */
    @Test
    public void testUpdateQuantityNull() {
        System.out.println("updateQuantity con producto null");
        Product product = null;
        Inventory instance = new Inventory();
        assertThrows(NullPointerException.class, () -> {
        instance.updateQuantity(product, 5);
        });
    }
    
        // ===== checkAvailability =====

    @Test
    public void testCheckAvailabilityNegativeQuantity() {
        System.out.println("checkAvailability con cantidad negativa");
        Inventory instance = new Inventory();
        instance.addProduct(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5));
        assertThrows(IllegalArgumentException.class, () -> {
        instance.checkAvailability(1, -2);
        });
    }
    
    @Test
    public void testGenerateLowStockAlertEnoughStock() {
        System.out.println("generateLowStockAlert con stock suficiente");
        Inventory instance = new Inventory(1, 0, 5);
        instance.addProduct(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 20));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.generateLowStockAlert();
        System.setOut(original);
        assertFalse(out.toString().contains("ALERTA"));
    }
    
    @Test
    public void testGetProductDetailsNegativeId() {
        System.out.println("getProductDetails con id negativo");
        Inventory instance = new Inventory();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.getProductDetails(-1);
        });
    }
    
    @Test
    public void testGetProductDetailsNotFound() {
        System.out.println("getProductDetails con id inexistente");
        Inventory instance = new Inventory();
        assertNull(instance.getProductDetails(99));
    }
    
    @Test
    public void testGetProductDetailsFound() {
        System.out.println("getProductDetails con id existente");
        Product product = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5);
        Inventory instance = new Inventory();
        instance.addProduct(product);
        assertSame(product, instance.getProductDetails(1));
    }
    
        @Test
    public void testGenerateLowStockAlertEqualToThreshold() {
        System.out.println("generateLowStockAlert con stock igual al umbral");
        Inventory instance = new Inventory(1, 0, 5);
        instance.addProduct(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.generateLowStockAlert();
        System.setOut(original);
        // Se espera alerta solo si el stock es menor al umbral
        assertFalse(out.toString().contains("ALERTA"));
    }
}
    
