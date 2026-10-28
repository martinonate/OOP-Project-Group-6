/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

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
public class PriceTest {


    @Test
    public void testCalculateFinalPriceDefault() {
        System.out.println("calculateFinalPrice de un precio sin valores");
        Price instance = new Price();
        assertEquals(0.0, instance.calculateFinalPrice(), 0.001);
    }


    @Test
    public void testApplyDiscountOver100() {
        System.out.println("applyDiscount mayor a 100 por ciento");
        Price instance = new Price(0, 100);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.applyDiscount(150);
        });
    }

    @Test
    public void testUpdatePriceValid() {
        System.out.println("updatePrice con precio valido");
        Price instance = new Price(10, 100);
        instance.updatePrice(80f);
        assertEquals(80.0, instance.calculateFinalPrice(), 0.001);
        assertEquals(0.0, instance.getDiscount(), 0.001);
    }

    @Test
    public void testUpdatePriceNegative() {
        System.out.println("updatePrice con precio negativo");
        Price instance = new Price(0, 100);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.updatePrice(-5f);
        });
    }
    
}
