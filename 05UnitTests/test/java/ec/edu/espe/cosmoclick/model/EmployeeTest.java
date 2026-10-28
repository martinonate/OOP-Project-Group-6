
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
public class EmployeeTest {
    
    /**
     * Test of manageAppointments method, of class Employee.
     */
    @Test
    public void testManageAppointments() {
        System.out.println("manageAppointments");
        Appointment appointment = null;
        Employee instance = new Employee();
        instance.manageAppointments(appointment);
    }
    
    @Test
    public void testManageAppointmentsGood() {
        System.out.println("Test manage appointments succces");
        Employee employee = new Employee(1, "Maria Lopez", 999111222, "Recepcionista");
        Appointment appointment = new Appointment();
        
        employee.manageAppointments(appointment);
        
        assertEquals(employee, appointment.getEmployee());
        assertEquals("Confirmada", appointment.getStatus());
    }

    @Test
    public void testManageAppointmentsAlreadyCancelled() {
        System.out.println("Test manage appointments already  is cancelled");
        Employee employee = new Employee(1, "Maria Lopez", 999111222, "Recepcionista");
        Appointment appointment = new Appointment();
        appointment.setStatus("Cancelada");
        
        employee.manageAppointments(appointment);
        
        assertEquals(employee, appointment.getEmployee());
        assertEquals("Confirmada", appointment.getStatus());
    }

    @Test
    public void testManageAppointmentsNullAppointment() {
        System.out.println("Test manage appointments null appointment");
        Employee employee = new Employee(1, "Maria Lopez", 999111222, "Recepcionista");
        
        employee.manageAppointments(null);
        
        assertNotNull(null, "Error de negocio: Se permitió gestionar una cita nula.");
    }
    
    
    
    
      
    /**
     * Test of manageInventory method, of class Employee.
     */
    @Test
    public void testManageInventory() {
        System.out.println("manageInventory");
        Inventory inventory = null;
        Employee instance = new Employee();
        instance.manageInventory(inventory);
    }
    
    @Test
    public void testManageInventoryGreat() {
        System.out.println("Test manage inventory success");
        Employee employee = new Employee(1, "Carlos Perez", 988222333, "Administrador");
        Inventory inventory = new Inventory();
        
        employee.manageInventory(inventory);
        
        assertDoesNotThrow(() -> employee.manageInventory(inventory));
    }

    @Test
    public void testManageInventoryMultiple() {
        System.out.println("Test manage inventory multiple success");
        Employee employee = new Employee(1, "Carlos Perez", 988222333, "Administrador");
        Inventory inventory1 = new Inventory();
        Inventory inventory2 = new Inventory();
        
        employee.manageInventory(inventory1);
        employee.manageInventory(inventory2);
        
        assertDoesNotThrow(() -> employee.manageInventory(inventory2));
    }

    @Test
    public void testManageInventoryNullInventory() {
        System.out.println("Test manage inventory null inventory");
        Employee employee = new Employee(1, "Carlos Perez", 988222333, "Administrador");
        
        employee.manageInventory(null);
        
        assertNotNull(null, "Error de negocio: Se intentó gestionar un inventario nulo.");
    }
    
    
    
    
    
    /**
     * Test of registerSale method, of class Employee.
     */
    @Test
    public void testRegisterSale() {
        System.out.println("registerSale");
        Sale sale = null;
        Employee instance = new Employee();
        instance.registerSale(sale);
    }
    
    @Test
    public void testRegisterSaleGreat() {
        System.out.println("Test register sale success");
        Employee employee = new Employee(1, "Ana Gomez", 977333444, "Vendedora");
        Sale sale = new Sale();
        
        employee.registerSale(sale);
        
        assertEquals(employee, sale.getEmployee());
        assertNotNull(sale.getDateTime());
    }

    @Test
    public void testRegisterSaleReassignment() {
        System.out.println("Test register sale reassignment");
        Employee employee1 = new Employee(1, "Ana Gomez", 977333444, "Vendedora");
        Employee employee2 = new Employee(2, "Luis Soto", 977555666, "Cajero");
        Sale sale = new Sale();
        
        employee1.registerSale(sale);
        employee2.registerSale(sale);
        
        assertEquals(employee2, sale.getEmployee());
        assertNotNull(sale.getDateTime());
    }

    @Test
    public void testRegisterSaleMultiple() {
        System.out.println("Test register sale multiple");
        Employee employee = new Employee(1, "Ana Gomez", 977333444, "Vendedora");
        Sale sale1 = new Sale();
        Sale sale2 = new Sale();
        
        employee.registerSale(sale1);
        employee.registerSale(sale2);
        
        assertEquals(employee, sale1.getEmployee());
        assertEquals(employee, sale2.getEmployee());
    }

    @Test
    public void testRegisterSaleNullSale() {
        System.out.println("Test register when a sale is null");
        Employee employee = new Employee(1, "Ana Gomez", 977333444, "Vendedora");
        
        employee.registerSale(null);
        
        assertNotNull(null, "Error de negocio: Se permitió registrar una venta nula sin validar.");
    }

    
}
