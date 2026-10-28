/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
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
public class SaleTest {

    @Test
    public void testAddItemQuantityThree() {
        System.out.println("addItem con cantidad 3");
        Sale instance = new Sale();
        instance.addItem(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5), 3);
        assertEquals(3, instance.getProducts().size());
    }

    @Test
    public void testAddItemNegativeQuantity() {
        System.out.println("addItem con cantidad negativa");
        Sale instance = new Sale();
        Product product = new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.addItem(product, -2);
        });
    }

    @Test
    public void testAddItemNullProduct() {
        System.out.println("addItem con producto null");
        Sale instance = new Sale();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.addItem(null, 1);
        });
    }

    @Test
    public void testCancelSaleClearsProducts() {
        System.out.println("cancelSale limpia los productos");
        Sale instance = new Sale();
        instance.addItem(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5), 2);
        instance.cancelSale();
        assertTrue(instance.getProducts().isEmpty());
        assertEquals("Venta cancelada", instance.getSaleDetails());
    }

    @Test
    public void testCancelSaleEmpty() {
        System.out.println("cancelSale de una venta vacia");
        Sale instance = new Sale();
        instance.cancelSale();
        assertEquals("Venta cancelada", instance.getSaleDetails());
    }

    @Test
    public void testGenerateReceiptValid() {
        System.out.println("generateReceipt con productos");
        Sale instance = new Sale(1, Calendar.getInstance(), null, null, null, "");
        instance.addItem(new Product(1, "Crema", "Nivea", new Price(0, 10), null, 5), 1);
        instance.addItem(new Product(2, "Gel", "Dove", new Price(0, 20), null, 5), 1);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.generateReceipt();
        System.setOut(original);
        assertTrue(out.toString().contains("Venta #1"));
        assertTrue(out.toString().contains("TOTAL: 30.0"));
    }

    @Test
    public void testGenerateReceiptWithoutDate() {
        System.out.println("generateReceipt sin fecha");
        Sale instance = new Sale();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.generateReceipt();
        System.setOut(original);
        assertFalse(out.toString().contains("Fecha:"));
    }

    @Test
    public void testGenerateReceiptEmptySale() {
        System.out.println("generateReceipt de una venta vacia");
        Sale instance = new Sale();
        assertThrows(IllegalStateException.class, () -> {
            instance.generateReceipt();
        });
    }

    
}
