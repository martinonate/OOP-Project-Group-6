/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class ServiceTest {
    
     @Test
    public void testDisplayDetailsValid() {
        System.out.println("displayDetails con servicio completo");
        Service instance = new Service(1, "Limpieza facial", "Limpieza profunda", new Price(0, 25), 45);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.displayDetails();
        System.setOut(original);
        assertTrue(out.toString().contains("Servicio: Limpieza facial"));
        assertTrue(out.toString().contains("Duracion: 45 minutos"));
    }

    @Test
    public void testDisplayDetailsWithoutPrice() {
        System.out.println("displayDetails sin precio");
        Service instance = new Service();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.displayDetails();
        });
    }

    @Test
    public void testUpdatePriceValid() {
        System.out.println("updatePrice con precio valido");
        Service instance = new Service();
        Price price = new Price(0, 30);
        instance.updatePrice(price);
        assertSame(price, instance.getPrice());
    }

    @Test
    public void testUpdatePriceNull() {
        System.out.println("updatePrice con precio null");
        Service instance = new Service();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.updatePrice(null);
        });
    }

    
}
