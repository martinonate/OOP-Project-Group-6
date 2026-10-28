/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ec.edu.espe.cosmoclick.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class ReminderTest {

    @Test
    public void testSendReminderNullMessage() {
        System.out.println("sendReminder con mensaje null");
        Reminder instance = new Reminder();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.sendReminder();
        });
    }

    @Test
    public void testMarkAsSentPending() {
        System.out.println("markAsSent de un recordatorio pendiente");
        Reminder instance = new Reminder(1, Calendar.getInstance(), "Cita manana", "Pendiente", new Appointment());
        instance.markAsSent();
        assertEquals("Enviado", instance.getStatus());
    }

    @Test
    public void testMarkAsSentTwice() {
        System.out.println("markAsSent llamado dos veces");
        Reminder instance = new Reminder(1, Calendar.getInstance(), "Cita manana", "Pendiente", new Appointment());
        instance.markAsSent();
        instance.markAsSent();
        assertEquals("Enviado", instance.getStatus());
    }

    @Test
    public void testMarkAsSentWithoutAppointment() {
        System.out.println("markAsSent sin cita asociada");
        Reminder instance = new Reminder();
        assertThrows(IllegalStateException.class, () -> {
            instance.markAsSent();
        });
    }

    
}
