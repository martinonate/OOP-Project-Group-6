
package ec.edu.espe.cosmoclick.model;

import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Reminder {
    
    private int id;
    private Calendar date;
    private String message;
    private String status;
    private Appointment appointment;

    public Reminder() {
    }

    public Reminder(int id, Calendar date, String message, String status, Appointment appointment) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.status = status;
        this.appointment = appointment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void sendReminder() {
        System.out.println("Recordatorio: " + message);
        this.status = "Enviado";
    }

    public void markAsSent() {
        this.status = "Enviado";
    }

    
}
