package ec.edu.espe.cosmoclick.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.edu.espe.cosmoclick.model.Appointment;
import ec.edu.espe.cosmoclick.model.Customer;
import ec.edu.espe.cosmoclick.model.Product;
import ec.edu.espe.cosmoclick.model.Sale;
import java.util.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpaControllerTest {

    private SpaController controller;
    private Calendar date;

    @BeforeEach
    public void setUp() {
        controller = new SpaController();
        date = DateUtil.parse("2030-05-20");
    }

    @Test
    public void registerCustomer_assignsConsecutiveIds() {
        Customer first = controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        Customer second = controller.registerCustomer("Luis", 992222222, "luis@mail.com");
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    public void updateCustomer_unknownId_returnsFalse() {
        assertFalse(controller.updateCustomer(99, "X", 1, "x"));
    }

    @Test
    public void scheduleAppointment_validData_isScheduled() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        Appointment appointment = controller.scheduleAppointment(1, 1, date, "10:00");
        assertNotNull(appointment);
        assertEquals("Programada", appointment.getStatus());
    }

    @Test
    public void scheduleAppointment_sameSlot_isRejected() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.scheduleAppointment(1, 1, date, "10:00");
        assertNull(controller.scheduleAppointment(1, 2, date, "10:00"));
    }

    @Test
    public void scheduleAppointment_afterCancel_slotIsFree() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.scheduleAppointment(1, 1, date, "10:00");
        controller.cancelAppointment(1);
        assertNotNull(controller.scheduleAppointment(1, 1, date, "10:00"));
    }

    @Test
    public void scheduleAppointment_unknownCustomer_returnsNull() {
        assertNull(controller.scheduleAppointment(5, 1, date, "10:00"));
    }

    @Test
    public void confirmAppointment_cancelled_returnsFalse() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.scheduleAppointment(1, 1, date, "10:00");
        controller.cancelAppointment(1);
        assertFalse(controller.confirmAppointment(1));
    }

    @Test
    public void registerSale_discountsStockAndProcessesPayment() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.registerProduct("Crema", "Nivea", 25.5, date, 10);
        Sale sale = controller.registerSale(1, new int[]{1}, new int[]{2}, "Efectivo");
        assertNotNull(sale);
        assertEquals(8, controller.getInventory().getProductDetails(1).getQuantity());
        assertEquals(51.0f, sale.calculateTotal(), 0.001);
        assertTrue(sale.getPayment().isPaymentStatus());
    }

    @Test
    public void registerSale_notEnoughStock_returnsNull() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.registerProduct("Crema", "Nivea", 25.5, date, 1);
        assertNull(controller.registerSale(1, new int[]{1}, new int[]{5}, "Efectivo"));
        assertEquals(1, controller.getInventory().getProductDetails(1).getQuantity());
    }

    @Test
    public void cancelSale_returnsStock() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.registerProduct("Crema", "Nivea", 25.5, date, 10);
        controller.registerSale(1, new int[]{1}, new int[]{3}, "Tarjeta");
        assertTrue(controller.cancelSale(1));
        Product product = controller.getInventory().getProductDetails(1);
        assertEquals(10, product.getQuantity());
        assertFalse(controller.cancelSale(1));
    }

    @Test
    public void buildPaymentReport_filtersByMethod() {
        controller.registerCustomer("Ana", 991111111, "ana@mail.com");
        controller.registerProduct("Crema", "Nivea", 10, date, 20);
        controller.registerSale(1, new int[]{1}, new int[]{1}, "Efectivo");
        controller.registerSale(1, new int[]{1}, new int[]{2}, "Tarjeta");
        assertEquals(2, controller.buildPaymentReport("Todos").getPaymentCount());
        assertEquals(1, controller.buildPaymentReport("Tarjeta").getPaymentCount());
        assertEquals(20.0f, controller.buildPaymentReport("Tarjeta").calculateTotal(), 0.001);
    }

    @Test
    public void restockProduct_addsQuantity() {
        controller.registerProduct("Crema", "Nivea", 10, date, 4);
        assertTrue(controller.restockProduct(1, 6));
        assertEquals(10, controller.getInventory().getProductDetails(1).getQuantity());
        assertFalse(controller.restockProduct(1, 0));
    }
}
