
package ec.edu.espe.cosmoclick.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class Sale {
    
    private int id;
    private Calendar dateTime;
    private Customer customer;
    private Employee employee;
    private ArrayList<Product> products;
    private Payment payment;
    private String saleDetails;

    public Sale() {
        this.products = new ArrayList<>();
    }

    public Sale(int id, Calendar dateTime, Customer customer, Employee employee, Payment payment, String saleDetails) {
        this.id = id;
        this.dateTime = dateTime;
        this.customer = customer;
        this.employee = employee;
        this.products = new ArrayList<>();
        this.payment = payment;
        this.saleDetails = saleDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(String saleDetails) {
        this.saleDetails = saleDetails;
    }

    public float calculateTotal() {
        float total = 0;
        for (int i = 0; i < products.size(); i++) {
            total = total + (float) products.get(i).getPrice().calculateFinalPrice();
        }
        return total;
    }

    public void addItem(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public void cancelSale() {
        products.clear();
        saleDetails = "Venta cancelada";
    }

    public void generateReceipt() {
        System.out.println("===== MaGaby Home Spa - Recibo =====");
        System.out.println("Venta #" + id);
        if (dateTime != null) {
            System.out.println("Fecha: " + dateTime.get(Calendar.DAY_OF_MONTH) + "/"
                    + (dateTime.get(Calendar.MONTH) + 1) + "/" + dateTime.get(Calendar.YEAR));
        }
        for (int i = 0; i < products.size(); i++) {
            System.out.println("- " + products.get(i).getName() + ": " + products.get(i).getPrice().calculateFinalPrice());
        }
        System.out.println("TOTAL: " + calculateTotal());
    }
}
