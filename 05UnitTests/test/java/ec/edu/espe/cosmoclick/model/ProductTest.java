/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class ProductTest {
    
    @Test
    public void testSearchProductWithoutName() {
        System.out.println("searchProduct sin nombre");
        Product instance = new Product();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.searchProduct();
        });
    }

    // ===== updatePrice =====

    @Test
    public void testUpdatePriceNull() {
        System.out.println("updatePrice con precio null");
        Product instance = new Product();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.updatePrice(null);
        });
    }

    @Test
    public void testBuyProductOnce() {
        System.out.println("buyProduct una vez");
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5);
        instance.buyProduct();
        assertEquals(6, instance.getQuantity());
    }

    @Test
    public void testSellProductWithStock() {
        System.out.println("sellProduct con stock");
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5);
        instance.sellProduct();
        assertEquals(4, instance.getQuantity());
    }


    @Test
    public void testSellProductWithoutStockThrows() {
        System.out.println("sellProduct sin stock lanza excepcion");
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 0);
        assertThrows(IllegalStateException.class, () -> {
            instance.sellProduct();
        });
    }

    @Test
    public void testCheckExpirationExpired() {
        System.out.println("checkExpiration con producto vencido");
        Calendar expired = Calendar.getInstance();
        expired.set(2020, Calendar.JANUARY, 1);
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), expired, 5);
        assertTrue(instance.checkExpiration(Calendar.getInstance()));
    }

    @Test
    public void testCheckExpirationNotExpired() {
        System.out.println("checkExpiration con producto vigente");
        Calendar future = Calendar.getInstance();
        future.set(2099, Calendar.JANUARY, 1);
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), future, 5);
        assertFalse(instance.checkExpiration(Calendar.getInstance()));
    }

    @Test
    public void testCheckExpirationNullDate() {
        System.out.println("checkExpiration sin fecha de caducidad");
        Product instance = new Product();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.checkExpiration(Calendar.getInstance());
        });
    }

    @Test
    public void testDiscardProductWithStock() {
        System.out.println("discardProduct con stock");
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5);
        instance.discardProduct();
        assertEquals(0, instance.getQuantity());
    }

    @Test
    public void testDiscardProductAlreadyEmpty() {
        System.out.println("discardProduct sin stock");
        Product instance = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 0);
        instance.discardProduct();
        assertEquals(0, instance.getQuantity());
    }
    
}
