package ec.edu.espe.cosmoclick.controller;

import ec.edu.espe.cosmoclick.model.Appointment;
import ec.edu.espe.cosmoclick.model.Customer;
import ec.edu.espe.cosmoclick.model.Employee;
import ec.edu.espe.cosmoclick.model.Inventory;
import ec.edu.espe.cosmoclick.model.Location;
import ec.edu.espe.cosmoclick.model.PatientHistory;
import ec.edu.espe.cosmoclick.model.Payment;
import ec.edu.espe.cosmoclick.model.PaymentReport;
import ec.edu.espe.cosmoclick.model.Price;
import ec.edu.espe.cosmoclick.model.Product;
import ec.edu.espe.cosmoclick.model.Sale;
import ec.edu.espe.cosmoclick.model.Service;
import ec.edu.espe.cosmoclick.model.DiagnosticRecord;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 *
 * @author Martin Onate <Team 6, espe>
 */
public class SpaController {
    
    private ArrayList<Customer> customers;
    private ArrayList<Appointment> appointments;
    private ArrayList<Service> services;
    private ArrayList<Sale> sales;
    private ArrayList<PatientHistory> histories;
    private Inventory inventory;
    private Employee employee;
    private Location location;

    public SpaController() {
        this.customers = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.services = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.histories = new ArrayList<>();
        this.inventory = new Inventory(1, 0, 5);
        this.employee = new Employee(1, "Maria Gabriela", 987654321, "Cosmetologa");
        this.location = new Location("Av. Rio Zamora y Acceso 5", "Cerca del Redondel");
        loadDefaultServices();
    }
    
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }
    
    public ArrayList<PatientHistory> getHistories() {
        return histories;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Location getLocation() {
        return location;
    }

    public Customer registerCustomer(String name, int phone, String email) {
        Customer customer = new Customer(nextCustomerId(), name, phone, email);
        customers.add(customer);
        return customer;
    }

    public Customer findCustomer(int id) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                return customers.get(i);
            }
        }
        return null;
    }

    public boolean updateCustomer(int id, String name, int phone, String email) {
        Customer customer = findCustomer(id);
        if (customer == null) {
            return false;
        }
        customer.updateInformation(name, phone, email);
        return true;
    }

    public Service findService(int id) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId() == id) {
                return services.get(i);
            }
        }
        return null;
    }

    public Appointment findAppointment(int id) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == id) {
                return appointments.get(i);
            }
        }
        return null;
    }

    public boolean isTimeTaken(Calendar date, String time) {
        String dateText = DateUtil.format(date);
        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            boolean sameSlot = DateUtil.format(a.getDate()).equals(dateText)
                    && a.getTime().equals(time);
            if (sameSlot && !"Cancelada".equals(a.getStatus())) {
                return true;
            }
        }
        return false;
    }

    public Appointment scheduleAppointment(int customerId, int serviceId, Calendar date, String time) {
        Customer customer = findCustomer(customerId);
        Service service = findService(serviceId);
        if (customer == null || service == null || isTimeTaken(date, time)) {
            return null;
        }
        Appointment appointment = new Appointment(nextAppointmentId(), date, time,
                "Pendiente", customer, null, location, service);
        appointment.schedule(date);
        appointments.add(appointment);
        return appointment;
    }

    public boolean confirmAppointment(int id) {
        Appointment appointment = findAppointment(id);
        if (appointment == null || "Cancelada".equals(appointment.getStatus())) {
            return false;
        }
        employee.manageAppointments(appointment);
        return true;
    }

    public boolean cancelAppointment(int id) {
        Appointment appointment = findAppointment(id);
        if (appointment == null) {
            return false;
        }
        appointment.cancel();
        return true;
    }

    public Product registerProduct(String name, String brand, double price, Calendar expiration, int quantity) {
        Product product = new Product(nextProductId(), name, brand, new Price(0, price), expiration, quantity);
        inventory.addProduct(product);
        return product;
    }
    
      public void loadDefaultProducts() {
        if (!inventory.getProducts().isEmpty()) {
            return;
        }
        registerProduct("Gel Limpiador Facial", "Cetaphil", 14.50, DateUtil.parse("2028-03-31"), 20);
        registerProduct("Serum Acido Hialuronico", "The Ordinary", 22.00, DateUtil.parse("2027-11-30"), 15);
        registerProduct("Crema Hidratante", "Nivea", 9.75, DateUtil.parse("2028-01-31"), 25);
        registerProduct("Protector Solar SPF 50", "La Roche-Posay", 28.90, DateUtil.parse("2027-09-30"), 12);
        registerProduct("Mascarilla de Arcilla", "Garnier", 7.50, DateUtil.parse("2028-05-31"), 18);
        registerProduct("Tonico Facial", "Neutrogena", 11.25, DateUtil.parse("2027-12-31"), 16);
        registerProduct("Aceite Corporal Reafirmante", "Weleda", 19.90, DateUtil.parse("2028-02-29"), 10);
        registerProduct("Shampoo Fortalecedor", "Kerastase", 24.00, DateUtil.parse("2028-04-30"), 14);
    }

    public boolean restockProduct(int id, int quantity) {
        Product product = inventory.getProductDetails(id);
        if (product == null || quantity <= 0) {
            return false;
        }
        inventory.updateQuantity(product, product.getQuantity() + quantity);
        return true;
    }

    // ===== Ventas y pagos =====
    public Sale findSale(int id) {
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getId() == id) {
                return sales.get(i);
            }
        }
        return null;
    }

    public Sale registerSale(int customerId, int[] productIds, int[] quantities, String method) {
        Customer customer = findCustomer(customerId);
        if (customer == null || productIds.length == 0 || !hasEnoughStock(productIds, quantities)) {
            return null;
        }
        Sale sale = new Sale(nextSaleId(), null, customer, null, null, "Venta de productos");
        employee.registerSale(sale);
        for (int i = 0; i < productIds.length; i++) {
            Product product = inventory.getProductDetails(productIds[i]);
            sale.addItem(product, quantities[i]);
            for (int j = 0; j < quantities[i]; j++) {
                product.sellProduct();
            }
            inventory.updateStock(product);
        }
        Payment payment = new Payment(nextPaymentId(), 0, method, null, false);
        payment.processPayment(sale);
        sales.add(sale);
        return sale;
    }

    public boolean cancelSale(int id) {
        Sale sale = findSale(id);
        if (sale == null || sale.getProducts().isEmpty()) {
            return false;
        }
        for (int i = 0; i < sale.getProducts().size(); i++) {
            Product product = sale.getProducts().get(i);
            product.buyProduct();
            inventory.updateStock(product);
        }
        if (sale.getPayment() != null) {
            sale.getPayment().generateRefund(sale.getPayment().getAmount());
        }
        sale.cancelSale();
        return true;
    }

    public PaymentReport buildPaymentReport(String method) {
        PaymentReport report = new PaymentReport(1, Calendar.getInstance(), method, 0, "General");
        for (int i = 0; i < sales.size(); i++) {
            Payment payment = sales.get(i).getPayment();
            boolean included = payment != null
                    && ("Todos".equalsIgnoreCase(method)
                    || payment.getPaymentMethod().equalsIgnoreCase(method));
            if (included) {
                report.generateReport(payment);
            }
        }
        return report;
    }

    public Sale findSaleByPayment(Payment payment) {
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getPayment() == payment) {
                return sales.get(i);
            }
        }
        return null;
    }

    private void loadDefaultServices() {
        services.add(new Service(1, "Limpieza Facial Profunda", "Tratamiento de limpieza profunda para el rostro", new Price(0, 45), 60));
        services.add(new Service(2, "Hidratación Facial Intensa", "Tratamiento avanzado para restaurar la hidratación de la piel", new Price(0, 50), 50));
        services.add(new Service(3, "Piel de Porcelana", "Tratamiento facial para lograr una piel lisa y luminosa", new Price(0, 60), 60));
        services.add(new Service(4, "Detox Facial", "Eliminación de toxinas e impurezas del rostro", new Price(0, 40), 45));
        services.add(new Service(5, "Mascarilla LED Facial", "Terapia de luz LED para rejuvenecimiento y cuidado de la piel", new Price(0, 35), 30));
        services.add(new Service(6, "Radiofrecuencia Facial", "Tratamiento reafirmante y tensor para el rostro", new Price(0, 70), 50));
        services.add(new Service(7, "Modelado Corporal", "Tratamiento estético para moldear la silueta", new Price(0, 80), 60));
        services.add(new Service(8, "Cintura Perfecta", "Tratamiento reductor enfocado en el área de la cintura", new Price(0, 65), 50));
        services.add(new Service(9, "Control de Celulitis", "Tratamiento especializado para reducir la celulitis", new Price(0, 60), 45));
        services.add(new Service(10, "Glúteos Firmes", "Tratamiento reafirmante y tonificante para glúteos", new Price(0, 55), 45));
        services.add(new Service(11, "Poder de Glúteos (Glute Power)", "Tratamiento intensivo para tonificar y levantar glúteos", new Price(0, 70), 50));
        services.add(new Service(12, "Camuflaje de Estrías", "Tratamiento estético para disimular la apariencia de estrías", new Price(0, 90), 60));
        services.add(new Service(13, "Revitalización Capilar", "Tratamiento para fortalecer y revitalizar el cabello", new Price(0, 50), 40));
        services.add(new Service(14, "Mesoterapia Capilar", "Aplicación de nutrientes directamente en el cuero cabelludo", new Price(0, 75), 45));
        services.add(new Service(15, "Dermapen Capilar", "Estimulación capilar mediante micropunciones", new Price(0, 70), 45));
        services.add(new Service(16, "Exosomas Capilares", "Tratamiento regenerativo avanzado con exosomas para el cabello", new Price(0, 120), 60));
    }

    private boolean hasEnoughStock(int[] productIds, int[] quantities) {
        for (int i = 0; i < productIds.length; i++) {
            if (quantities[i] <= 0 || !inventory.checkAvailability(productIds[i], quantities[i])) {
                return false;
            }
        }
        return true;
    }

    private int nextCustomerId() {
        int max = 0;
        for (int i = 0; i < customers.size(); i++) {
            max = Math.max(max, customers.get(i).getId());
        }
        return max + 1;
    }

    private int nextAppointmentId() {
        int max = 0;
        for (int i = 0; i < appointments.size(); i++) {
            max = Math.max(max, appointments.get(i).getId());
        }
        return max + 1;
    }

    private int nextProductId() {
        int max = 0;
        for (int i = 0; i < inventory.getProducts().size(); i++) {
            max = Math.max(max, inventory.getProducts().get(i).getId());
        }
        return max + 1;
    }

    private int nextSaleId() {
        int max = 0;
        for (int i = 0; i < sales.size(); i++) {
            max = Math.max(max, sales.get(i).getId());
        }
        return max + 1;
    }

    private int nextPaymentId() {
        int max = 0;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getPayment() != null) {
                max = Math.max(max, sales.get(i).getPayment().getId());
            }
        }
        return max + 1;
    }

    public PatientHistory registerPatientHistory(int customerId, int age, String skinType, String allergies, String treatments, String observations) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            return null;
        }
        PatientHistory history = findHistoryByCustomer(customerId);
        if (history == null) {
            history = new PatientHistory();
            history.setId(nextHistoryId());
            history.setCustomer(customer);
            history.setPatientName(customer.getName());
            histories.add(history);
        }
        history.registerPatientHistory(age, skinType, allergies, treatments, observations);
        return history;
    }
    
    public DiagnosticRecord addDiagnosticRecord(int customerId, String skinType, String healthHistory, String observations) {
    PatientHistory history = findHistoryByCustomer(customerId);
        if (history == null) {
            return null;
        }
    DiagnosticRecord record = new DiagnosticRecord(nextRecordId(history), skinType, healthHistory, observations, history);
    record.createRecord(history);   // lo agrega a la lista del historial
    return record;
    }

    public boolean updateDiagnosticRecord(int customerId, int recordId, String skinType, String healthHistory, String observations) {
        PatientHistory history = findHistoryByCustomer(customerId);
        if (history == null) {
            return false;
        }
    for (int i = 0; i < history.getRecords().size(); i++) {
        DiagnosticRecord record = history.getRecords().get(i);
        if (record.getId() == recordId) {
            record.updateRecord(skinType, healthHistory, observations);
            return true;
        }
    }
        return false;
    }

    private int nextRecordId(PatientHistory history) {
        int max = 0;
        for (int i = 0; i < history.getRecords().size(); i++) {
            max = Math.max(max, history.getRecords().get(i).getId());
        }
        return max + 1;
    }

    private int nextHistoryId() {
        int max = 0;
        for (int i = 0; i < histories.size(); i++) {
            max = Math.max(max, histories.get(i).getId());
        }
        return max + 1;
    }

    public PatientHistory findHistoryByCustomer(int customerId) {
        for (int i = 0; i < histories.size(); i++) {
        Customer customer = histories.get(i).getCustomer();
        if (customer != null && customer.getId() == customerId) {
            return histories.get(i);
        }
    }
    return null;
  }
}