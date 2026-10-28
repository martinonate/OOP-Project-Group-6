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
public class LocationTest {

    

    @Test
    public void testGetLocationValid() {
        System.out.println("getLocation con direccion y referencia");
        Location instance = new Location("Av. Amazonas", "Frente al parque");
        assertEquals("Av. Amazonas (Frente al parque)", instance.getLocation());
    }

    @Test
    public void testGetLocationNullReference() {
        System.out.println("getLocation sin referencia");
        Location instance = new Location("Av. Amazonas", null);
        assertEquals("Av. Amazonas", instance.getLocation());
    }

    @Test
    public void testGetLocationEmptyObject() {
        System.out.println("getLocation de un objeto vacio");
        Location instance = new Location();
        assertThrows(IllegalStateException.class, () -> {
            instance.getLocation();
        });
    }
    
    
}
