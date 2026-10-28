package ec.edu.espe.cosmoclick.model;


import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Employee {
    
     // ===== Atributos =====
    private int id;
    private String name;
    private int phone;
    private String role;

    // ===== Constructores =====
    public Employee() {
    }

    public Employee(int id, String name, int phone, String role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    // ===== Getters y Setters =====
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void manageAppointments(Appointment appointment) {
        appointment.setEmployee(this);
        appointment.confirm();
    }

    public void manageInventory(Inventory inventory) {
        inventory.generateLowStockAlert();
    }

    public void registerSale(Sale sale) {
        sale.setEmployee(this);
        sale.setDateTime(Calendar.getInstance());
    }

    
}
