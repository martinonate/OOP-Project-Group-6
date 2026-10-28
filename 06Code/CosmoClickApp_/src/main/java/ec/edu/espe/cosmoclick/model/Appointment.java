package ec.edu.espe.cosmoclick.model;


import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */


public class Appointment {

    private int id;
    private Calendar date;
    private String time;
    private String status;
    private Customer customer;
    private Employee employee;
    private Location location;
    private Service service;


    public Appointment(int id, Calendar date, String time, String status, Customer customer, Employee employee, Location location, Service service) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.customer = customer;
        this.employee = employee;
        this.location = location;
        this.service = service;
    }
    
    public Appointment() {
        
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void schedule(Calendar date) {
        this.date = date;
        this.status = "Programada";
        if (customer != null) {
            customer.getAppointments().add(this);
        }
    }

    public void cancel() {
        this.status = "Cancelada";
    }

    public void confirm() {
        this.status = "Confirmada";
    }

    public Location seeLocation() {
        return location;
    }
}
    
