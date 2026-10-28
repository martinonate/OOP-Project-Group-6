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
public class AppointmentTest {
    
     /**
     * Test of schedule method, of class Appointment.
     */
    @Test
    public void testSchedule() {
        System.out.println("schedule");
        Calendar date = null;
        Appointment instance = new Appointment();
        instance.schedule(date);
    }

    @Test
    public void testScheduleSuccess() {
        System.out.println("Test schedule success");
        Calendar date = Calendar.getInstance();
        Appointment instance = new Appointment();
        
        instance.schedule(date);
        
        assertEquals("Programada", instance.getStatus());
        assertEquals(date, instance.getDate());
    }

    @Test
    public void testScheduleCustomerSuccess() {
        System.out.println("Test schedule with Customer Success");
        Calendar date = Calendar.getInstance();
        Customer customer = new Customer();
        customer.setAppointments(new java.util.ArrayList<>());
        
        Appointment instance = new Appointment();
        instance.setCustomer(customer);
        
        instance.schedule(date);
        
        assertEquals("Programada", instance.getStatus());
        assertTrue(customer.getAppointments().contains(instance));
    }

    @Test
    public void testScheduleNullDate() {
        System.out.println("Test Schedule when the date is null");
        Appointment instance = new Appointment();
        instance.schedule(null);
        
        assertNotNull(instance.getDate(), "Error de negocio: El sistema permitió programar una cita con fecha nula.");
    }

    @Test
    public void testSchedulePastDate() {
        System.out.println("Test schedule using a past date");
        Calendar pastDate = Calendar.getInstance();
        pastDate.set(2020, Calendar.JANUARY, 1);
        
        Appointment instance = new Appointment();
        instance.schedule(pastDate);
        
        assertNotEquals("Programada", instance.getStatus(), "Error de negocio: El sistema aceptó y programó una cita con fecha pasada.");
    }

    
    
    
    
    
    /**
     * Test of cancel method, of class Appointment.
     */
    @Test
    public void testCancel() {
        System.out.println("cancel");
        Appointment instance = new Appointment();
        instance.cancel();
    }
    
    @Test
    public void testCancelAppointment() {
        System.out.println("Test cancel an appointmen when it works correctly");
        Appointment instance = new Appointment();
        instance.setStatus("Programada");
        
        instance.cancel();
        
        assertEquals("Cancelada", instance.getStatus());
    }

    @Test
    public void testCancelNewAppointment() {
        System.out.println("Test cancel new appointment");
        Appointment instance = new Appointment();
        
        instance.cancel();
        
        assertEquals("Cancelada", instance.getStatus());
    }

    @Test
    public void testCancelAlreadyCompleted() {
        System.out.println("Test cancel already completed appointment");
        Appointment instance = new Appointment();
        instance.setStatus("Completada");
        
        instance.cancel();
        
        assertNotEquals("Cancelada", instance.getStatus(), "Error de negocio: Se permitió cancelar una cita que ya estaba completada.");
    }

    @Test
    public void testCancelAlreadyCancelled() {
        System.out.println("Test cancel already cancelled appointment");
        Appointment instance = new Appointment();
        instance.setStatus("Cancelada");
        
        instance.cancel();
        
        assertEquals("Activa", instance.getStatus(), "Error de negocio: El sistema no validó que la cita ya estaba cancelada previamente.");
    }
    
    
    
    
    
    /**
     * Test of confirm method, of class Appointment.
     */
    @Test
    public void testConfirm() {
        System.out.println("confirm");
        Appointment instance = new Appointment();
        instance.confirm();
    }
    
    @Test
    public void testConfirmSuccess() {
        System.out.println("Test confirm success");
        Appointment instance = new Appointment();
        instance.setStatus("Programada");
        
        instance.confirm();
        
        assertEquals("Confirmada", instance.getStatus());
    }

    @Test
    public void testConfirmNewAppointment() {
        System.out.println("Test confirm new created appointment");
        Appointment instance = new Appointment();
        
        instance.confirm();
        
        assertEquals("Confirmada", instance.getStatus());
    }

    @Test
    public void testConfirmAlreadyCancelled() {
        System.out.println("Test confirm already cancelled appointment");
        Appointment instance = new Appointment();
        instance.setStatus("Cancelada");
        
        instance.confirm();
        
        assertEquals("Confirmada", instance.getStatus());
    }

    @Test
    public void testConfirmAlreadyCompleted() {
        System.out.println("Test confirm already completed appointment");
        Appointment instance = new Appointment();
        instance.setStatus("Completada");
        
        instance.confirm();
        
        assertNotEquals("Confirmada", instance.getStatus(), "Error de negocio: Se permitió confirmar una cita que ya estaba completada.");
    }
    
    
    
    
        
    /**
     * Test of seeLocation method, of class Appointment.
     */
    @Test
    public void testSeeLocation() {
        System.out.println("seeLocation");
        Appointment instance = new Appointment();
        Location expResult = null;
        Location result = instance.seeLocation();
        assertEquals(expResult, result);
    }
    
    
    @Test
    public void testSeeLocationSucces() {
        System.out.println("Test see location success");
        Location location = new Location("Av. Rio Zamora y Acceso 5");
        Appointment instance = new Appointment();
        instance.setLocation(location);
        
        Location result = instance.seeLocation();
        
        assertEquals(location, result);
    }

    @Test
    public void testSeeLocationNull() {
        System.out.println("Test see when a location is null");
        Appointment instance = new Appointment();
        
        Location result = instance.seeLocation();
        
        assertNull(result);
    }

    @Test
    public void testSeeLocationUpdate() {
        System.out.println("Test to see the last location update ");
        Location location1 = new Location("Av. Rio Zamora y Acceso 5");
        Location location2 = new Location("Av. Rio Zamora y Acceso 5");
        Appointment instance = new Appointment();
        instance.setLocation(location1);
        instance.setLocation(location2);
        
        Location result = instance.seeLocation();
        
        assertEquals(location2, result);
    }

    @Test
    public void testSeeLocationMandatoryFails() {
        System.out.println("Test seeLocation mandatory location (Debe fallar)");
        Appointment instance = new Appointment(); // location es null por defecto
        
        assertNotNull(instance.seeLocation(), "Error de negocio: La cita no cuenta con una ubicación asignada.");
    }

    
}
