package ec.edu.espe.cosmoclick.model;


import java.util.ArrayList;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Customer {
    
    private int id;
    private String name;
    private int phone;
    private String email;
    private ArrayList<Appointment> appointments;

    public Customer() {
        this.appointments = new ArrayList<>();
    }

    public Customer(int id, String name, int phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.appointments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void register() {
        System.out.println("Cliente registrado: " + name);
    }

    public void updateInformation(String name, int phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public ArrayList<Appointment> viewAppointments() {
        return appointments;
    }
    
}
