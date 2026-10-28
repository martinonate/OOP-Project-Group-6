/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

import java.util.ArrayList;
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
public class CustomerTest {
    
   /**
     * Test of register method, of class Customer.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        Customer instance = new Customer();
        instance.register();
    }
    
    @Test
    public void testRegisterSuccess() {
        System.out.println("Test register success");
        Customer instance = new Customer();
        instance.setName("Erick Revelo");
        
        instance.register();
    }

    @Test
    public void testRegisterWithFullData() {
        System.out.println("Test register with full data");
        Customer instance = new Customer(1, "Ana Lopez", 987654321, "ana@mail.com");
        
        instance.register();
        assertEquals("Ana Lopez", instance.getName());
    }

    @Test
    public void testRegisterEmptyName() {
        System.out.println("Test register when the name is empty");
        Customer instance = new Customer();
        instance.setName("");
        
        instance.register();
        
        assertFalse(instance.getName().isEmpty(), "Error de negocio: Se permitió registrar o procesar un cliente con nombre vacío.");
    }

    @Test
    public void testRegisterNullName() {
        System.out.println("Test register whem tehe name is null");
        Customer instance = new Customer();
        instance.setName(null);
        
        instance.register();
        
        assertNotNull(instance.getName(), "Error de negocio: Se permitió procesar un cliente con nombre nulo.");
    }
    
    
    /**
     * Test of updateInformation method, of class Customer.
     */
    @Test
    public void testUpdateInformation() {
        System.out.println("updateInformation");
        String name = "";
        int phone = 0;
        String email = "";
        Customer instance = new Customer();
        instance.updateInformation(name, phone, email);
    }
    
    @Test
    public void testUpdateInformationComplete() {
        System.out.println("Test update information correctly");
        Customer instance = new Customer(1, "Juan Perez", 9999999, "juan@mail.com");
        
        instance.updateInformation("Carlos Gomez", 8888888, "carlos@mail.com");
        
        assertEquals("Carlos Gomez", instance.getName());
        assertEquals(8888888, instance.getPhone());
        assertEquals("carlos@mail.com", instance.getEmail());
    }

    @Test
    public void testUpdateInformationPartial() {
        System.out.println("Test update  whit partial information");
        Customer instance = new Customer();
        
        instance.updateInformation("Maria", 987654, "maria@mail.com");
        
        assertNotNull(instance.getName());
        assertEquals("maria@mail.com", instance.getEmail());
    }

    @Test
    public void testUpdateInformationEmptyName() {
        System.out.println("Test update information when the name is empty");
        Customer instance = new Customer(1, "Ana", 1234567, "ana@mail.com");
        
        instance.updateInformation("", 1234567, "ana@mail.com");
        
        assertFalse(instance.getName().isEmpty(), "Error de negocio: Se permitió actualizar el cliente con un nombre vacío.");
    }

    @Test
    public void testUpdateInformationInvalidPhone() {
        System.out.println("Test update information when the phone number is invalid");
        Customer instance = new Customer(1, "Pedro", 1234567, "pedro@mail.com");
        
        instance.updateInformation("Pedro", -500, "pedro@mail.com");
        
        assertTrue(instance.getPhone() > 0, "Error de negocio: Se permitió actualizar un número de teléfono negativo.");
    }
     
    
    /**
     * Test of viewAppointments method, of class Customer.
     */
    @Test
    public void testViewAppointments() {
        System.out.println("viewAppointments");
        Customer instance = new Customer();
        ArrayList<Appointment> expResult = null;
        ArrayList<Appointment> result = instance.viewAppointments();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testViewAppointmentsCorrectly() {
        System.out.println("Test view appointments success");
        Customer instance = new Customer();
        ArrayList<Appointment> result = instance.viewAppointments();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testViewAppointmentsWithItems() {
        System.out.println("Test viewAppointments with items success");
        Customer instance = new Customer();
        Appointment appointment = new Appointment();
        instance.getAppointments().add(appointment);
        
        ArrayList<Appointment> result = instance.viewAppointments();
        
        assertEquals(1, result.size());
        assertTrue(result.contains(appointment));
    }

    @Test
    public void testViewAppointmentsModifiedList() {
        System.out.println("Test view appointments modified list");
        Customer instance = new Customer();
        ArrayList<Appointment> customList = new ArrayList<>();
        instance.setAppointments(customList);
        
        ArrayList<Appointment> result = instance.viewAppointments();
        
        assertEquals(customList, result);
    }

    @Test
    public void testViewAppointmentsNotNullLists() {
        System.out.println("Test view appointments mandatory initialized list");
        Customer instance = new Customer();
        instance.setAppointments(null);
        
        ArrayList<Appointment> result = instance.viewAppointments();
        
        assertNotNull(result, "Error de negocio: La lista de citas retornada es nula.");
    }

    
}
