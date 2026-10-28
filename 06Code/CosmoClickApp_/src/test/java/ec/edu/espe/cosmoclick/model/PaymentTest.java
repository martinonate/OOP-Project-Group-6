/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

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
public class PaymentTest {

    @Test
    public void testProcessPaymentValid() {
        System.out.println("processPayment con venta valida");
        Sale sale = new Sale();
        sale.addItem(new Product(1, "Crema", "Nivea", new Price(0, 50), null, 5), 1);
        Payment instance = new Payment();
        instance.processPayment(sale);
        assertEquals(50f, instance.getAmount(), 0.001f);
        assertTrue(instance.isPaymentStatus());
        assertSame(instance, sale.getPayment());
    }

    @Test
    public void testProcessPaymentEmptySale() {
        System.out.println("processPayment con venta sin productos");
        Sale sale = new Sale();
        Payment instance = new Payment();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.processPayment(sale);
        });
    }

    @Test
    public void testProcessPaymentNullSale() {
        System.out.println("processPayment con venta null");
        Sale sale = null;
        Payment instance = new Payment();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.processPayment(sale);
        });
    }



    @Test
    public void testCheckStatusPending() {
        System.out.println("checkStatus de un pago pendiente");
        Payment instance = new Payment(1, 50f, "Efectivo", Calendar.getInstance(), false);
        assertEquals("Pendiente", instance.checkStatus());
    }

    @Test
    public void testGenerateRefundValid() {
        System.out.println("generateRefund con monto valido");
        Payment instance = new Payment(1, 100f, "Efectivo", Calendar.getInstance(), true);
        instance.generateRefund(30f);
        assertEquals(70f, instance.getAmount(), 0.001f);
    }

    @Test
    public void testGenerateRefundGreaterThanAmount() {
        System.out.println("generateRefund mayor al monto pagado");
        Payment instance = new Payment(1, 100f, "Efectivo", Calendar.getInstance(), true);
        instance.generateRefund(150f);
        assertEquals(100f, instance.getAmount(), 0.001f);
    }

    @Test
    public void testGenerateRefundNegative() {
        System.out.println("generateRefund con monto negativo");
        Payment instance = new Payment(1, 100f, "Efectivo", Calendar.getInstance(), true);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.generateRefund(-20f);
        });
    }


    @Test
    public void testValidateAmountNegative() {
        System.out.println("validateAmount con monto negativo");
        Payment instance = new Payment(1, 50f, "Efectivo", Calendar.getInstance(), true);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.validateAmount(-50f);
        });
    }

    
}
