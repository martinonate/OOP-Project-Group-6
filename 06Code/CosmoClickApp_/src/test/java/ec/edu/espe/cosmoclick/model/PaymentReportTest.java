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
public class PaymentReportTest {
    

    @Test
    public void testGenerateReportNull() {
        System.out.println("generateReport con pago null");
        Payment payment = null;
        PaymentReport instance = new PaymentReport();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.generateReport(payment);
        });
    }

    @Test
    public void testCalculateTotalEmpty() {
        System.out.println("calculateTotal sin pagos");
        PaymentReport instance = new PaymentReport();
        assertEquals(0f, instance.calculateTotal(), 0.001f);
    }

    @Test
    public void testFilterByMethodNotFound() {
        System.out.println("filterByMethod con metodo inexistente");
        PaymentReport instance = new PaymentReport();
        instance.generateReport(new Payment(1, 30f, "Efectivo", Calendar.getInstance(), true));
        assertTrue(instance.filterByMethod("Transferencia").isEmpty());
    }

    @Test
    public void testFilterByMethodNull() {
        System.out.println("filterByMethod con metodo null");
        PaymentReport instance = new PaymentReport();
        instance.generateReport(new Payment(1, 30f, "Efectivo", Calendar.getInstance(), true));
        assertThrows(IllegalArgumentException.class, () -> {
            instance.filterByMethod(null);
        });
    }

    @Test
    public void testShowPaymentsStatusPaid() {
        System.out.println("showPaymentsStatus de un pago realizado");
        PaymentReport instance = new PaymentReport();
        Payment payment = new Payment(1, 30f, "Efectivo", Calendar.getInstance(), true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        instance.showPaymentsStatus(payment);
        System.setOut(original);
        assertTrue(out.toString().contains("Pago #1: Pagado"));
    }

    @Test
    public void testShowPaymentsStatusNull() {
        System.out.println("showPaymentsStatus con pago null");
        PaymentReport instance = new PaymentReport();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.showPaymentsStatus(null);
        });
    }
    
}
